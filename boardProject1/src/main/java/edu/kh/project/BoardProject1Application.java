package edu.kh.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스프링 스케쥴러 활성화 하는 어노테이션
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BoardProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(BoardProject1Application.class, args);
	}

}
