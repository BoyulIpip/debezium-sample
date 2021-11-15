package com.example.debeziumsample.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "customer.datasource")
public class DatabaseConfiguration {

  private String host;
  private String port;
  private String database;
  private String username;
  private String password;
}
