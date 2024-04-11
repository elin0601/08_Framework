package edu.kh.project.board.model.service;

import java.util.List;
import java.util.Map;

public interface BoardService {
	
	/** 게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList(); // 컬럼이 두개라 String 보단 Map으로 묶음 -> [{"K":V, "K":V}] 형식이 여러개 생성됨 -> Map들을 한 번에 담아 올 수 있는 List 이용

}
