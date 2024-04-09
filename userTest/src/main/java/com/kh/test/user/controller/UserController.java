package com.kh.test.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.test.user.model.dto.User;
import com.kh.test.user.model.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class UserController {
	
	private final UserService service;

	@GetMapping("select")
	public String userSelect() {
		
		return "/";
	}
	
	@PostMapping("select")
	public String userSelect(
			User user,
			RedirectAttributes ra,
			Model model){
		
		
		List<User> userList = service.userSelect(user);
		String path=null;
		
		if(!userList.isEmpty()) {
			path="/searchSuccess";
		}
		
		else {
			path = "/searchFail";
		}
		
		 model.addAttribute("userList", userList);
		
		return path;
	}


}
