package com.example.debeziumsample;

import com.example.debeziumsample.config.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DatabaseConfiguration.class)
public class DebeziumSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebeziumSampleApplication.class, args);
	}

}
