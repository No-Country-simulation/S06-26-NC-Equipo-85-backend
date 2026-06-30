package com.appbit.health.controller;

import com.appbit.health.dto.CheckinDetailResponse;
import com.appbit.health.dto.CheckinRequest;
import com.appbit.health.dto.CheckinResponse;
import com.appbit.health.dto.CheckinSummaryResponse;
import com.appbit.health.dto.EmpathicResponse;
import com.appbit.health.service.HealthService;
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
@RequestMapping("/api/v1/health/checkins")
@RequiredArgsConstructor
@Tag(name = "Health", description = "Mental health check-in endpoints")
@SecurityRequirement(name = "bearerAuth")
public class HealthController {

    private final HealthService healthService;

    @PostMapping
    @Operation(summary = "Submit a mental health check-in")
    public ResponseEntity<CheckinResponse> saveCheckin(@Valid @RequestBody CheckinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(healthService.saveCheckin(request));
    }

    @GetMapping
    @Operation(summary = "Get the authenticated user's check-in history")
    public ResponseEntity<List<CheckinSummaryResponse>> getCheckinHistory() {
        return ResponseEntity.ok(healthService.getCheckinHistory());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the detail of a specific check-in")
    public ResponseEntity<CheckinDetailResponse> getCheckinDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(healthService.getCheckinDetail(id));
    }

    @PostMapping("/{id}/empathic-response")
    @Operation(summary = "Get an empathic AI response for a given check-in")
    public ResponseEntity<EmpathicResponse> getEmpathicResponse(@PathVariable UUID id) {
        String text = healthService.getEmpathicResponse(id);
        return ResponseEntity.ok(new EmpathicResponse(text));
    }
}
