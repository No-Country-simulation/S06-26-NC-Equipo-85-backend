package com.appbit.profile.controller;

import com.appbit.profile.model.Profile;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @GetMapping("/{id}")
    public Profile getProfileById(@PathVariable UUID id) {
        return null;
    }
}
