package edu.kh.music.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.music.board.model.dto.Board;
import edu.kh.music.board.model.dto.BoardImg;

@Mapper
public interface EditBoardMapper {

	// 게시글 작성
	int boardInsert(Board inputBoard);

	// 게시글 모두 삽입
	int insertUploadList(List<BoardImg> uploadList);

	// 게시글 삭제
	int deleteBoard(Board board);

	// 게시글 수정
	int boardUpdate(Board inputBoard);

	// 이미지 삭제
	int deleteImage(Map<String, Object> map);

	// 이미지 수정
	int updateImage(BoardImg img);

	// 이미지 1 개 삽입
	int insertImage(BoardImg img);

}
