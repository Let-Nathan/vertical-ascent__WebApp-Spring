package com.webapp.verticalascent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class VerticalAscentApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(VerticalAscentApplication.class);
		Environment env = app.run(args).getEnvironment();
		int port = Integer.parseInt(env.getProperty("PORT", "420"));
		System.setProperty("server.port", String.valueOf(port));
	}
}
