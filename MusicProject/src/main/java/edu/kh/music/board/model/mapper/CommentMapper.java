package edu.kh.music.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.music.board.model.dto.Comment;

@Mapper
public interface CommentMapper {

	/** 댓글 조회
	 * @param boardNo
	 * @return
	 */
	List<Comment> select(int boardNo);

	/** 댓글 작성
	 * @param comment
	 * @return
	 */
	int insertComment(Comment comment);

	/** 댓글 삭제
	 * @param comment
	 * @return
	 */
	int deleteComment(int comment);

	/** 댓글 수정
	 * @param comment
	 * @return
	 */
	int updateComment(Comment comment);

}
