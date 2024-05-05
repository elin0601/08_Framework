package edu.kh.music.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.music.member.model.dto.Member;
import edu.kh.music.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	// 로그인
	@Override
	public Member login(Member member) {
		
		// 입력 받은 pw
//		String pw = member.getMemberPw();
		
		
		Member loginMember = mapper.login(member.getMemberEmail());
		
		if(loginMember == null) return null;
		
		if( !bcrypt.matches(member.getMemberPw(), loginMember.getMemberPw()) ) return null;
		
		loginMember.setMemberPw(null);
		
		return loginMember;
	}
	
	
	// 회원가입
	@Override
	public int signup(Member member) {
		
		if(member.getMemberAddress() == null) member.setMemberAddress(null);
		
		String bcryptPw = bcrypt.encode(member.getMemberPw());
		member.setMemberPw(bcryptPw);
		
		return mapper.signup(member);
	}
	
	
	// 회원가입(이메일 중복 검사)
	@Override
	public int checkEmail(String memberEmail) {
		return mapper.checkEmail(memberEmail);
	}
	
	
	// 회원가입 (닉네임 중복 검사)
	@Override
	public int checkNickname(String memberNickname) {
		return mapper.checkNickname(memberNickname);
	}
	
	
	// 아이디 찾기
	@Override
	public String findId(Member member) {
		return mapper.findId(member);
	}
	
	
	// 비밀번호 찾기
	@Override
	public Integer findPw(Member member) {
		return mapper.findPw(member);
	}
	

	// 비밀번호 변경	
	@Override
	public int updatePw(String newPw, Member member) {
		
		String bcryptPw = bcrypt.encode(newPw);
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberNo", member.getMemberNo());
		map.put("newPw", bcryptPw);
		
		return mapper.updatePw(map);
	}

	
	// 빠른 로그인
	@Override
	public Member quickLogin(String memberEmail) {
		Member loginMember = mapper.login(memberEmail);
		
		// 탈퇴 또는 없는 회원
		if(loginMember == null) return null;
		
		// 조회된 비밀번호 null 변경
		loginMember.setMemberPw(null);
		
		return loginMember;
	}
}







