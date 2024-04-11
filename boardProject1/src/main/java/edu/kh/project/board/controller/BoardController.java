package edu.kh.project.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.project.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
@Slf4j
public class BoardController {
	
	private final BoardService service;
	
	@GetMapping("{boardCode}")
	public String selectBoardList(
			@PathVariable("boardCode") int boardCode) {
		
		// 주소에 적힌 변수명과 일치하는 값을 가져와 변수에 담아서 사용
		
		return null;
	}
}
