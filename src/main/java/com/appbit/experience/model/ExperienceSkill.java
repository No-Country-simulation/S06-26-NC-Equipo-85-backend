package com.appbit.experience.model;

import java.util.UUID;

import com.appbit.skill.model.Skill;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "experience_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id", nullable = false)
    private Experience experience; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill; 
}