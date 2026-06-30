package com.appbit.profile.service;

import com.appbit.profile.dto.ContactDto;
import com.appbit.profile.dto.LocationDto;
import com.appbit.profile.dto.ProfileResponse;
import com.appbit.profile.dto.ProfileUpsertRequest;
import com.appbit.profile.model.Profile;
import com.appbit.profile.repository.ProfileRepository;
import com.appbit.user.model.User;
import com.appbit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ProfileResponse getProfile() {
        User user = getAuthenticatedUser();
        Profile profile = profileRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Profile not found for the authenticated user"));
        return toResponse(profile, user);
    }

    @Transactional
    public ProfileResponse upsertProfile(ProfileUpsertRequest request) {
        User user = getAuthenticatedUser();

        Profile profile = profileRepository.findById(user.getId())
                .orElseGet(() -> Profile.builder().user(user).build());

        profile.setFullName(request.getName());
        profile.setBirthDate(request.getBirthDate());
        profile.setGender(request.getGender());
        profile.setEducationLevel(request.getEducation());
        profile.setProfessionalLevel(request.getProfessionalLevel());
        profile.setTechArea(request.getTechArea());
        profile.setObjective(request.getObjective());

        if (request.getLocation() != null) {
            profile.setContinent(request.getLocation().getContinent());
            profile.setCountry(request.getLocation().getCountry());
            profile.setState(request.getLocation().getState());
            profile.setCity(request.getLocation().getCity());
            profile.setLatitude(request.getLocation().getLatitude());
            profile.setLongitude(request.getLocation().getLongitude());
        }

        if (request.getContact() != null) {
            profile.setWhatsapp(request.getContact().getWhatsapp());
        }

        return toResponse(profileRepository.save(profile), user);
    }

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Authenticated user not found"));
    }

    private ProfileResponse toResponse(Profile profile, User user) {
        return ProfileResponse.builder()
                .role(user.getRole())
                .name(profile.getFullName())
                .birthDate(profile.getBirthDate())
                .gender(profile.getGender())
                .education(profile.getEducationLevel())
                .professionalLevel(profile.getProfessionalLevel())
                .techArea(profile.getTechArea())
                .objective(profile.getObjective())
                .location(LocationDto.builder()
                        .continent(profile.getContinent())
                        .country(profile.getCountry())
                        .state(profile.getState())
                        .city(profile.getCity())
                        .latitude(profile.getLatitude())
                        .longitude(profile.getLongitude())
                        .build())
                .contact(ContactDto.builder()
                        .whatsapp(profile.getWhatsapp())
                        .build())
                .build();
    }
}
