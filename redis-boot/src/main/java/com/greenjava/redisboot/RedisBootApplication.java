package com.greenjava.redisboot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		/*ValueOperations operations= redisTemplate.opsForValue();
		String strKey="name";
		String name = (String) operations.get(strKey);
		System.out.println(name);*/

		// Hash Operations
		String hmkey="ht";
		HashOperations< String, String, String> hash = redisTemplate.opsForHash();
		/*for(String key:hash.keys(hmkey)) {
			System.out.println(key+" --- "+hash.get(hmkey, key));
		}*/

		/*String data = hash.get(hmkey, "data");

		ObjectMapper objectMapper=new ObjectMapper();
		List<User> list = objectMapper.readValue(data, new TypeReference<List<User>>(){});
		list.forEach(user -> System.out.println("ID "+user.getId()+" "+user.getName()));*/

		/*ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		User[] cars = objectMapper.readValue(data, User[].class);
		System.out.println(cars);*/


		List<String> stringList=new ArrayList<>();
		for (int i=1;i<=1000;i++){
			User user = new User(i, UUID.randomUUID().toString());
			String jsonString = user.toString();
			stringList.add(jsonString);
		}
		System.out.println(stringList);
		hash.put(hmkey,"data",stringList.toString());

		/*for(String key:hash.keys(hmkey)) {
			System.out.println(key+" --- "+hash.get(hmkey, key));
		}*/









		//List operations

		/*ListOperations listOperations= redisTemplate.opsForList();
		Object listKey="city";
		List<String> list = listOperations.range(listKey, 0, -1);
		list.forEach(System.out::println);*/

		// Set operations
		/*SetOperations setOperations=redisTemplate.opsForSet();
		Object setKey="state";
		Set<String> strings= setOperations.members(setKey);
		strings.forEach(System.out::println);*/


	}

}
