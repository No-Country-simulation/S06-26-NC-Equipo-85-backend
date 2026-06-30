package com.appbit.job.service;

import com.appbit.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de la lógica de negocio relacionada con las ofertas laborales.
 *
 * <p>Actúa como intermediario entre los controladores y el repositorio,
 * coordinando validaciones, reglas de negocio y operaciones de persistencia.</p>
 */
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
}
