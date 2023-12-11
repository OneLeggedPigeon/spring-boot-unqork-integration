package com.myipcinvestments.bulkdownload.controller;

import com.myipcinvestments.bulkdownload.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/")
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/test")
    @ResponseBody
    public void apiTest(){
        apiService.printApiProperties();
        System.out.println("AccessTokenDto: " + apiService.getAccessToken());
    }

}
