package edu.kh.music.myPage.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.music.board.model.dto.Board;
import edu.kh.music.board.model.dto.Comment;
import edu.kh.music.member.model.dto.Member;

public interface MyPageService {

	// 비밀번호 변경
	int changePw(String currentPw, String newPw, int memberNo);

	// 회원 탈퇴
	int secession(String memberPw, Member loginMember);

	// 회원 정보 수정
	int updateProfile(Member member, MultipartFile profileImg, int memberNo, Member loginMember) throws IllegalStateException, IOException ;

	// 내가 쓴 글 조회
	Map<String, Object> selectMyBoard(int memberNo, int cp);

	// 내가 쓴 댓글 조회
	Map<String, Object> selectMyComment(int memberNo, int cp);






}
