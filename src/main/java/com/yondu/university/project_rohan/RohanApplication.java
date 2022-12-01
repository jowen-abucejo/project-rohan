package com.yondu.university.project_rohan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.yondu.university.project_rohan.config.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class RohanApplication {

	public static void main(String[] args) {
		SpringApplication.run(RohanApplication.class, args);
	}

}
