package com.kh.test.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.test.customer.model.dto.Customer;
import com.kh.test.customer.model.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class CustomerController {

	private final CustomerService service;
	
	@GetMapping("insert")
	public String insert() {
		return "/";
	}
	
	@PostMapping("insert")
	public String insert(
			Customer ct,
			Model model,
			RedirectAttributes ra) {
		
		String customerName = ct.getCustomerName();
		
		int result = service.insert(ct);
		
		String message = null;
		
		
		if(result>0) message = "등록 되었습니다.";
		
		ra.addFlashAttribute("message", message);
		model.addAttribute("customerName", customerName);
		
		return "result";
	}
}
