package com.appbit;

import com.appbit.common.enums.SkillCategory;
import com.appbit.skill.model.Skill;
import com.appbit.skill.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Usa tu Postgres de Docker
public class SkillMappingTest {

    @Autowired
    private SkillRepository skillRepository;

    @Test
    public void testSaveAndFindSkill() {
        // 1. Crear una nueva habilidad basada en tu clase Java
        Skill skill = new Skill();
        skill.setName("Java 21");
        skill.setCategory(SkillCategory.BACKEND);

        // 2. Guardarla en la base de datos
        Skill savedSkill = skillRepository.save(skill);

        // 3. Verificar que se le asignó un ID automático y que los datos coinciden
        assertThat(savedSkill.getId()).isNotNull();
        assertThat(savedSkill.getName()).isEqualTo("Java 21");
        assertThat(savedSkill.getCategory()).isEqualTo(SkillCategory.BACKEND);
    }
}