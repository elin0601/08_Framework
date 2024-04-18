package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/* @RestController (Rest API 구축을 위해 사용하는 컨트롤러)
 * 
 *  == @Controller (요청/응답 제어 + Bean 등록)
 *    + @ResponseBody (응답 본문으로 데이터를 반환)
 * 
 * -> 모든 응답을 응답 본문(ajax)으로 반환하는 컨트롤러
 * */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("comment")
public class CommentController {
	
	private final CommentService service;
	
	// value 속성 : 매핑할 주소
	// produces 속성 : 응답할 데이터를 형식을 지정
	@GetMapping(value="",produces = "application/json" )
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		
		// HttpMessageConverter가
		// List -> JSON (문자열)로 변환해서 응답
		
		return service.select(boardNo);
	}
	
	
	@PostMapping("")
	public int insert() {
		return 0;
	}
	
	
	@PutMapping("")
	public int update() {
		return 0;
	}
	
	
	@DeleteMapping("")
	public int delete() {
		return 0;
	}
	

}
