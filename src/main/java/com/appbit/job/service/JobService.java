package com.appbit.job.service;

import com.appbit.job.dto.JobDetailResponse;
import com.appbit.job.dto.JobMatchResponse;
import com.appbit.job.model.Job;
import com.appbit.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * Servicio encargado de la lógica de negocio relacionada con las ofertas laborales.
 *
 * <p>Actúa como intermediario entre los controladores y el repositorio,
 * coordinando validaciones, reglas de negocio y operaciones de persistencia.</p>
 */
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public List<JobMatchResponse> getJobMatches(double minMatch) {

        return List.of();
    }

    @Transactional(readOnly = true)
    public JobDetailResponse getJobById(UUID id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job not found"
                ));

        return new JobDetailResponse(
                job.getId(),
                job.getCompany(),
                job.getTitle(),
                job.getDescription(),
                job.getSkills()
                        .stream()
                        .map(jobSkill -> jobSkill.getSkill().getName())
                        .toList()
        );
    }
}
