package edu.kh.project.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.project.myPage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {
	
	private final MyPageService service;
	
	/** 내 정보 조회/수정 화면으로 전환
	 * @return "myPage/myPage-info" 
	 */
	@GetMapping("info") // /myPage/info (GET)
	public String info() {
		
		// /templates/myPage/myPage-info.html로 forward
		return "myPage/myPage-info";
	}
}
