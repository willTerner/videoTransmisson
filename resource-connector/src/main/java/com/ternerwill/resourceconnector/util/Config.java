package com.ternerwill.resourceconnector.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@ConfigurationProperties("my-config")
public class Config {

    private static String requestBaseUrl;


    public void setRequestBaseUrl(String url){
        requestBaseUrl = url;
    }


    public static String getRequestBaseUrl(){
        return requestBaseUrl;
    }




}
