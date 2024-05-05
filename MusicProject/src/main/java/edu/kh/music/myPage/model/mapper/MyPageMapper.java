package edu.kh.music.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.music.board.model.dto.Board;
import edu.kh.music.board.model.dto.Comment;
import edu.kh.music.member.model.dto.Member;

@Mapper
public interface MyPageMapper {

	// 회원 비밀번호 조회
	String selectPw(int memberNo);

	// 비밀번호 변경
	int changePw(Map<String, Object> map);

	// 회원 탈퇴
	int secession(int memberNo);

	// 회원 수정
	int updateInfo(Member member);

	// 프로필 이미지 수정
	int updateProfile(Member mem);

	// 내가 쓴 게시글 수 조회
	int getListCount(int memberNo);

	// 내가 쓴 게시글 조회
	List<Board> selectMyBoard(int memberNo, RowBounds rowBounds);

	// 내가 쓴 댓글 수 조회
	int getCommentCount(int memberNo);

	// 내가 쓴 댓글 조회
	List<Comment> selectMyComment(int memberNo, RowBounds rowBounds);






	

}
