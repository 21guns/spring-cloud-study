package com.itmuch.cloud.study.turbine.dashboard;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@SpringBootApplication
@EnableTurbineStream
@EnableHystrixDashboard
public class HystrixTurbinrDashboardApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(HystrixTurbinrDashboardApplication.class).run(args);
    }
}