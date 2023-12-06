package com.myipcinvestments.bulkdownload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlArrayDto {

    @JsonProperty("urlArray")
    private String[] urlArray;

    public String[] getUrlArray() {
        return urlArray;
    }

    public void setUrlArray(String[] urlArray) {
        this.urlArray = urlArray;
    }
}
