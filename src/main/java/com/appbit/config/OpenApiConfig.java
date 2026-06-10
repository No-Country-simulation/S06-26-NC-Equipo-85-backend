package com.appbit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI appBitOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("App BiT API")
                        .description("Backend API for App BiT")
                        .version("v1"));
    }
}
