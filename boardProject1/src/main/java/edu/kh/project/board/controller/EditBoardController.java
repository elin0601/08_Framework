package edu.kh.project.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.service.BoardService;
import edu.kh.project.board.model.service.EditBoardService;
import edu.kh.project.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
/**
 * 
 */
/**
 * 
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("editBoard")
@Slf4j
public class EditBoardController {

	private final EditBoardService service;

	private final BoardService boardService;

	/**
	 * 게시글 작성 화면 전환
	 * 
	 * @return board/boardWrite
	 */
	@GetMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(@PathVariable("boardCode") int boardCode) {

		return "board/boardWrite";
	}

	/**
	 * 게시글 작성
	 * 
	 * @param boardCode   : 어떤 게시판에 작성한 글인지 구분
	 * @param inputBoard  : 입력된 값(제목, 내용) + 추가 데이터 저장(커맨드 객체)
	 * @param loginMember : 로그인 한 회원 번호 얻어오는 용도
	 * @param imgaes      : 제출된 file 타입 input 태그 데이터들
	 * @param ra          : 리다이렉트 시 request scope로 데이터 전달
	 * 
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@PostMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(@PathVariable("boardCode") int boardCode, @ModelAttribute Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember, @RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra) throws IllegalStateException, IOException {

		/*
		 * *** 전달되는 파라미터 확인 ***
		 * 
		 * List<MultipartFile> - 5개 모드 업로드 O => 0~4번 인덱스에 파일 저장됨 - 5개 모드 업로드 X => 0~4번
		 * 인덱스 파일 저장 X - 2번 인덱스만 업로드 => 2번 인덱스만 파일 저장 0, 1, 3, 4번 인덱스는 저장 X
		 * 
		 * 
		 * [문제점] - 파일이 선택되지 않은 input 태그도 제출되고 있음!! (제출은 되었는데 데이터는 ""(빈칸))
		 * 
		 * -> 파일 선택이 안된 input 태그 값을 서버에 저장하려고 하면 오류 발생함!!
		 * 
		 * [해결방법] - 무작정 서버에 저장X -> 제출된 파일이 있느지 확인하는 로직을 추가 구성
		 * 
		 * 
		 * + List 요소의 index 번호 == IMG_ORDER 와 같음
		 */

		// 1. boardCode, 로그인한 회원 번호를 inputBoard에 세팅
		inputBoard.setBoardNo(boardCode);
		inputBoard.setMemberNo(loginMember.getMemberNo());

		// 2. 서비스 메서드 호출 후 결과를 반환 받기
		// -> 성공 시 [상세 조회] 를 요청할 수 있도록
		// 삽입된 게시글 번호 반환 받기
		int boardNo = service.boardInsert(inputBoard, images);

		// 3. 서비스 결과에 따라 message, redirect 경로 지정

		String path = null;
		String message = null;

		if (boardNo > 0) {
			path = "/board/" + boardCode + "/" + boardNo; // 상세 조회
			message = "게시글이 작성 되었습니다.";

		} else {
			path = "insert";
			message = "게시글이 작성 실패";
		}

		ra.addFlashAttribute("message", message);

		// 게시글 작성(INSERT) 성공 시 -> 작성된 글 상세 조회로 redirect
		return "redirect:" + path;
	}
	
	
	
	/* 게시글 삭제 */
	
//	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/delete")
//	@PostMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/delete")
	

	/**
	 * 게시글 삭제
	 * 
	 * @param boardCode
	 * @param boardNo
	 * @return
	 */
/*	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/delete")
	public String boardDelete(
			@PathVariable("boardCode") int boardCode, 
			@PathVariable("boardNo") int boardNo,
			Board board) {

		int result = service.boardDelete(board);

		String path = null;

		if (result > 0)
			path = "/board/" + boardCode;
		else
			path = "delete";

		return "redirect:" + path;
	}
*/ // =====> 내 풀이
	
	
	
	/**
	 * ******** 강사님 풀이 ********
	 */
	@RequestMapping(value="{boardCode:[0-9]+}/{boardNo:[0-9]+}/delete", method= {RequestMethod.GET, RequestMethod.POST})
	public String boardDelete(
			@PathVariable("boardCode") int boardCode,
			@PathVariable("boardNo") int boardNo,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@SessionAttribute("loginMember") Member loginMemebr,
			RedirectAttributes ra
			) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		map.put("memberNo", loginMemebr.getMemberNo());
		
		int result = service.boardDelete(map);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			path = String.format("/board/%d", boardCode);
			message = "삭제 되었습니다.";
			
		} else {
			path = String.format("/board/%d/%d?cp=%d", boardCode, boardNo, cp);
			message = "삭제 실패";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	


	/* 게시글 수정 화면 전환 */

	/**
	 * @param boardCode   : 게시판 종류
	 * @param boardNo     : 게시글 번호
	 * @param loginMember : 로그인한 회원이 작성한 글이 맞는지 검사하는 용도
	 * @param model       : 포워드 시 request scope로 값 전달
	 * @param ra          : 리다이렉트 시 request scope로 값 전달
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(
			@PathVariable("boardCode") int boardCode, 
			@PathVariable("boardNo") int boardNo,
			@SessionAttribute("loginMember") Member loginMember, 
			Model model, 
			RedirectAttributes ra) {

		// 수정 화면에 출력할 제목 / 내용 / 이미지 조회
		// -> 게시글 상세 조회

		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);

		// BoardService.selecOne(map) 호출
		Board board = boardService.selectOne(map);

		String message = null;
		String path = null;

		if (board == null) {
			path = "redirect:/"; // 메인 페이지
			message = "해당 게시글이 존재하지 않습니다.";

			ra.addFlashAttribute("message", message);

		} else if (board.getMemberNo() != loginMember.getMemberNo()) {

			// 해당 글 상세 조회
			path = String.format("redirect:/board/%d/%d", boardCode, boardNo);
			message = "자신이 작성한 글만 수정할 수 있습니다.";

			ra.addFlashAttribute("message", message);

		} else {

			path = "board/boardUpdate";
			model.addAttribute("board", board);
		}

		return path;
	}

	/**
	 * 게시글 수정
	 * 
	 * @param boardCode   : 게시판 종류
	 * @param boardNo     : 수정할 게시글 번호
	 * @param inputBoard  : 커맨드 객체(제목, 내용)
	 * @param loginMember : 로그인한 회원 번호 이용(로그인 == 작성자)
	 * @param images      : 제출된 input type="file" 모든 요소
	 * @param ra          : 리다이렉트 시 request scope 로 값 전달
	 * @param deleteOrder : 삭제된 이미지 순서가 기록된 문자열(1,2,3)
	 * @param queryStirng : 수정 성공 시 이전 파라미터 유지(cp, 검색어)
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@PostMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(
			@PathVariable("boardCode") int boardCode, 
			@PathVariable("boardNo") int boardNo,
			@ModelAttribute Board inputBoard, 
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra,
			@RequestParam(value = "deleteOrder", required = false) String deleteOrder,
			@RequestParam(value = "queryStirng", required = false, defaultValue = "") String queryString)
			throws IllegalStateException, IOException {

		// 1. 커맨드 객체(inputBoard)에 boardCode, boardNo, memberNo 세팅
		inputBoard.setBoardCode(boardCode);
		inputBoard.setBoardNo(boardNo);
		inputBoard.setMemberNo(loginMember.getMemberNo());

		// -> inputBoard (제목, 내용, boardCode, boardNo, memberNo)

		// 2. 게시글 수정 서비스 호출 후 결과 반환 받기
		int result = service.boardUpdate(inputBoard, images, deleteOrder);

		// 서비스 결과에 따라 응답 제어
		String message = null;
		String path = null;

		if (result > 0) {
			message = "게시글이 수정 되었습니다.";
			path = String.format("/board/%d/%d%s", boardCode, boardNo, queryString);

		} else {
			message = "수정 실패";
			path = "update"; // 수정 화면 전환 상대 경로
		}

		ra.addFlashAttribute("message", message);
		return "redirect:" +  path;
	}
}
