package com.reactive.config;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class MongoConfig {

    private final String database;
    private final MongoClient mongoClient;

    public MongoConfig( @Value("${spring.data.mongodb.database}")String database, MongoClient mongoClient) {
        this.database = database;
        this.mongoClient = mongoClient;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient, database);
    }
}
