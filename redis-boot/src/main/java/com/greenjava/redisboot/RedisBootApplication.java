package com.greenjava.redisboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class RedisBootApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(RedisBootApplication.class, args);
	}

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void run(String... args) throws Exception {

		String college="india";
		System.out.println(redisTemplate.hasKey(college));

		HashOperations< String, String, String> hash = redisTemplate.opsForHash();
		for(String key:hash.keys(college)) {
			System.out.println(key+" --- "+hash.get(college, key));
		}

	}
}
