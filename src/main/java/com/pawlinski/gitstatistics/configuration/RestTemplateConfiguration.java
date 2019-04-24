package com.pawlinski.gitstatistics.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Value("${api.username}")
    private String username;

    @Value("${api.password}")
    private String password;

    private final String GITHUB_HOST = "https://api.github.com";

    @Bean
    public RestTemplate githubRestTemplate(@Autowired RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.basicAuthentication(username, password).rootUri(GITHUB_HOST).build();
    }
}
