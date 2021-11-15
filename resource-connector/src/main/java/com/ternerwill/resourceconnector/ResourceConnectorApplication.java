package com.ternerwill.resourceconnector;

import com.ternerwill.resourceconnector.util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ResourceConnectorApplication {

	public static void main(String[] args) {
		ApplicationContext container = SpringApplication.run(ResourceConnectorApplication.class, args);

	}

}
