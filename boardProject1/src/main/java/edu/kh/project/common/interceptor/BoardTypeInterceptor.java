 package edu.kh.project.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.kh.project.board.model.service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/* Interceptor : 요청/응답 가로채는 객체 (Spring 지원)
 * 
 * Client <-> Filter <-> Dispatcher Servlet <-> Interceptor <-> Controller .... 
 * 
 * 
 * * HandlerInterceptor 인터페이스를 상속 받아서 구현 해야한다.
 * 
 * - preHandle (전처리) : Dispatcher Servlet -> Controller 사이 수행
 * 
 * - postHandle (후처리) : Controller -> Dispatcher Servlet 사이 수행
 * 
 * - afterCompletion (뷰 완성(forward 코드 해석) 후) :  View Resolver -> Dispatcher Servlet 사이
 * 
 * */

/* ServletContext
 * 
 * 1) 서블릿 환경에 관한 정보를 검색
 * 
 * 2) 동일한 웹 애플리케이션 내의 다른 서블릿과 상호 작용
 * 
 * 3) 웹 애플리케이션에서 접근 가능한 파일이나 데이터베이스 연결과 같은 리소스를 얻음
 * 
 * -> 블릿과 웹 컨테이너 간의 통신 채널 역할을 하며, 컨테이너의 기능과 리소스에 액세스하는 메서드를 제공
 * */

@Slf4j
public class BoardTypeInterceptor implements HandlerInterceptor{

	
	// BoardService 의존성 주입
	@Autowired // InterceptorConfig 오류 때문에 사용
	private BoardService service;
	
	
	// 전처리
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// application scope : 
		// - 서버 종료 시 까지 유지되는 Servlet 내장 객체
		// - 서버 내에 딱 한 개만 존재!
		//   --> 모든 클라이언트가 공용으로 사용
		
		
		// application scope 객체 얻어오기
		ServletContext application = request.getServletContext();
		
		
		// application scope에 boardTypeList가 없을 경우
		if(application.getAttribute("boardTypeList") == null) {
			
//			log.info("BoardTypeInterceptor - postHandler(전처리) 동작 실행");
			
			// boardTypeList 조회 서비스 호출
			List<Map<String, Object>> boardTypeList = service.selectBoardTypeList();
			
			// boardCode, boardName이 [ {"boardCoed" : boardName} ... ]
			// 모양으로 Map을 이용해서 묶음
			// Map을 이용해서 묶은 걸 List로 다시 타입 제한해서 List 타입으로 얻어옴
			
			// 조회 결과를 application scope에 추가
			application.setAttribute("boardTypeList", boardTypeList);
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	
	// 후처리
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {  
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	
	// 뷰 완성 후
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
