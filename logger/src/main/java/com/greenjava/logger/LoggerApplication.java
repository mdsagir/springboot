package com.greenjava.logger;

import com.greenjava.logger.service.Config;
import com.greenjava.logger.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class LoggerApplication implements CommandLineRunner {

	@Autowired
	private MyService myService;

	public static void main(String[] args) {
		Config.init();
		SpringApplication.run(LoggerApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		myService.serviceCall();
	}

}
