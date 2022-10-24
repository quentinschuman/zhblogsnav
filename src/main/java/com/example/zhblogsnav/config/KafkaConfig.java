package com.example.zhblogsnav.config;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/22
 */
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    ProducerFactory kafkaProducerFactory() {
        Map<String, Object> configs = new HashMap<>();
        // TODO 属性配置待补充完善
        configs.put("bootstrap.servers", bootstrapServers);
        configs.put("key.serializer", StringSerializer.class);
        configs.put("value.serializer", StringSerializer.class);
        return new DefaultKafkaProducerFactory(configs);
    }

    @Bean
    KafkaTemplate<String,String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(kafkaProducerFactory());
    }
}
