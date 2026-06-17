package com.appbit.profile.model;

import com.appbit.common.enums.*;
import com.appbit.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;           
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    private UUID id; 

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM) 
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM) 
    @Column(name = "education_level")
    private EducationLevelType educationLevel;

    private String continent;
    private String state;
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;
    private String whatsapp;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "professional_level")
    private LevelType professionalLevel;

    @Column(name = "tech_area")
    private String techArea;

    private String objective;
    
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileSkill> skills;
}