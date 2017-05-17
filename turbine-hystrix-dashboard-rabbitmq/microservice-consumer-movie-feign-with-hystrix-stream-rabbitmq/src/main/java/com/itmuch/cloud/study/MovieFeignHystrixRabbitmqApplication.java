package com.itmuch.cloud.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 使用@EnableFeignClients开启Feign
 * @author eacdy
 */
@SpringBootApplication
//feign包含熔断
@EnableFeignClients
@EnableDiscoveryClient
public class MovieFeignHystrixRabbitmqApplication {
  public static void main(String[] args) {
    SpringApplication.run(MovieFeignHystrixRabbitmqApplication.class, args);
  }
}
