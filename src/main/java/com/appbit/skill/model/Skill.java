package com.appbit.skill.model;

import java.util.List; 
import java.util.UUID;

import com.appbit.common.model.SkillCategory;
import com.appbit.course.model.CourseSkill;      
import com.appbit.experience.model.ExperienceSkill;
import com.appbit.job.model.JobSkill;
import com.appbit.profile.model.ProfileSkill;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode; 
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private SkillCategory category;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileSkill> profiles;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseSkill> courses;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobSkill> jobs;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceSkill> experiences;
}