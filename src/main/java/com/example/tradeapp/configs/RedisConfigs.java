package com.example.tradeapp.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@PropertySource("application.yml")
public class RedisConfigs {
    @Value("${redis.hostname}")
    private String hostName;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.username}")
    private String username;

    @Value("${redis.password}")
    private String password;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(hostName, port);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setUsername(username);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public <S, F> RedisTemplate<S, F> redisTemplate() {
        RedisTemplate<S, F> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
