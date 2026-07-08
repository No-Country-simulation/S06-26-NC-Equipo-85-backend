package com.appbit.job.controller;

import com.appbit.job.dto.JobDetailResponse;
import com.appbit.job.dto.JobMatchResponse;
import com.appbit.job.service.JobService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST encargado de exponer los endpoints relacionados con las ofertas laborales.
 *
 * <p>Recibe las solicitudes HTTP, delega el procesamiento al servicio correspondiente
 * y devuelve las respuestas utilizando DTOs para evitar exponer directamente las entidades de persistencia.</p>
 */
@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
@Tag(name = "Jobs", description = "Job opportunities")
@SecurityRequirement(name = "bearerAuth")
public class JobController {

    private final JobService jobService;

    @GetMapping("/matches")
    public ResponseEntity<List<JobMatchResponse>> getJobMatches(
            @RequestParam(defaultValue = "0") double minMatch) {

        return ResponseEntity.ok(jobService.getJobMatches(minMatch));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDetailResponse> getJobById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(jobService.getJobById(id));
    }

}
