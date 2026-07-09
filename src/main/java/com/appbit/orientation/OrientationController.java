package com.appbit.orientation;

import com.appbit.course.model.Course;
import com.appbit.job.model.Job;
import com.appbit.orientation.dto.*;
import com.appbit.skill.model.Skill;
import com.appbit.user.model.User;
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
    public OrientationResponse orient(@AuthenticationPrincipal User currentUser) {
        return orientationService.orient(currentUser.getId());
    }

    @PostMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public HealthResponse checkHealth(@Valid @RequestBody HealthRequest request) {
        return orientationService.checkHealth(request);
    }

    /**
     * Obtiene las vacantes compatibles para el usuario autenticado.
     *
     * @param currentUser usuario autenticado, resuelto desde el JWT
     * @return lista de vacantes compatibles
     */
    @GetMapping("/jobs/matches")
    @ResponseStatus(HttpStatus.OK)
    public List<JobMatch> getJobMatches(@AuthenticationPrincipal User currentUser) {
        return orientationService.getJobMatches(currentUser.getId());
    }

    @GetMapping("/jobs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Job getJobById(@PathVariable UUID id) {
        return orientationService.getJobById(id);
    }

    @GetMapping("/skills")
    @ResponseStatus(HttpStatus.OK)
    public List<Skill> getSkills() {
        return orientationService.getSkills();
    }

    @GetMapping("/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getCourses() {
        return orientationService.getCourses();
    }
}
