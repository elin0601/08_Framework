package edu.kh.project.member.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {
	
	/** 로그인 SQL 실행
	 * @param memberEmail
	 * @return loginMember
	 */
	public Member login(String memberEmail);

	/** 회원 가입SQL 실행
	 * @param inputMember
	 * @return result
	 */
	public int signup(Member inputMember);

	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return count
	 */
	public int checkEmail(String memberEmail);

	/** 닉네임 중복 검사
	 * @param memberNickname
	 * @return count
	 */
	public int checkNickname(String memberNickname);

	/** 조회버튼 클릭 시 회원 조회
	 * @return
	 */
	public List<Member> selectMemberList();


		
}
