package com.appbit.course.service;

import com.appbit.course.dto.CourseResponse;
import com.appbit.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    /**
     * Recupera todos los cursos registrados en la plataforma.
     *
     * Este método consulta la base de datos y transforma las
     * entidades Course en DTOs para ser consumidos por el frontend.
     *
     * @return lista de cursos disponibles.
     */
    public List<CourseResponse> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(course -> new CourseResponse(
                        course.getId(),
                        course.getName(),
                        course.getProvider()
                ))
                .toList();
    }
}
