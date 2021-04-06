package com.reactive.redis.reactiveredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Primary
    @Bean
    ReactiveRedisOperations<String, String> redisOperations3(ReactiveRedisConnectionFactory factory) {

        Jackson2JsonRedisSerializer<String> serializer = new Jackson2JsonRedisSerializer<>(String.class);
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        RedisSerializationContext.RedisSerializationContextBuilder<String, String> contextBuilder = RedisSerializationContext.newSerializationContext();

        contextBuilder.key(stringRedisSerializer);
        contextBuilder.value(serializer);
        contextBuilder.hashKey(stringRedisSerializer);
        contextBuilder.hashValue(genericJackson2JsonRedisSerializer);

        RedisSerializationContext<String, String> context = contextBuilder.build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}

