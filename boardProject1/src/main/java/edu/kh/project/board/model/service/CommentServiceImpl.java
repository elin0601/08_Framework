package edu.kh.project.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{
	
	private final CommentMapper mapper;
	
	// 댓글 목록 조회
	@Override
	public List<Comment> select(int boardNo) {
		return mapper.select(boardNo);
	}
	
	
	// 댓글 등록
	@Override
	public int insert(Comment comment) {
		int result = mapper.insert(comment);
		if(result>0) return comment.getCommentNo();
		return 0;
	}

	
	// 댓글 삭제
	@Override
	public int delete(int commentNo) {
		return mapper.delete(commentNo);
	}

	
	// 댓글 수정
	@Override
	public int update(Comment comment) {
		return mapper.update(comment);
	}

}
