package com.bmt.user.service.filter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class FilterChainLogger {

    @Bean
    public CommandLineRunner logSecurityFilters(FilterChainProxy filterChainProxy) {
        return args -> {
            System.out.println("\n=== Spring Security Filters ===");
            int chainIndex = 1;
            for (SecurityFilterChain chain : filterChainProxy.getFilterChains()) {
                System.out.println("Filter chain " + (chainIndex++) + ": " + chain);
                chain.getFilters().forEach(f ->
                        System.out.println(" - " + f.getClass().getName()));
            }
            System.out.println("================================\n");
        };
    }
}
