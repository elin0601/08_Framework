package edu.kh.project.board.controller;

import java.util.HashMap;
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

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;
import edu.kh.project.board.model.service.BoardService;
import edu.kh.project.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
@Slf4j
public class BoardController {
	
	private final BoardService service;
	
	/** 게시글 목록 조회
	 * @param boardCode : 게시판 종류 구분
	 * @param cp : 현재 조회 요청한 페이지( 없으면 1)
	 * @return
	 * 
	 * 
	 * - /board/000
	 *  /board 이하 레벨 자리에 숫자로된 요청 주소가 
	 *  작성되어 있을 때만 동작 -> 정규 표현식 이용
	 *  
	 *  [0-9] : 한 칸에 0~9사이 숫자 입력 가능
	 *  + : 하나 이상
	 *  
	 *  [0-9]+ : 모든 숫자
	 */
	@GetMapping("{boardCode:[0-9]+}")
	public String selectBoardList(
			@PathVariable("boardCode") int boardCode,
			@RequestParam(value="cp", required=false, defaultValue="1") int cp,
			Model model){ 
		// @PathVariable : 주소에 특정한 부분에 적힌 값과 일치하는 값을 가져와 변수에 담아서 사용
		
		log.debug("boardCode : " + boardCode);
		
		
		// 조회 서비스 호출 후 결과 반환
		Map<String, Object> map = service.selectBoardList(boardCode, cp);
		
		model.addAttribute("pagination", map.get("pagination") );
		model.addAttribute("boardList", map.get("boardList") );
		
		return "board/boardList"; // boardList.html로 forward
	}
	
	
	
	// 상세 조회 요청 주소
	// /board/1/1998?cp=1
	// /board/2/2000?cp=2
	
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(
			@PathVariable("boardCode") int boardCode, 
			@PathVariable("boardNo") int boardNo,
			Model model,
			RedirectAttributes ra,
			@SessionAttribute(value="loginMember", required=false) Member loginMember) {
		
		// @SessionAttribute(value="loginMember", required=false)
		// - @SessionAttribute : Session에서 속성 값 얻어오기
		// - value="loginMember" :  속성의 Key 값 loginMember
		// - required = false : 필수 X (없어도 오류X)
		//	-> 해당 속성 값이 없으면 null 반환
		
		
		// 게시글 상세 조회 서비스 호출
		
		// 1) Map으로 전달할 파라미터 묶기
		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		
		// 로그인 상태인 경우에만 memberNo 추가
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}

		
		
		// 2) 서비스 호출
		Board board = service.selectOne(map);
		
		String path = null;
		
		// 조회 결과가 없는 경우
		if(board == null) {
			path = "redirect:/board/" + boardCode; // 목록 재요청
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다.");
		} 
		
		// 조회 결과가 있을 경우
		else {
			path = "board/boardDetail";
			
			// board - 게시글 상세 조회 + imageList + commentList
			model.addAttribute("board", board);
			
			
			// 조회된 이미지 목록(imageList)가 있을 경우
			if(!board.getImageList().isEmpty()) {
				
				BoardImg thumbnail = null;
				
				// imageList의 0번 인덱스 == 가장 빠른 순서(imageOrder)
				
				// 이미지 목록의 첫번째 행이 순서가 0 == 썸네일인 경우
				if(board.getImageList().get(0).getImgOrder() == 0) {
					
					thumbnail = board.getImageList().get(0);
				}
				
				// 썸네일이 있을 때 / 없을 때 
				// 출력되는 이미지 시작 인덱스 지정하는 코드
				// (썸네일 제외하고 인덱스 계산)
				model.addAttribute("thumbnail", thumbnail);
				model.addAttribute("start", thumbnail != null ? 1 : 0);
				
			}
			
		}
		
		return path;
	}
	
	
	/** 게시글 좋아요 체크 / 해제
	 * @return result
	 */
	@ResponseBody
	@PostMapping("like")
	public int boardLike(
			@RequestBody Map<String, Integer> map) {
		
		return service.boardLike(map);
	}
}








