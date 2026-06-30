package com.appbit.experience.repository;

import com.appbit.experience.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ExperienceRepository extends JpaRepository<Experience, UUID>, JpaSpecificationExecutor<Experience> {
}
