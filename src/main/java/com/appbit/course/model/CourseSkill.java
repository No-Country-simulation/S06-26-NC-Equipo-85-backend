package com.appbit.course.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_id", nullable = false)
    private Integer courseId;

    @Column(name = "skill_id", nullable = false)
    private Integer skillId;
}