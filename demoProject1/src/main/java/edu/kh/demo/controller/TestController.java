package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Java 객체 : new 연산자에 의해 Heap 영역에 
//			  클래스에 작성된 내용대로 생성된 것

// instance : 개발자가 만들고 관리하는 객체

// Bean : Spring Container(Spring)가 만들고 관리하는 객체

@Controller // 요청/응답을 제어할 컨트롤러 역할 명시
			// + Bean 으로 등록 (== 객체로 생성해서 스프링이 관리)
public class TestController {
	
	// 기존 Servlet : 클래스 단위로 하나의 요청만 처리 가능
	
	// Spring : 메서드 단위로 요청 처리 가능
	
	// @RequestMapping("요청 주소)
	// - 요청 주소를 처리할 메서드를 매핑하는 어노테이션
	
	// 1) 메서드에 작성 : 
	// - 요청 주소와 메서드를 매핑
	// - GET/POST 가리지 않고 매핑 (속성을 통해서 지정 가능)
	
	// 2) 클래스에 작성
	// - 공통 주소를 매핑
	// ex) /todo/insert,  /todo/select,  /todo/update
	
	// @RequestMapping("todo")  // 폴더명처럼 앞부분 공통 주소 작성
	// public class 클래스명 {
	
	//   @RequesrMapping("insert")  // /todo/insert 매핑
	//	 public String 메서드명(){}
	
	//   @RequesrMapping("update")  // /todo/udpate 매핑
	//	 public String 메서드명(){}
	
	// }
	
	
	/*************************************************/
	// Spring Boot Controller 에서
	// 특수한 경우를 제외하고
	// 매핑 주소 제일 앞에 "/"를 작성하지 않는다!!!!
	/*************************************************/
	
	
	@RequestMapping("test") // /test 요청 시 처리할 메서드 매핑
	public String testMethod() {
		System.out.println("/test 요청 받음");
		
		/* Controller 메서드의 반환형이 String인 이유
		 * - 메서드에서 반환되는 문자열이
		 *   forward 할 html 파일의 경로가 되기 때문!! 
		 * */
		
		
		/* Thymeleaf : JSP 대신 사용하는 템플릿 엔진
		 * 
		 * classpath : == src/main/resources
		 * 접두사 : classpath:/templates/
		 * 접미사 : .html
		 * */
		
		// src/main/resources/template/test.html
		// test
		
		return "test"; // 접두사 + 반환값 + 접미사 경로의 html로 forward
		
						// String path = "/WEB-INF/views/ 이름.jsp";
						// req.getRequestDispatcher(path).forward(req.resp); 을 단순화 시킨 코드
		
		/* 접두사, 접미사 forward 설정은 View Resolver 객체가 담당 */ 
	}
	
}
