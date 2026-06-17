package com.appbit.experience.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode; 
import org.hibernate.type.SqlTypes;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.appbit.common.enums.ExperienceType;

@Entity
@Table(name = "experience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "speaker_name")
    private String speakerName;

    @Column(name = "speaker_role")
    private String speakerRole;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private ExperienceType type;

    @Column(name = "content_url")
    private String contentUrl;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceSkill> skills;

}