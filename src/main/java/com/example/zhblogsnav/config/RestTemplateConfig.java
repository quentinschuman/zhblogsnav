package com.example.zhblogsnav.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/20
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        builder.setConnectTimeout(Duration.ofSeconds(30));
        builder.setReadTimeout(Duration.ofSeconds(30));
        return builder;
    }

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder().build();
    }
}
