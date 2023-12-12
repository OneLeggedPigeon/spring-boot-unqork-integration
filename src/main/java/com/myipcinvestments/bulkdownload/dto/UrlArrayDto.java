package com.myipcinvestments.bulkdownload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class UrlArrayDto {
    final static int LIMIT = 200;

    @JsonProperty("urlArray")
    private String[] urlArray;

    public String[] getUrlArray() {
        return urlArray;
    }

    public void setUrlArray(String[] urlArray) {
        this.urlArray = urlArray;
    }

    // limit file array to a set number of entries
    public void limit() {
        int newUrlLength = Math.min(urlArray.length, LIMIT);
        setUrlArray(Arrays.copyOfRange(urlArray, 0, newUrlLength));
        if (urlArray.length > LIMIT) System.out.println("Array limited to " + newUrlLength);
    }
}
