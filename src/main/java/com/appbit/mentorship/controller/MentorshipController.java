package com.appbit.mentorship.controller;

import com.appbit.common.enums.SessionStatus;
import com.appbit.mentorship.dto.CreateSessionRequest;
import com.appbit.mentorship.dto.MentorshipSessionResponse;
import com.appbit.mentorship.dto.MentorshipSummaryResponse;
import com.appbit.mentorship.service.MentorshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mentorships")
@RequiredArgsConstructor
@Tag(name = "Mentorships", description = "Mentorship session endpoints")
@SecurityRequirement(name = "bearerAuth")
public class MentorshipController {

    private final MentorshipService mentorshipService;

    @PostMapping("/sessions")
    @Operation(summary = "Create an available session slot (MENTOR only)")
    public ResponseEntity<MentorshipSessionResponse> createSession(
            @Valid @RequestBody CreateSessionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mentorshipService.createSession(request));
    }

    @GetMapping("/sessions")
    @Operation(summary = "List sessions with optional filters (defaults to AVAILABLE)")
    public ResponseEntity<List<MentorshipSummaryResponse>> getSessions(
            @RequestParam(required = false) SessionStatus status,
            @RequestParam(required = false) Boolean practice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(mentorshipService.getSessions(status, practice, date));
    }

    @GetMapping("/sessions/{id}")
    @Operation(summary = "Get the detail of a specific session")
    public ResponseEntity<MentorshipSessionResponse> getSessionDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(mentorshipService.getSessionDetail(id));
    }

    @PostMapping("/sessions/{id}/book")
    @Operation(summary = "Book an available session (MENTEE only)")
    public ResponseEntity<MentorshipSessionResponse> bookSession(@PathVariable UUID id) {
        return ResponseEntity.ok(mentorshipService.bookSession(id));
    }

    @PatchMapping("/sessions/{id}/cancel")
    @Operation(summary = "Cancel a session (mentor or assigned mentee)")
    public ResponseEntity<MentorshipSessionResponse> cancelSession(@PathVariable UUID id) {
        return ResponseEntity.ok(mentorshipService.cancelSession(id));
    }

    @PatchMapping("/sessions/{id}/complete")
    @Operation(summary = "Mark a session as completed (session's MENTOR only)")
    public ResponseEntity<MentorshipSessionResponse> completeSession(@PathVariable UUID id) {
        return ResponseEntity.ok(mentorshipService.completeSession(id));
    }

    @GetMapping("/my-sessions")
    @Operation(summary = "Get sessions for the authenticated user (mentor or mentee)")
    public ResponseEntity<List<MentorshipSummaryResponse>> getMySessions() {
        return ResponseEntity.ok(mentorshipService.getMySessions());
    }
}
