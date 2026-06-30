package com.appbit.health.model;

import com.appbit.common.enums.MoodEmoji;
import com.appbit.profile.model.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode; 
import org.hibernate.type.SqlTypes;           
import java.time.OffsetDateTime;
import java.util.UUID; 

@Entity
@Table(name = "mod_checkins")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class ModCheckin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM) 
    private MoodEmoji emoji;

    private Integer rating; 
    
    @Column(columnDefinition = "TEXT") 
    private String context;

    @Column(name = "suggested_action", columnDefinition = "TEXT") 
    private String suggestedAction;

    @Builder.Default
    @Column(name = "derive_cvv")
    private Boolean deriveCvv = false;

    @Builder.Default
    @Column(name = "created_at", nullable = false, updatable = false) 
    private OffsetDateTime createdAt = OffsetDateTime.now();
}