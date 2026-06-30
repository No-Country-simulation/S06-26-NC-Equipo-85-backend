package com.appbit.experience.service;

import com.appbit.common.enums.ExperienceType;
import com.appbit.common.enums.UserRole;
import com.appbit.experience.dto.ExperienceDetailResponse;
import com.appbit.experience.dto.ExperienceRequest;
import com.appbit.experience.dto.ExperienceSummaryResponse;
import com.appbit.experience.dto.SkillSummaryResponse;
import com.appbit.experience.model.Experience;
import com.appbit.experience.model.ExperienceSkill;
import com.appbit.experience.repository.ExperienceRepository;
import com.appbit.profile.model.Profile;
import com.appbit.profile.repository.ProfileRepository;
import com.appbit.skill.model.Skill;
import com.appbit.skill.repository.SkillRepository;
import com.appbit.user.model.User;
import com.appbit.user.repository.UserRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    @Transactional
    public ExperienceDetailResponse createExperience(ExperienceRequest request) {
        User user = getAuthenticatedUser();
        requireRole(user, UserRole.MENTOR);
        Profile mentorProfile = getAuthenticatedProfile(user);

        Experience experience = Experience.builder()
                .mentorProfile(mentorProfile)
                .title(request.getTitle())
                .description(request.getDescription())
                .speakerName(mentorProfile.getFullName())
                .speakerRole(request.getSpeakerRole())
                .type(request.getType())
                .contentUrl(request.getContentUrl())
                .dateTime(request.getDateTime())
                .build();

        experience.setSkills(buildExperienceSkills(experience, request.getSkillIds()));

        return toDetailResponse(experienceRepository.save(experience));
    }

    @Transactional(readOnly = true)
    public List<ExperienceSummaryResponse> getExperiences(UUID skillId, ExperienceType type) {
        Specification<Experience> spec = buildFilter(skillId, type);

        return experienceRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "dateTime"))
                .stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExperienceDetailResponse getExperienceDetail(UUID experienceId) {
        return toDetailResponse(findOrThrow(experienceId));
    }

    @Transactional
    public ExperienceDetailResponse updateExperience(UUID experienceId, ExperienceRequest request) {
        Profile profile = getAuthenticatedProfile(getAuthenticatedUser());
        Experience experience = findOrThrow(experienceId);
        requireOwnership(experience, profile);

        experience.setTitle(request.getTitle());
        experience.setDescription(request.getDescription());
        experience.setSpeakerRole(request.getSpeakerRole());
        experience.setType(request.getType());
        experience.setContentUrl(request.getContentUrl());
        experience.setDateTime(request.getDateTime());

        experience.getSkills().clear();
        experience.getSkills().addAll(buildExperienceSkills(experience, request.getSkillIds()));

        return toDetailResponse(experienceRepository.save(experience));
    }

    @Transactional
    public void deleteExperience(UUID experienceId) {
        Profile profile = getAuthenticatedProfile(getAuthenticatedUser());
        Experience experience = findOrThrow(experienceId);
        requireOwnership(experience, profile);

        experienceRepository.delete(experience);
    }

    private List<ExperienceSkill> buildExperienceSkills(Experience experience, List<UUID> skillIds) {
        List<Skill> skills = skillRepository.findAllById(skillIds);
        if (skills.size() != skillIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more skill ids are invalid");
        }

        return skills.stream()
                .map(skill -> ExperienceSkill.builder()
                        .experience(experience)
                        .skill(skill)
                        .build())
                .toList();
    }

    private Specification<Experience> buildFilter(UUID skillId, ExperienceType type) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            if (skillId != null) {
                query.distinct(true);
                Join<Experience, ExperienceSkill> skillsJoin = root.join("skills");
                predicates.add(cb.equal(skillsJoin.get("skill").get("id"), skillId));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private void requireOwnership(Experience experience, Profile profile) {
        if (!experience.getMentorProfile().getId().equals(profile.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only the owning mentor can modify this experience");
        }
    }

    private void requireRole(User user, UserRole required) {
        if (user.getRole() != required) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "This action requires the " + required.name() + " role");
        }
    }

    private Experience findOrThrow(UUID experienceId) {
        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Experience not found with id: " + experienceId));
    }

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Authenticated user not found"));
    }

    private Profile getAuthenticatedProfile(User user) {
        return profileRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Profile not found for the authenticated user"));
    }

    private ExperienceSummaryResponse toSummaryResponse(Experience experience) {
        return ExperienceSummaryResponse.builder()
                .id(experience.getId())
                .mentorProfileId(experience.getMentorProfile().getId())
                .title(experience.getTitle())
                .speakerName(experience.getSpeakerName())
                .speakerRole(experience.getSpeakerRole())
                .type(experience.getType())
                .dateTime(experience.getDateTime())
                .contentUrl(experience.getContentUrl())
                .createdAt(experience.getCreatedAt())
                .updatedAt(experience.getUpdatedAt())
                .build();
    }

    private ExperienceDetailResponse toDetailResponse(Experience experience) {
        List<SkillSummaryResponse> skills = experience.getSkills().stream()
                .map(experienceSkill -> SkillSummaryResponse.builder()
                        .id(experienceSkill.getSkill().getId())
                        .name(experienceSkill.getSkill().getName())
                        .build())
                .toList();

        return ExperienceDetailResponse.builder()
                .id(experience.getId())
                .mentorProfileId(experience.getMentorProfile().getId())
                .title(experience.getTitle())
                .description(experience.getDescription())
                .speakerName(experience.getSpeakerName())
                .speakerRole(experience.getSpeakerRole())
                .type(experience.getType())
                .contentUrl(experience.getContentUrl())
                .createdAt(experience.getCreatedAt())
                .dateTime(experience.getDateTime())
                .updatedAt(experience.getUpdatedAt())
                .skills(skills)
                .owner(
                    experience.getMentorProfile().getId()
                        .equals(getAuthenticatedProfile(getAuthenticatedUser()).getId())
                )
                .build();
    }
}
