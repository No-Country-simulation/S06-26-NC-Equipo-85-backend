package com.appbit.job.controller;

import com.appbit.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST encargado de exponer los endpoints relacionados con las ofertas laborales.
 *
 * <p>Recibe las solicitudes HTTP, delega el procesamiento al servicio correspondiente
 * y devuelve las respuestas utilizando DTOs para evitar exponer directamente las entidades de persistencia.</p>
 */
@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
}
