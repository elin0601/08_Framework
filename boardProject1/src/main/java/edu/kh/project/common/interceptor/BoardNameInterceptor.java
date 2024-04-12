package edu.kh.project.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardNameInterceptor implements HandlerInterceptor{

	// 후처리 (Controller에서 Dispatcher Servlet 사이)
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		// application scope 에서 boardTypeList 얻어오기
		ServletContext appication = request.getServletContext();
		
		List<Map<String, Object>> boardTypeList = (List<Map<String, Object>>)appication.getAttribute("boardTypeList");
		
		// log.debug(boardTypeList.toString());

		
		// Uniform Resurce Identifier : 통합 자원 식별자
		// - 자원 이름(주소)만 봐도 무엇인지 구별할 수 있는 문자열
		
		// uri : /board/1
		// url : location/board/1
		
		String uri = request.getRequestURI();
		// log.debug("uri : " + uri);
		
										// ["", "board", "1"] -- 2번 인덱스를 가져와 숫자로 형변환
		int boardCode = Integer.parseInt(uri.split("/")[2]);
		
		
		// boardTypeList 에서 boardCode를 하나씩 꺼내서 비교
		for( Map<String, Object> boardType : boardTypeList ) {
			
			// object -> String -> int 순서로 자료형 변환
			
			// String.valueOf(값) : String으로 변환
			int temp = Integer.parseInt( String.valueOf(boardType.get("boardCode")) );
			
			
			// 비교 결과가 같다면
			// request scope 에 boardName을 추가
			if( temp == boardCode) {
				request.setAttribute("boardName", boardType.get("boardName"));
				break;
			}
		}
		
		
		// boardTypeList
		 // [{boardCode : 1, boardName = 공지}, {boardCode : 2, boarName = 자유} .... ]
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	
}
