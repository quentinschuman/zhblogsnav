package com.example.zhblogsnav.constant;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/20
 */
public class CommonConst {

    public static final String BLOG_REDIS_KEY = "blog:";

    public static final String BLOG_RABBITMQ_EXCHANGE = "blog_sync";

    public static final String BLOG_RABBITMQ_ROUTINGKEY = "schedule";

    public static final String BLOG_RABBITMQ_CONSUMER_QUEUE = "schedule.consumer";

    public static final String BLOG_KAFKA_TOPIC = "BLOG_SYNC";
}
