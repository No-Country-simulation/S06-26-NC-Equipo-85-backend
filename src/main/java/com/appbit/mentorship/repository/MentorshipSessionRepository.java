package com.appbit.mentorship.repository;

import com.appbit.mentorship.model.MentorshipSession;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MentorshipSessionRepository
        extends JpaRepository<MentorshipSession, UUID>, JpaSpecificationExecutor<MentorshipSession> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM MentorshipSession s WHERE s.id = :id")
    Optional<MentorshipSession> findByIdWithLock(@Param("id") UUID id);

    List<MentorshipSession> findByMentorProfile_IdOrderByScheduleDateDesc(UUID profileId);

    List<MentorshipSession> findByMenteeProfile_IdOrderByScheduleDateDesc(UUID profileId);
}
