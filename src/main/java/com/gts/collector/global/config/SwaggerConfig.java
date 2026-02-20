package com.gts.collector.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger(OpenAPI) 상세 설정을 담당하는 클래스
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GrowTechStack API Document")
                        .description("기술 블로그 콘텐츠 수집 및 제공 서비스 API 명세서")
                        .version("v1.0.0"));
    }
}
