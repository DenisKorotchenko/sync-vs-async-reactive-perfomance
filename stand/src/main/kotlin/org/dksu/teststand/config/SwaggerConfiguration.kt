package org.dksu.teststand.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info

@Configuration
class SwaggerConfiguration {
    @Bean
    fun swagger(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Test Stand")
        )
}
