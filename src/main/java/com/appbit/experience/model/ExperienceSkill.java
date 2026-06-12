package com.appbit.experience.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "experience_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "experience_id", nullable = false)
    private Integer experienceId;

    @Column(name = "skill_id", nullable = false)
    private Integer skillId;
}