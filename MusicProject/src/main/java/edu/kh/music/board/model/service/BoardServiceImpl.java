package edu.kh.music.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.music.board.model.dto.Board;
import edu.kh.music.board.model.dto.Pagination;
import edu.kh.music.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper mapper;

	
	// 게시판 종류 조회
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	}
	
	// 카테고리 버튼
	@Override
	public List<Map<String, Object>> selectCategoryList(int boardCode) {
		return mapper.selectCategoryList(boardCode);
	}
	
	
	// 게시글 목록 조회
	@Override
	public Map<String, Object> selectBoardList(int boardCode, int cp) {
		
		int listCount = mapper.getListCount(boardCode);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp-1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.selectBoardList(boardCode, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
	}
	
	// 특정 카테고리 게시판 목록
	@Override
	public Map<Object, Object> selectCategoryBoardList(int categoryNo, int cp) {
		
		int listCount = mapper.getCategoryCount(categoryNo);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp-1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.selectCategoryBoardList(categoryNo, rowBounds);
		
		Map<Object, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
	}
	
	
	// 게시글에 하나하나에 카테고리 가져오기
	@Override
	public String getCategoryName(int categoryNo) {
		return mapper.getCategoryName();
	}

	// 게시글 상세조회
	@Override
	public Board selectOne(Map<String, Integer> map) {
		return mapper.selectOne(map);
	}
	
	// 좋아요
	@Override
	public int boardLike(Map<String, Integer> map) {
		
		int result = 0;
		
		if(map.get("likeCheck") == 1) {
			result = mapper.deleteBoardLike(map);
			
		} else {
			result = mapper.addBoardLike(map);
		}
		
		if(result > 0) {
			return mapper.selectLikeCount(map.get("boardNo"));
			
		}
		
		return -1;
	}

	// 조회수 증가
	@Override
	public int updateReadCount(int boardNo) {
		
		int result = mapper.updateReadCount(boardNo);
		
		if(result > 0) {
			return mapper.selectReadCount(boardNo);
		}
		
		return -1;
	}
	
	// 메인에 미니 게시판
	@Override
	public List<Board> selectMiniList(int boardCode) {
		int limit = 7;
		int offset = 0;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return mapper.selectMiniList(boardCode, rowBounds);
	}
	
	// 검색기능
	@Override
	public Map<String, Object> searchList(String keyword, int cp) {
		
		int listCount = mapper.getSearchCount(keyword);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit(); int offset = (cp-1) * limit; 
		RowBounds rowBounds = new RowBounds(offset, limit);
		  
		  List<Board> boardList = mapper.searchList(keyword, rowBounds);
		  
		  Map<String, Object> map = new HashMap<>(); 
		  
		  map.put("pagination", pagination);
		  map.put("boardList", boardList);
		  
		  return map;
	}
	
	
	@Override
	public List<Board> selectHotBoard() {

		int limit = 7;
		int offset = 0;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return mapper.selectHotBoard(rowBounds);
	}
	
	
	
	
	
	
	
	
}
	

