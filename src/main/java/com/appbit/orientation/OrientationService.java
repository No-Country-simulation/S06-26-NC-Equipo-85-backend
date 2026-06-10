package com.appbit.orientation;

import com.appbit.orientation.dto.HealthRequest;
import com.appbit.orientation.dto.HealthResponse;
import com.appbit.orientation.dto.OrientationRequest;
import com.appbit.orientation.dto.OrientationResponse;
import org.springframework.stereotype.Service;

@Service
public class OrientationService {

    public OrientationResponse orient(OrientationRequest request) {
        return new OrientationResponse(
                "ORIENTATION_CREATED",
                "Orientation request received successfully",
                request.query());
    }

    public HealthResponse checkHealth(HealthRequest request) {
        return new HealthResponse(
                "HEALTH_CHECK_CREATED",
                "Health request received successfully",
                request.description());
    }
}
