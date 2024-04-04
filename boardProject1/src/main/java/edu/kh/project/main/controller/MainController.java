package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.project.main.model.service.MainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller // Bean 등록
public class MainController {
	
	private final MainService service;

	@RequestMapping("/") // "/"요청 매핑(method 가리지 않음)
	public String mainPage() {
		
		return "common/main";
	}
	
	
	/** 비밀번호 초기화
	 * @param inputNo 초기화 할 회원 번호
	 * @return  result
	 */
	@ResponseBody
	@PutMapping("resetPw")
	public int resetPw(@RequestBody int inputNo) {
		
		return service.resetPw(inputNo);
	}
	
	
	/** 탈퇴 회원 복구
	 * @param inputNo
	 * @return result
	 */
	@ResponseBody
	@PutMapping("restorationNo")
	public int restorationNo(@RequestBody int inputNo) {
		
		return service.restorationNo(inputNo);
	}
	
	
	/** 회원 영구 삭제
	 * @param inputNo
	 * @return result
	 */
	@ResponseBody
	@DeleteMapping("deleteNo")
	public int deleteNo(@RequestBody int inputNo) {
		
		return service.deleteNo(inputNo);
	}
}
