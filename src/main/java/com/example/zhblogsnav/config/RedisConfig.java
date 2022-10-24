package com.example.zhblogsnav.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/20
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer maxActive;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer minIdle;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private String maxWait;

    @Value("${spring.redis.lettuce.shutdown-timeout}")
    private String shutdownTimeout;

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setDatabase(database);
        return factory;
    }

    @Bean
    StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }
}
