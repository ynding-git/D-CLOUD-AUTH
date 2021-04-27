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

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private Long maxWait;

    @Value("${spring.redis.timeout}")
    private Long timeout;

}
