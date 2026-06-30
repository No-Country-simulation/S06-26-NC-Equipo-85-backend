package com.appbit.experience.controller;

import com.appbit.common.enums.ExperienceType;
import com.appbit.experience.dto.ExperienceDetailResponse;
import com.appbit.experience.dto.ExperienceRequest;
import com.appbit.experience.dto.ExperienceSummaryResponse;
import com.appbit.experience.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/experiences")
@RequiredArgsConstructor
@Tag(name = "Experiences", description = "Mentor experience endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping
    @Operation(summary = "Create an experience (MENTOR only)")
    public ResponseEntity<ExperienceDetailResponse> createExperience(
            @Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(experienceService.createExperience(request));
    }

    @GetMapping
    @Operation(summary = "List experiences with optional filters (skillId, type)")
    public ResponseEntity<List<ExperienceSummaryResponse>> getExperiences(
            @RequestParam(required = false) UUID skillId,
            @RequestParam(required = false) ExperienceType type) {
        return ResponseEntity.ok(experienceService.getExperiences(skillId, type));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the detail of a specific experience")
    public ResponseEntity<ExperienceDetailResponse> getExperienceDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(experienceService.getExperienceDetail(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an experience (owning mentor only)")
    public ResponseEntity<ExperienceDetailResponse> updateExperience(
            @PathVariable UUID id,
            @Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.ok(experienceService.updateExperience(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an experience (owning mentor only)")
    public ResponseEntity<Void> deleteExperience(@PathVariable UUID id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }
}
