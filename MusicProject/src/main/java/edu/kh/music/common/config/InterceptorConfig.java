package edu.kh.music.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.music.common.BoardTypeInterceptor;
import edu.kh.music.common.interceptor.BoardNameInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Bean
	public BoardTypeInterceptor boardTypeInterceptor() {
		return new BoardTypeInterceptor();
	}
	
	@Bean
	public BoardNameInterceptor boardNameInterceptor() {
		return new BoardNameInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(boardTypeInterceptor())
		.addPathPatterns("/**")
		
		.excludePathPatterns("/css/**",
							 "/js/**",
							 "/images/**",
							 "/favicon.ico");
		
		registry.addInterceptor(boardNameInterceptor())
		.addPathPatterns("/board/**", "/editBoard/**")


		.excludePathPatterns("/board/selectMiniList/**", "/board/search/**", "/board/hotBoard/**");

	}

}
