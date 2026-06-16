package com.appbit.profile.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;
import com.appbit.skill.model.Skill;

@Entity
@Table(name = "profile_skills")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder 
public class ProfileSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false) 
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)   
    private Skill skill;
}