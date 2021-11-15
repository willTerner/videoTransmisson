package com.ternerwill.resourceconnector.config;


import com.ternerwill.resourceconnector.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer configurer = new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/desktop/**")
                        .allowedOrigins("http://localhost:8080")
                        .allowedHeaders("*")
                        .allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
                WebMvcConfigurer.super.addCorsMappings(registry);

                registry.addMapping("/mobile/**")
                        .allowedOrigins("http://localhost:8082","http://192.168.1.7:8082")
                        .allowedHeaders("*")
                        .allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
                WebMvcConfigurer.super.addCorsMappings(registry);


            }
        };
        return configurer;
    }


    @Bean
    public Config testConfig(){
        return new Config();
    }

}
