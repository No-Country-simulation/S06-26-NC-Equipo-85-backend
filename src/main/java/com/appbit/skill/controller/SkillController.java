package com.appbit.skill.controller;

import com.appbit.skill.dto.SkillResponse;
import com.appbit.skill.service.SkillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
@Tag(name = "Skills", description = "Technical skills catalog")
@SecurityRequirement(name = "bearerAuth")
public class SkillController {

    private final SkillService skillService;

    /**
     * Recupera el catálogo completo de habilidades disponibles.
     *
     * Este endpoint es utilizado por el frontend para mostrar todas
     * las habilidades técnicas registradas en el sistema.
     *
     * @return lista de habilidades.
     */
    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }
}
