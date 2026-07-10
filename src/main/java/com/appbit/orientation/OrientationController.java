package com.appbit.orientation;

import com.appbit.course.dto.CourseResponse;
import com.appbit.course.model.Course;
import com.appbit.job.dto.JobMatchResponse;
import com.appbit.job.model.Job;
import com.appbit.orientation.dto.*;
import com.appbit.skill.dto.SkillResponse;
import com.appbit.skill.model.Skill;
import com.appbit.user.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrientationController {

    private final OrientationService orientationService;

    public OrientationController(OrientationService orientationService) {
        this.orientationService = orientationService;
    }

    /**
     * Genera una orientación profesional para el usuario autenticado.
     *
     * @param currentUser usuario autenticado, resuelto desde el JWT
     * @return resultado de orientación profesional
     */
    @PostMapping("/v1/guidance")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    public OrientationResponse orient(@AuthenticationPrincipal User currentUser) {
        return orientationService.orient(currentUser.getId());
    }

    @PostMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    public HealthResponse checkHealth(@Valid @RequestBody HealthRequest request) {
        return orientationService.checkHealth(request);
    }
}
