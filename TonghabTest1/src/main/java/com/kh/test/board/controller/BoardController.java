package com.kh.test.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.test.board.model.dto.Board;
import com.kh.test.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class BoardController {

	private final BoardService service;

	@PostMapping("search")
	public String search(Board board, Model model) {

		List<Board> boardSearch = service.search(board);

		String path = null;

		if (!boardSearch.isEmpty())
			path = "/searchSuccess";
		else
			path = "/searchFail";
		
		model.addAttribute("boardSearch", boardSearch);

		return path;

	}

}
