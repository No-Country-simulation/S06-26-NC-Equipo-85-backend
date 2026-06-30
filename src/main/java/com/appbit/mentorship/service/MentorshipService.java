package com.appbit.mentorship.service;

import com.appbit.common.enums.SessionStatus;
import com.appbit.common.enums.UserRole;
import com.appbit.mentorship.dto.CreateSessionRequest;
import com.appbit.mentorship.dto.MentorshipSessionResponse;
import com.appbit.mentorship.dto.MentorshipSummaryResponse;
import com.appbit.mentorship.model.MentorshipSession;
import com.appbit.mentorship.repository.MentorshipSessionRepository;
import com.appbit.profile.model.Profile;
import com.appbit.profile.repository.ProfileRepository;
import com.appbit.user.model.User;
import com.appbit.user.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentorshipService {

    private final MentorshipSessionRepository sessionRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public MentorshipSessionResponse createSession(CreateSessionRequest request) {
        User user = getAuthenticatedUser();
        requireRole(user, UserRole.MENTOR);
        Profile mentorProfile = getAuthenticatedProfile(user);

        MentorshipSession session = MentorshipSession.builder()
                .mentorProfile(mentorProfile)
                .menteeProfile(null)
                .scheduleDate(request.getScheduleDate().atOffset(ZoneOffset.UTC))
                .status(SessionStatus.AVAILABLE)
                .isPracticeInvitation(request.isPractice())
                .build();

        return toResponse(sessionRepository.save(session));
    }

    @Transactional(readOnly = true)
    public List<MentorshipSummaryResponse> getSessions(SessionStatus status, Boolean practice, LocalDate date) {
        SessionStatus effectiveStatus = status != null ? status : SessionStatus.AVAILABLE;
        Specification<MentorshipSession> spec = buildFilter(effectiveStatus, practice, date);

        return sessionRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "scheduleDate"))
                .stream()
                .map(this::toSummary)
                .toList();
    }

    @Transactional(readOnly = true)
    public MentorshipSessionResponse getSessionDetail(UUID sessionId) {
        MentorshipSession session = findOrThrow(sessionId);
        return toResponse(session);
    }

    @Transactional
    public MentorshipSessionResponse bookSession(UUID sessionId) {
        User user = getAuthenticatedUser();
        requireRole(user, UserRole.MENTEE);
        Profile menteeProfile = getAuthenticatedProfile(user);

        MentorshipSession session = sessionRepository.findByIdWithLock(sessionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Session not found with id: " + sessionId));

        if (session.getStatus() != SessionStatus.AVAILABLE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Session is no longer available (current status: " + session.getStatus() + ")");
        }

        session.setMenteeProfile(menteeProfile);
        session.setStatus(SessionStatus.SCHEDULED);

        return toResponse(sessionRepository.save(session));
    }

    @Transactional
    public MentorshipSessionResponse cancelSession(UUID sessionId) {
        User user = getAuthenticatedUser();
        Profile profile = getAuthenticatedProfile(user);
        MentorshipSession session = findOrThrow(sessionId);

        boolean isMentor = session.getMentorProfile().getId().equals(profile.getId());
        boolean isMentee = session.getMenteeProfile() != null
                && session.getMenteeProfile().getId().equals(profile.getId());

        if (!isMentor && !isMentee) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You are not authorized to cancel this session");
        }

        if (session.getStatus() == SessionStatus.COMPLETED
                || session.getStatus() == SessionStatus.CANCELED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Session cannot be canceled in its current status: " + session.getStatus());
        }

        session.setStatus(SessionStatus.CANCELED);
        return toResponse(sessionRepository.save(session));
    }

    @Transactional
    public MentorshipSessionResponse completeSession(UUID sessionId) {
        User user = getAuthenticatedUser();
        requireRole(user, UserRole.MENTOR);
        Profile profile = getAuthenticatedProfile(user);
        MentorshipSession session = findOrThrow(sessionId);

        if (!session.getMentorProfile().getId().equals(profile.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only the session's mentor can mark it as completed");
        }

        if (session.getStatus() != SessionStatus.SCHEDULED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only SCHEDULED sessions can be completed (current status: " + session.getStatus() + ")");
        }

        session.setStatus(SessionStatus.COMPLETED);
        return toResponse(sessionRepository.save(session));
    }

    @Transactional(readOnly = true)
    public List<MentorshipSummaryResponse> getMySessions() {
        User user = getAuthenticatedUser();
        Profile profile = getAuthenticatedProfile(user);

        List<MentorshipSession> sessions = user.getRole() == UserRole.MENTOR
                ? sessionRepository.findByMentorProfile_IdOrderByScheduleDateDesc(profile.getId())
                : sessionRepository.findByMenteeProfile_IdOrderByScheduleDateDesc(profile.getId());

        return sessions.stream().map(this::toSummary).toList();
    }


    private MentorshipSession findOrThrow(UUID sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Session not found with id: " + sessionId));
    }

    private Specification<MentorshipSession> buildFilter(SessionStatus status, Boolean practice, LocalDate date) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("status"), status));

            if (practice != null) {
                predicates.add(cb.equal(root.get("isPracticeInvitation"), practice));
            }

            if (date != null) {
                OffsetDateTime from = date.atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime to = date.plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC);
                predicates.add(cb.greaterThanOrEqualTo(root.get("scheduleDate"), from));
                predicates.add(cb.lessThan(root.get("scheduleDate"), to));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private void requireRole(User user, UserRole required) {
        if (user.getRole() != required) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "This action requires the " + required.name() + " role");
        }
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

    private MentorshipSessionResponse toResponse(MentorshipSession session) {
        return MentorshipSessionResponse.builder()
                .id(session.getId())
                .mentorProfileId(session.getMentorProfile().getId())
                .menteeProfileId(session.getMenteeProfile() != null
                        ? session.getMenteeProfile().getId() : null)
                .scheduleDate(session.getScheduleDate())
                .status(session.getStatus())
                .isPracticeInvitation(session.getIsPracticeInvitation())
                .build();
    }

    private MentorshipSummaryResponse toSummary(MentorshipSession session) {
        return MentorshipSummaryResponse.builder()
                .id(session.getId())
                .mentorProfileId(session.getMentorProfile().getId())
                .menteeProfileId(session.getMenteeProfile() != null
                        ? session.getMenteeProfile().getId() : null)
                .scheduleDate(session.getScheduleDate())
                .status(session.getStatus())
                .isPracticeInvitation(session.getIsPracticeInvitation())
                .build();
    }
}
