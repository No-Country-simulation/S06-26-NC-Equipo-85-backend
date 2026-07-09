package com.appbit.job.controller;

import com.appbit.job.dto.JobDetailResponse;
import com.appbit.job.dto.JobMatchResponse;
import com.appbit.job.service.JobService;
import com.appbit.orientation.OrientationService;
import com.appbit.user.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final OrientationService orientationService;

    @GetMapping("/matches")
    public ResponseEntity<List<JobMatchResponse>> getJobMatches(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(defaultValue = "0") double minMatch) {

        List<JobMatchResponse> matches = orientationService.getJobMatches(currentUser.getId())
                .stream()
                .filter(match -> match.matchRate() >= minMatch)
                .map(match -> new JobMatchResponse(
                        match.jobId(),
                        match.company(),
                        match.title(),
                        match.matchRate()
                ))
                .toList();

        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDetailResponse> getJobById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(jobService.getJobById(id));
    }

}
