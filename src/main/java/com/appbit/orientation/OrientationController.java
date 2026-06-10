package com.appbit.orientation;

import com.appbit.orientation.dto.HealthRequest;
import com.appbit.orientation.dto.HealthResponse;
import com.appbit.orientation.dto.OrientationRequest;
import com.appbit.orientation.dto.OrientationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrientationController {

    private final OrientationService orientationService;

    public OrientationController(OrientationService orientationService) {
        this.orientationService = orientationService;
    }

    @PostMapping("/orientar")
    @ResponseStatus(HttpStatus.OK)
    public OrientationResponse orient(@Valid @RequestBody OrientationRequest request) {
        return orientationService.orient(request);
    }

    @PostMapping("/salud")
    @ResponseStatus(HttpStatus.OK)
    public HealthResponse checkHealth(@Valid @RequestBody HealthRequest request) {
        return orientationService.checkHealth(request);
    }
}
