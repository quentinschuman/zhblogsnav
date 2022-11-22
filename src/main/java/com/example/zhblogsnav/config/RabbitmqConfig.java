package com.example.zhblogsnav.config;

import com.example.zhblogsnav.constant.CommonConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/22
 */
@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.connection-timeout}")
    private Integer connectionTimeout;

    @Value("${spring.rabbitmq.shutdown-timeout}")
    private Integer shutdownTimeout;

    @Bean
    DirectExchange blogSyncExchange() {
        return new DirectExchange(CommonConst.BLOG_RABBITMQ_EXCHANGE);
    }

    @Bean
    Queue blogConsumeQueue() {
        return new Queue(CommonConst.BLOG_RABBITMQ_CONSUMER_QUEUE);
    }

    @Bean
    Binding blogConsumeBinding() {
        return new Binding(CommonConst.BLOG_RABBITMQ_CONSUMER_QUEUE, Binding.DestinationType.QUEUE,
                CommonConst.BLOG_RABBITMQ_EXCHANGE, CommonConst.BLOG_RABBITMQ_ROUTINGKEY, null);
    }

    @Bean
    CachingConnectionFactory rabbitmqConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setConnectionTimeout(connectionTimeout);
        factory.setCloseTimeout(shutdownTimeout);
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
//        factory.setPublisherReturns(true);
        return factory;
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitmqConnectionFactory());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setUsePublisherConnection(true);
        return rabbitTemplate;
    }
}
