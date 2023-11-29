package com.myipcinvestments.bulkdownload.service;

import com.myipcinvestments.bulkdownload.config.ApiConfig;
import com.myipcinvestments.bulkdownload.dto.AccessTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiService {

    private final ApiConfig apiConfig;
    private final WebClient webClient;

    @Autowired
    public ApiService(ApiConfig apiConfig, WebClient webClient) {
        this.apiConfig = apiConfig;
        this.webClient = webClient;
    }

    public void printApiProperties(String... args) {
        // Access properties from the bean
        System.out.println("URL: " + apiConfig.getUrl());
        System.out.println("Endpoint Token: " + apiConfig.getEndpointToken());
        System.out.println("Username: " + apiConfig.getUsername());
        System.out.println("Password: " + apiConfig.getPassword());

        String tokenEndpoint = apiConfig.getEndpointToken();
        String username = apiConfig.getUsername();
        String password = apiConfig.getPassword();

        WebClient webClient = WebClient.create();

        String accessToken = webClient.post()
                .uri(tokenEndpoint)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("username", username)
                        .with("password", password))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Access Token: " + accessToken);
    }

    public AccessTokenDto getAccessToken() {
        String tokenEndpoint = apiConfig.getEndpointToken();
        String username = apiConfig.getUsername();
        String password = apiConfig.getPassword();

        return webClient.post()
                .uri(tokenEndpoint)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("username", username)
                        .with("password", password))
                .retrieve()
                .bodyToMono(AccessTokenDto.class) // Deserialization using AccessTokenDto
                .block();
    }

    public String getSubmissionsExample(AccessTokenDto accessTokenDto) {
        String url = apiConfig.getEndpointSubmissionsExample();

        return webClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenDto.getAccessToken())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
