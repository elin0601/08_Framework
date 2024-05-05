package edu.kh.music.board.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.music.board.model.dto.Board;
import edu.kh.music.board.model.dto.BoardImg;
import edu.kh.music.board.model.service.BoardService;
import edu.kh.music.member.model.dto.Member;
import jakarta.mail.internet.ParseException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService service;
	
	
	/** 게시글 목록 조회
	 * @param boardCode
	 * @param cp
	 * @param model
	 * @param paramMap
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}")
	public String selectBoardList(
		@PathVariable("boardCode") int boardCode,
		@RequestParam(value="categoryNo", required=false) Integer categoryNo,
		@RequestParam(value="cp", required=false, defaultValue="1") int cp,
		Model model
		
		) {
		
		// 카테고리 버튼이름 가져오기
		List<Map<String, Object>> categoryList = service.selectCategoryList(boardCode);
		
		Map<String, Object> map = null;

			map = service.selectBoardList(boardCode, cp);
			
			List<Board> boardList = (List<Board>) map.get("boardList");
			for(Board board : boardList) {
				if(board.getBoardCode() == 1 || board.getBoardCode() == 2) {
					String categoryName = service.getCategoryName(boardCode);
					board.setCategoryName(categoryName);
				}
			}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("categoryList", categoryList);
		
		return "board/boardList";
	}
	
	
	/** 특정 카테고리 게시글 목록 조회
	 * @param boardCode
	 * @param categoryNo
	 * @param cp
	 * @param model
	 * @param paramMap
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/category/{categoryNo:[0-9]+}")
	public String selectCategoryBoardList(
			@PathVariable("boardCode") int boardCode,
			@PathVariable("categoryNo") int categoryNo,
			@RequestParam(value="cp", required=false, defaultValue="1") int cp,
			Model model) {
		
		
		// 카테고리 버튼이름 가져오기
		List<Map<String, Object>> categoryList = service.selectCategoryList(boardCode);
		
		// 카테고리 게시글 리스트
		Map<Object, Object> map = service.selectCategoryBoardList(categoryNo, cp);
		List<Board> boardList = (List<Board>) map.get("boardList");
		
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("categoryNo", categoryNo);
		model.addAttribute("boardList", boardList);
		model.addAttribute("categoryList", categoryList);
		
		return "board/boardList";
	}

	
	
	// 게시글 상세 조회
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(
		@PathVariable("boardCode") int boardCode,
		@PathVariable("boardNo") int boardNo,
		@SessionAttribute(value="loginMember", required=false) Member loginMember,
		Model model,
		RedirectAttributes ra,
		HttpServletRequest req,
		HttpServletResponse resp) throws ParseException, Exception {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		Board board = service.selectOne(map);
		
		String path = null;
		
		if(board == null) {
			path = "redirect:/board/" + boardCode;
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다.");
			
		} else {
			if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
				
				Cookie[] cookies = req.getCookies();
				Cookie c = null;
				
				if(cookies != null) {
					for(Cookie temp : cookies) {
						
						if(temp.getName().equals("readBoardNo")) {
							c = temp;
							break;
						}
					}
				}
				
				int result = 0;
				
				if(c == null) {
					c = new Cookie("readBoardNo", "[" + boardNo + "]");
					result = service.updateReadCount(boardNo);
				}
				
				else {
					if(c.getValue().indexOf("[" + boardNo +"]") == -1) {
						c.setValue(c.getValue() + "[" + boardNo + "]");
						result = service.updateReadCount(boardNo);
					}
				}
				
				if(result > 0) {
					board.setReadCount(result);
					
					c.setPath("/");
					
					Calendar cal = Calendar.getInstance();
					cal.add(cal.DATE, 1);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					Date a = new Date();
				
					Date temp = new Date(cal.getTimeInMillis());
					Date b = sdf.parse(sdf.format(temp));
					long diff = (b.getTime() - a.getTime()) / 1000;
					
					c.setMaxAge((int)diff);
					resp.addCookie(c);
				}
			}
			
			path = "board/boardDetail";
			model.addAttribute("board", board);
			model.addAttribute("start", 0);
			
		}
		
		return path;
	}
	
	// 좋아요
	@ResponseBody
	@PostMapping("like")
	public int boardLike(
		@RequestBody Map<String, Integer> map) {
		
		return service.boardLike(map);
	}
	
	
	// 메인에 미니 보드
	@ResponseBody
	@GetMapping("selectMiniList/{boardCode:[0-9]+}")
	public List<Board> selectMiniList(
		@PathVariable("boardCode") int boardCode) {
		return service.selectMiniList(boardCode);
	}
	
	
	// 검색
	@GetMapping("search")
	public String search(
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp, 
			Model model,
			@RequestParam("keyword") String keyword) {
		
		Map<String, Object> map = service.searchList(keyword, cp);
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList")); 

		return "board/boardList";

	}
	
	@ResponseBody
	@GetMapping("hotBoard")
	public List<Board> hotBoard() {
		return service.selectHotBoard();
	}
	
	
	
	

}
