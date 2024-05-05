package edu.kh.music.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.music.board.model.dto.Board;
import edu.kh.music.board.model.service.BoardService;
import edu.kh.music.board.model.service.EditBoardService;
import edu.kh.music.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("editBoard")
public class EditBoardController {

	private final EditBoardService service;
	
	private final BoardService boardService;
	
	@GetMapping("/{boardCode:[0-9]+}/insert")
	public String insertBoard(
		@PathVariable("boardCode") int boardCode) {
		return "board/boardWrite";
	}
	
	

	@PostMapping("/{boardCode:[0-9]+}/insert")
	public String boardInsert(
		@PathVariable("boardCode") int boardCode,
		Board inputBoard,
		@SessionAttribute("loginMember") Member loginMember,
		@RequestParam("images") List<MultipartFile> images,
		RedirectAttributes ra) throws IllegalStateException, IOException {
		
		inputBoard.setBoardCode(boardCode);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		int boardNo = service.boardInsert(inputBoard,images);
		
		String path = null;
		String message = null;
		
		if(boardNo > 0 ) {
			path = "/board/" + boardCode + "/" + boardNo;
			message = "게시글이 작성되었습니다.";
		}else {
			path = "insert";
			message = "게시글 작성 실패";
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:" + path;
	}
	
	// 게시글 삭제
	@GetMapping("/{boardCode:[0-9]+}/{boardNo:[0-9]+}/delete")
	public String deleteBoard(
		@PathVariable("boardCode") int boardCode,
		@PathVariable("boardNo") int boardNo,
		Board board) {
		
		int result = service.deleteBoard(board);
		
		String path = null;
		
		if(result > 0) {
			path = "/board/" + boardCode;
		}else {
			path = "/editBoard/" + boardCode + "/" + boardNo;
		}
		
		return "redirect:" + path;
	}
	
	
	
	
	// 게시글 수정 화면 전환
	@GetMapping("/{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(
		@PathVariable("boardCode") int boardCode,
		@PathVariable("boardNo") int boardNo,
		@SessionAttribute("loginMember") Member loginMember,
		Model model, RedirectAttributes ra) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
	
		Board board = boardService.selectOne(map);
		
		String path = null;
		String message = null;
		
		if(board == null) {
			message = "해당 게시글이 존재하지 않습니다.";
			path = "redirect:/";
			ra.addFlashAttribute("message", message);
		} else if(board.getMemberNo() != loginMember.getMemberNo()) {
			message = "자신이 작성한 글만 수정할 수 있습니다.";
			path = "redirect:/board/" + boardCode + "/" + boardNo;
			ra.addFlashAttribute("message", message);
		} else {
			path = "board/boardUpdate";
			model.addAttribute("board", board);
		}
		
		return path;
	}
	
	
	
	@PostMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(
		@PathVariable("boardCode") int boardCode,
		@PathVariable("boardNo") int boardNo,
		Board inputBoard,
		@SessionAttribute("loginMember") Member loginMember,
		@RequestParam("images") List<MultipartFile> images,
		@RequestParam(value="deleteOrder", required=false) String deleteOrder,
		@RequestParam(value="querystring", required=false, defaultValue="") String querystring,
		RedirectAttributes ra
		) throws IllegalStateException, IOException {
		
		inputBoard.setBoardCode(boardCode);
		inputBoard.setBoardNo(boardNo);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		int result = service.boardUpdate(inputBoard, images, deleteOrder);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			message = "게시글이 수정 되었습니다.";
			path = String.format("/board/%d/%d%s", boardCode, boardNo, querystring);
		} else {
			message = "수정 실패";
			path = "update";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
