package com.appbit.mentorship.model;

import com.appbit.common.enums.SessionStatus;
import com.appbit.profile.model.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "mentorship_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorshipSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_profile_id", nullable = false)
    private Profile mentorProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_profile_id", nullable = true)
    private Profile menteeProfile;

    @Column(name = "schedule_date", nullable = false)
    private OffsetDateTime scheduleDate;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private SessionStatus status;

    @Column(name = "is_practice_invitation", nullable = false)
    private Boolean isPracticeInvitation;
}
