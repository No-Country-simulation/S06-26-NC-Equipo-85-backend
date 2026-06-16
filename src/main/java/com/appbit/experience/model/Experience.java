package com.appbit.experience.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;

import com.appbit.common.model.ExperienceType;

@Entity
@Table(name = "experience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "speaker_name")
    private String speakerName;

    @Column(name = "speaker_role")
    private String speakerRole;

    @Enumerated(EnumType.STRING)
    private ExperienceType type;

    @Column(name = "content_url")
    private String contentUrl;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;
}