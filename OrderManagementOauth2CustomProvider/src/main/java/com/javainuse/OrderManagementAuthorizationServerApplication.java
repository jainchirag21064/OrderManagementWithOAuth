package com.javainuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Main Application for OrderManagement OAuth Authorizer Server
 */
@SpringBootApplication
@EnableAuthorizationServer
public class OrderManagementAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementAuthorizationServerApplication.class, args);
    }

}
