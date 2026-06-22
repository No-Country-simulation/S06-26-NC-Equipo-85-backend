package com.appbit.profile.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Invalid WhatsApp number format. Use digits only, optionally starting with +"
    )
    private String whatsapp;
}
