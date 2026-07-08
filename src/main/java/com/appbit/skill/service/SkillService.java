package com.appbit.skill.service;

import com.appbit.skill.dto.SkillResponse;
import com.appbit.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    /**
     * Recupera todas las habilidades registradas en el sistema.
     *
     * Este método obtiene todas las entidades Skill desde la base
     * de datos y las transforma en DTOs para ser enviadas al frontend.
     *
     * @return lista de habilidades disponibles.
     */
    public List<SkillResponse> getAllSkills() {

        return skillRepository.findAll()
                .stream()
                .map(skill -> new SkillResponse(
                        skill.getId(),
                        skill.getName()
                ))
                .toList();
    }
}
