package edu.kh.music.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.music.board.model.dto.Comment;
import edu.kh.music.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {
	
	private final CommentService service;
	
	// 댓글 조회
	@GetMapping(value="", produces="application/json")
	public List<Comment> select(
			@RequestParam("boardNo") int boardNo){
		
		return service.select(boardNo);
	}
	
	// 댓글 작성
	@PostMapping("")
	public int insertComment(@RequestBody Comment comment) {
		return service.insertComment(comment);
	}
	
	// 댓글 삭제
	@DeleteMapping("")
	public int deleteComment(@RequestBody int comment) {
		return service.deletecomment(comment);
	}
	
	// 댓글 수정
	@PutMapping("")
	public int updateComment(@RequestBody Comment comment) {
		return service.updateComment(comment);
	}

}
