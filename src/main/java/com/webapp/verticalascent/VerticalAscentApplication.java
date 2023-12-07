package com.webapp.verticalascent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application class for Vertical Ascent.
 *
 * @author Nathan L.
 * @version 1.0
 */
@SpringBootApplication
@EnableScheduling
public class VerticalAscentApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(VerticalAscentApplication.class, args);
    }
}
