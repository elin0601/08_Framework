package edu.kh.music.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.music.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"authKey"})
@RequestMapping("email")
public class EmailController {
	
	private final EmailService service;
	
	@ResponseBody
	@PostMapping("sendMail")
	public int sendMail(
			@RequestBody String authEmail) {
		
		String authKey = service.sendEmail("sendMail", authEmail);
		
		if(authKey != null) return 1;
		
		return 0;
	}
	
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, Object> map) {
		
		// count 결과 반환
		return service.checkAuthKey(map);
	}
	
}
