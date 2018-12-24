package net.in.dayan.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
//@EnableRedisHttpSession
@SpringBootApplication
public class SpringdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringdemoApplication.class, args);
	}

}

