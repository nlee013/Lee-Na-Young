package com.springboot.lookoutside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.springboot.lookoutside.config.AppProperties;

@EnableJpaAuditing
@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class LookOutsideApplication {

	public static void main(String[] args) {
		SpringApplication.run(LookOutsideApplication.class, args);
	}

}
