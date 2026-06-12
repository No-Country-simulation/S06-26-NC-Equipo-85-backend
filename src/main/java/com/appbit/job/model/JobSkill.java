package com.appbit.job.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_id", nullable = false)
    private Integer jobId;

    @Column(name = "skill_id", nullable = false)
    private Integer skillId;
}