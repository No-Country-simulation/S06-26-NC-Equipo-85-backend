package com.appbit.course.model;

import com.appbit.common.model.SkillCategory;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String provider;

    @Enumerated(EnumType.STRING)
    private SkillCategory level;
    
    private String url;
}