package edu.kh.music.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardNameInterceptor implements HandlerInterceptor {
	
	@Override
	public void postHandle(HttpServletRequest request, 
						   HttpServletResponse response, 
						   Object handler,
						   ModelAndView modelAndView) throws Exception {
		
		ServletContext application = request.getServletContext();
		
		List<Map<String, Object>> boardTypeList = 
			(List<Map<String, Object>>)application.getAttribute("boardTypeList");
		
		String uri = request.getRequestURI();
		
		try {
			int boardCode = Integer.parseInt(uri.split("/")[2]);
			
			for(Map<String, Object> boardType : boardTypeList) {
				int temp = 
					Integer.parseInt(String.valueOf(boardType.get("boardCode")));
				
				if(temp == boardCode) {
					request.setAttribute("boardName", boardType.get("boardName"));
					break;
				}
			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
}
