package edu.kh.music.member.model.service;

import java.util.Map;

import edu.kh.music.member.model.dto.Member;

public interface MemberService {

	/** 로그인
	 * @param member
	 * @return
	 */
	Member login(Member member);

	/** 회원가입
	 * @param member
	 * @return
	 */
	int signup(Member member);

	/** 회원가입 (이메일 중복 검사)
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

	/** 회원가입 (닉네임 중복 검사)
	 * @param memberNickname
	 * @return
	 */
	int checkNickname(String memberNickname);
	
	/** 아이디 찾기
	 * @param member
	 * @return
	 */
	String findId(Member member);

	/** 비밀번호 찾기
	 * @param member
	 * @return
	 */
	Integer findPw(Member member);

	/** 비밀번호 변경
	 * @param newPw
	 * @param member
	 * @param memberNickname 
	 * @param member 
	 * @return
	 */
	int updatePw(String newPw, Member member);

	/** 빠른 로그인
	 * @param ㅓmemberEmail
	 * @return
	 */
	Member quickLogin(String memberEmail);


}
