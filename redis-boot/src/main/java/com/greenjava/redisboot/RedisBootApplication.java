package com.greenjava.redisboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class RedisBootApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(RedisBootApplication.class, args);
	}

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public void run(String... args) throws Exception {

		// String Operations.
		ValueOperations operations= redisTemplate.opsForValue();
		String strKey="name";
		String name = (String) operations.get(strKey);
		System.out.println(name);

		// Hash Operations
		String hmkey="india";
		HashOperations< String, String, String> hash = redisTemplate.opsForHash();
		for(String key:hash.keys(hmkey)) {
			System.out.println(key+" --- "+hash.get(hmkey, key));
		}

		//List operations

		ListOperations listOperations= redisTemplate.opsForList();
		Object listKey="city";
		List<String> list = listOperations.range(listKey, 0, -1);
		list.forEach(System.out::println);

		// Set operations
		SetOperations setOperations=redisTemplate.opsForSet();
		Object setKey="state";
		Set<String> strings= setOperations.members(setKey);
		strings.forEach(System.out::println);


	}
}
