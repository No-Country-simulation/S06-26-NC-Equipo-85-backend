package com.appbit.gemini.controller;

import com.appbit.gemini.service.GeminiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class GeminiTestController {
    private final GeminiService geminiService;

    @GetMapping("/gemini")
    public String testGemini() {
        return geminiService.generateRecommendation(
                "Hola Gemini, responde únicamente con la palabra OK."
        );
    }
}
