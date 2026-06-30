package com.appbit.profile.dto;

import com.appbit.common.enums.EducationLevelType;
import com.appbit.common.enums.GenderType;
import com.appbit.common.enums.LevelType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpsertRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private GenderType gender;

    private EducationLevelType education;

    private LevelType professionalLevel;
    
    private String techArea;
    
    private String objective;
    
    @Valid
    private LocationDto location;

    @Valid
    private ContactDto contact;
}
