package com.example.zhblogsnav.schedule;

import com.example.zhblogsnav.constant.CommonConst;
import com.example.zhblogsnav.entity.Blog;
import com.example.zhblogsnav.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/20
 */
@Component
public class BlogSyncSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogSyncSchedule.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private BlogRepository blogRepository;

    private StringRedisTemplate stringRedisTemplate;

    private RabbitTemplate rabbitTemplate;

    private KafkaTemplate<String, String> kafkaTemplate;

    public BlogSyncSchedule(BlogRepository blogRepository, StringRedisTemplate stringRedisTemplate, RabbitTemplate rabbitTemplate,
    KafkaTemplate<String, String> kafkaTemplate) {
        this.blogRepository = blogRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(cron = "${blog.sync.redis.cron}")
    public void blogSync2Redis() {
        try {
            LOGGER.info("==========start sync blog to redis==========");
            long start = System.currentTimeMillis();
            List<Blog> allBlog = blogRepository.findAll();
            for (Blog blog : allBlog) {
                stringRedisTemplate.opsForValue().set(CommonConst.BLOG_REDIS_KEY + blog.getIdx(),
                        MAPPER.writeValueAsString(blog));
            }
            long end = System.currentTimeMillis();
            LOGGER.info("==========sync blog to redis end,size:{},cost:{}ms==========", allBlog.size(), (end - start));
        } catch (Exception e) {
            LOGGER.error("blogSync2Redis error:", e);
        }
    }

    @Scheduled(cron = "${blog.sync.rabbitmq.cron}")
    public void blogSync2Rabbitmq() {
        try {
            LOGGER.info("==========start sync blog to rabbitmq==========");
            long start = System.currentTimeMillis();
            List<Blog> allBlog = blogRepository.findAll();
            for (Blog blog : allBlog) {
                CorrelationData correlationData = new CorrelationData();
                correlationData.setId(blog.getId());
                rabbitTemplate.convertAndSend(CommonConst.BLOG_RABBITMQ_EXCHANGE, CommonConst.BLOG_RABBITMQ_ROUTINGKEY,
                        MAPPER.writeValueAsString(blog), correlationData);
            }
            long end = System.currentTimeMillis();
            LOGGER.info("==========sync blog to rabbitmq end,size:{},cost:{}ms==========", allBlog.size(), (end - start));
        } catch (Exception e) {
            LOGGER.error("blogSync2Rabbitmq error:", e);
        }
    }

    @Scheduled(cron = "${blog.sync.kafka.cron}")
    public void blogSync2Kafka() {
        try {
            LOGGER.info("==========start sync blog to kafka==========");
            long start = System.currentTimeMillis();
            List<Blog> allBlog = blogRepository.findAll();
            for (Blog blog : allBlog) {
                kafkaTemplate.send(CommonConst.BLOG_KAFKA_TOPIC, MAPPER.writeValueAsString(blog));
            }
            long end = System.currentTimeMillis();
            LOGGER.info("==========sync blog to kafka end,size:{},cost:{}ms==========", allBlog.size(), (end - start));
        } catch (Exception e) {
            LOGGER.error("blogSync2Kafka error:", e);
        }
    }
}
