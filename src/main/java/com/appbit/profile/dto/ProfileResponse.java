package com.appbit.profile.dto;

import com.appbit.common.enums.EducationLevelType;
import com.appbit.common.enums.GenderType;
import com.appbit.common.enums.LevelType;
import com.appbit.common.enums.UserRole;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProfileResponse {
    private UserRole role;
    private String name;
    private LocalDate birthDate;
    private GenderType gender;
    private EducationLevelType education;
    private LevelType professionalLevel;
    private String techArea;
    private String objective;
    private LocationDto location;
    private ContactDto contact;
}
