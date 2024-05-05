package edu.kh.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MusicProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicProjectApplication.class, args);
	}

}
