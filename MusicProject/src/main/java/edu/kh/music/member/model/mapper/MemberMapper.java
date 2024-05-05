package edu.kh.music.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.music.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/** 로그인
	 * @param memberPw
	 * @return
	 */
	Member login(String memberEmail);

	/** 회원가입
	 * @param map
	 * @return
	 */
	int signup(Member member);

	/** 회원가입(이메일 중복 검사)
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
	 * @param map
	 * @return
	 */
	int updatePw(Map<String, Object> map);

	int selectMember(int memberNo);

	
	


}
