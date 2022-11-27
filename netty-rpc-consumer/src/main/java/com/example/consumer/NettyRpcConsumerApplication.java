package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.example.protocol.spring.reference", "com.example.protocol.annotation", "com.example.consumer.rest"})
@SpringBootApplication
public class NettyRpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcConsumerApplication.class, args);
    }

}
