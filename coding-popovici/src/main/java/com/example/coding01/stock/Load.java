package com.example.coding01.stock;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Load {

    @Bean
    public CommandLineRunner save() {
        return strings -> {
            log.info("Import");
        };
    }
}
