package com.jun0x2dev.devfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class DevfeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevfeedApplication.class, args);
    }

}
