package com.appbit.orientation;

import com.appbit.course.model.Course;
import com.appbit.course.repository.CourseRepository;
import com.appbit.job.model.Job;
import com.appbit.job.model.JobSkill;
import com.appbit.job.repository.JobRepository;
import com.appbit.orientation.dto.*;
import com.appbit.profile.model.Profile;
import com.appbit.profile.model.ProfileSkill;
import com.appbit.profile.repository.ProfileRepository;
import com.appbit.skill.model.Skill;
import com.appbit.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrientationService {

    private final ProfileRepository profileRepository;
    private final JobRepository jobRepository;
    private final CourseRepository courseRepository;
    private final SkillRepository skillRepository;

    /**
     * Genera una orientación profesional basada en el perfil del usuario.
     *
     * <p>Actualmente devuelve información simulada mientras se implementa
     * la lógica real del motor de matching.</p>
     *
     * @param request solicitud de orientación
     * @return resultado de orientación profesional
     */
    @Transactional(readOnly = true)
    public OrientationResponse orient(OrientationRequest request) {

        ///Obtiene el perfil asociado al usuario solicitado.
        Profile profile = profileRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        /**
         * Recupera las habilidades asociadas al perfil del usuario.
         */
        List<Skill> userSkills = profile.getSkills()
                .stream()
                .map(ProfileSkill::getSkill)
                .toList();

        /**
         * Recupera todas las vacantes disponibles para realizar el matching.
         */
        List<Job> jobs = jobRepository.findAll();

        /**
         * Almacena las vacantes compatibles encontradas durante el proceso
         * de matching.
         */
        List<JobMatch> jobMatches = new ArrayList<>();
        List<GapItem> gapItems = new ArrayList<>();

        /**
         * Recorre cada vacante para calcular el porcentaje de compatibilidad.
         */
        for (Job job : jobs) {

            /**
             * Obtiene las habilidades requeridas por la vacante.
             */
            List<Skill> jobSkills = job.getSkills()
                    .stream()
                    .map(JobSkill::getSkill)
                    .toList();

            /**
             * Cuenta cuántas habilidades de la vacante posee el usuario.
             */
            long coincidencias = jobSkills.stream()
                    .filter(jobSkill ->
                            userSkills.stream()
                                    .anyMatch(userSkill ->
                                            userSkill.getId().equals(jobSkill.getId())
                                    )
                    )
                    .count();

            /**
             * Obtiene las habilidades requeridas por la vacante que el usuario
             * todavía no posee.
             */
            List<Skill> missingSkills = jobSkills.stream()
                    .filter(jobSkill ->
                            userSkills.stream()
                                    .noneMatch(userSkill ->
                                            userSkill.getId().equals(jobSkill.getId())
                                    )
                    )
                    .toList();

            gapItems.addAll(
                    missingSkills.stream()
                            .map(skill -> new GapItem(
                                    skill.getId(),
                                    skill.getName(),
                                    "Required"
                            ))
                            .toList()
            );

            /**
             * Calcula el porcentaje de compatibilidad.
             */
            double matchRate = jobSkills.isEmpty()
                    ? 0
                    : (double) coincidencias / jobSkills.size() * 100;

            jobMatches.add(
                    new JobMatch(
                            job.getId(),
                            job.getCompany(),
                            job.getTitle(),
                            matchRate
                    )
            );
        }
        /**
         * Recupera todos los cursos disponibles.
         */
        List<Course> courses = courseRepository.findAll();

        /**
        * Genera cursos sugeridos para cubrir las habilidades faltantes.
        */
        List<SuggestedCourse> suggestedCourses = courses.stream()
                .filter(course ->
                        course.getSkills().stream()
                                .anyMatch(courseSkill ->
                                        gapItems.stream()
                                                .anyMatch(gapItem ->
                                                        gapItem.id().equals(
                                                                courseSkill.getSkill().getId()
                                                        )
                                                )
                                )
                )
                .map(course -> new SuggestedCourse(
                        course.getId(),
                        course.getName(),
                        course.getProvider(),
                        course.getSkills().stream()
                                .map(courseSkill -> courseSkill.getSkill().getName())
                                .toList()
                ))
                .toList();

        /**
         * Calcula el nivel de confianza del matching en función de la
         * mejor coincidencia encontrada.
         */
        double confianza = jobMatches.stream()
                .mapToDouble(JobMatch::matchRate)
                .max()
                .orElse(0.0);

        /**
         * Calcula el porcentaje de brecha técnica del usuario.
         */
        double gapPorcentual = userSkills.isEmpty()
                ? 100.0
                : (double) gapItems.size() / (userSkills.size() + gapItems.size()) * 100;

        return new OrientationResponse(
                gapPorcentual,
                gapItems,
                suggestedCourses,
                jobMatches,
                confianza
        );
    }

    public HealthResponse checkHealth(HealthRequest request) {
        return new HealthResponse(
                "HEALTH_CHECK_CREATED",
                "Health request received successfully",
                request.description());
    }

    /**
     * Obtiene las vacantes compatibles para el usuario indicado.
     *
     * @param userId identificador del usuario
     * @return lista de vacantes con su porcentaje de compatibilidad
     */
    public List<JobMatch> getJobMatches(UUID userId) {

        /**
         * Obtiene el perfil del usuario.
         */
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        /**
         * Recupera las habilidades del usuario.
         */
        List<Skill> userSkills = profile.getSkills()
                .stream()
                .map(ProfileSkill::getSkill)
                .toList();

        /**
         * Recupera todas las vacantes disponibles.
         */
        List<Job> jobs = jobRepository.findAll();

        /**
         * Almacena las vacantes compatibles.
         */
        List<JobMatch> jobMatches = new ArrayList<>();

        /**
         * Calcula el porcentaje de compatibilidad para cada vacante.
         */
        for (Job job : jobs) {

            List<Skill> jobSkills = job.getSkills()
                    .stream()
                    .map(JobSkill::getSkill)
                    .toList();

            long coincidencias = jobSkills.stream()
                    .filter(jobSkill ->
                            userSkills.stream()
                                    .anyMatch(userSkill ->
                                            userSkill.getId().equals(jobSkill.getId())
                                    )
                    )
                    .count();

            double matchRate = jobSkills.isEmpty()
                    ? 0
                    : (double) coincidencias / jobSkills.size() * 100;

            jobMatches.add(
                    new JobMatch(
                            job.getId(),
                            job.getCompany(),
                            job.getTitle(),
                            matchRate
                    )
            );
        }

        return jobMatches;
    }

    /**
     * Obtiene una vacante por su identificador.
     *
     * @param id identificador de la vacante
     * @return vacante encontrada
     */
    public Job getJobById(UUID id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));
    }

    /**
     * Recupera el catálogo de habilidades disponibles.
     *
     * @return listado de habilidades
     */
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    /**
     * Recupera el catálogo de cursos disponibles.
     *
     * @return listado de cursos
     */
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
}
