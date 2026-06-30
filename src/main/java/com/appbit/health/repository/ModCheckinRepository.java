package com.appbit.health.repository;

import com.appbit.health.model.ModCheckin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModCheckinRepository extends JpaRepository<ModCheckin, UUID> {

    List<ModCheckin> findByProfile_IdOrderByCreatedAtDesc(UUID profileId);

    Optional<ModCheckin> findByIdAndProfile_Id(UUID id, UUID profileId);
}
