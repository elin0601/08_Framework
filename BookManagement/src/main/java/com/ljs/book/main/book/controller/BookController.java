package com.ljs.book.main.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ljs.book.main.book.model.dto.Book;
import com.ljs.book.main.book.model.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

	private final BookService service;
	
	@GetMapping("insertBook")
	public String insertBook() {
		
		return "book/insertBook";
	}
	
	@PostMapping("insertBook")
	public String insertBook(Book bookInput,
			RedirectAttributes ra) {
		
		int result = service.insertBook(bookInput);
		 
		String message;
		String path;
		
		if(result > 0) {
			message = "등록 성공";
			path = "/";
			
			
		} else { 
			message="다시 입력해 주세요.";
			path = "insertBook";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	

	// 전체 조회
	@ResponseBody
	@GetMapping("selectList")
	public List<Book> selectList() {
		
		return service.selectList();
	}
	
	
	
	@GetMapping("updateBook")
	public String searchBook() {
		return "book/updateBook";
	}
	
	
	// 제목 검색
	@ResponseBody
	@PostMapping("updateBook")
	public List<Book> searchBook(
			@RequestBody String bookTitle) {
		
		List<Book> bookList = service.searchBook(bookTitle);
		
		/* model.addAttribute("bookList", bookList); */
			
		return bookList;
	}
	
	
	
	
}















