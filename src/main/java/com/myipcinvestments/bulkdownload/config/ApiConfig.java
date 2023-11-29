package com.myipcinvestments.bulkdownload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "unqork.api")
public class ApiConfig {
    private String url; // https://xyzfinancial.unqork.io/
    private String username;
    private String password;
    private String endpointToken; // api/1.0/oauth2/access_token
    private String endpointSubmissionsExample; // fbu/uapi/modules/{{moduleId}}/submissions?sortBy=created&sortOrder=-1\

    // Getters and setters for the properties

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEndpointToken() {
        return url + endpointToken;
    }

    public void setEndpointToken(String endpointToken) {
        this.endpointToken = endpointToken;
    }

    public String getEndpointSubmissionsExample() {
        return url + endpointSubmissionsExample;
    }

    public void setEndpointSubmissionsExample(String endpointSubmissionsExample) {
        this.endpointSubmissionsExample = endpointSubmissionsExample;
    }

    public ApiConfig apiConfig() {
        return new ApiConfig();
    }
}