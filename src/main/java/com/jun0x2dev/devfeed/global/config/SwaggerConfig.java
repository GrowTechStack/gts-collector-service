package com.jun0x2dev.devfeed.global.config;

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
                        .title("DevFeed API Document")
                        .description("기술 블로그 어그리게이터 DevFeed의 API 문서입니다.")
                        .version("v0.0.1"));
    }
}
