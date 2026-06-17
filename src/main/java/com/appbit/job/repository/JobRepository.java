package com.appbit.job.repository;

import com.appbit.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
/**
 * Repositorio encargado de las operaciones de acceso a datos relacionadas con la entidad Job.
 *
 * <p>Proporciona operaciones CRUD y capacidades de consulta mediante Spring Data JPA.
 * Aquí podrán definirse consultas personalizadas cuando sean necesarias.</p>
 */
public interface JobRepository extends JpaRepository<Job, UUID> {
}
