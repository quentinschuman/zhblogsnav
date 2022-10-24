package com.example.zhblogsnav.schedule;

import com.example.zhblogsnav.constant.CommonConst;
import com.example.zhblogsnav.entity.Blog;
import com.example.zhblogsnav.repository.BlogRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/22
 */
@Component
public class BlogConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogConsumer.class);

    private BlogRepository blogRepository;

    private StringRedisTemplate stringRedisTemplate;

    private RabbitTemplate rabbitTemplate;

    public BlogConsumer(BlogRepository blogRepository, StringRedisTemplate stringRedisTemplate, RabbitTemplate rabbitTemplate) {
        this.blogRepository = blogRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(cron = "${blog.consumer.redis.cron}")
    public void blogConsumeFromRedis() {
        try {
            LOGGER.info("==========start consume blog from redis==========");
            long start = System.currentTimeMillis();
            List<Blog> allBlog = blogRepository.findAll();
            List<String> redisBlogs = new LinkedList<>();
            for (Blog blog : allBlog) {
                String blogStr = stringRedisTemplate.opsForValue().get(CommonConst.BLOG_REDIS_KEY + blog.getIdx());
                redisBlogs.add(blogStr);
                LOGGER.info("blogConsumeFromRedis:{}", blogStr);
            }
            long end = System.currentTimeMillis();
            LOGGER.info("==========consume blog from redis end,size:{},cost:{}ms==========", redisBlogs.size(), (end - start));
        } catch (Exception e) {
            LOGGER.error("blogConsumeFromRedis error:", e);
        }
    }

    @RabbitListener(queues = {CommonConst.BLOG_RABBITMQ_CONSUMER_QUEUE})
    public void blogConsumeFromRabbitmq(Message message) {
        try {
            LOGGER.info("==========start consume blog from rabbitmq==========");
            long start = System.currentTimeMillis();
            String blogStr = new String(message.getBody(), "utf-8");
            LOGGER.info("blogConsumeFromRabbitmq:{}", blogStr);
            long end = System.currentTimeMillis();
            LOGGER.info("==========consume blog from rabbitmq end,cost:{}ms==========", (end - start));
        } catch (Exception e) {
            LOGGER.error("blogConsumeFromRabbitmq error:", e);
        }
    }

    @KafkaListener(topics = {CommonConst.BLOG_KAFKA_TOPIC}, groupId = "${spring.kafka.consumer.group-id}")
    public void blogConsumeFromKafka(ConsumerRecord<String, String> record) {
        try {
            LOGGER.info("==========start consume blog from kafka==========");
            long start = System.currentTimeMillis();
            String key = record.key();
            String value = record.value();
            LOGGER.info("blogConsumeFromKafka:key={},value={}", key, value);
            long end = System.currentTimeMillis();
            LOGGER.info("==========consume blog from kafka end,cost:{}ms==========", (end - start));
        } catch (Exception e) {
            LOGGER.error("blogConsumeFromKafka error:", e);
        }
    }
}
