package com.ynding.cloud.auth.authentication.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p> </p>
 *
 * @author ynding
 * @version 2021/4/27
 **/
@Data
@Configuration
public class RedisProperties {

    /**
     * cache的数据库索引
     */
    @Value("${redis.cache.database}")
    private Integer cacheDatabaseIndex;
    /**
     * sessiondb的数据库索引
     */
    @Value("${redis.session.database}")
    private Integer sessionDatabaseIndex;

    /**
     * host
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * port
     */
    @Value("${spring.redis.port}")
    private int port;

    /**
     * password
     */
    @Value("${spring.redis.password}")
    private String password;

    /**
     *  最大空闲连接
     */
    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;

    /**
     * 最小空闲连接
     */
    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;

    /**
     * 最大连接数
     */
    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;

    /**
     * 连接的最大等待阻塞的时间
     */
    @Value("${spring.redis.jedis.pool.max-wait}")
    private Long maxWait;

    /**
     * 连接超时的时间
     */
    @Value("${spring.redis.timeout}")
    private Long timeout;

}
