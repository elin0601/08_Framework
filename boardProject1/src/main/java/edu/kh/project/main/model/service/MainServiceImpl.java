package edu.kh.project.main.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.main.model.mapper.MainMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MainServiceImpl implements MainService {

	private final MainMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	// 비밀번호 초기화
	@Override
	public int resetPw(int inputNo) {

		String pw = "pass01!";
		
		String encPw = bcrypt.encode(pw);
		
		Map<String, Object> map = new HashMap<>();
		map.put("inputNo", inputNo);
		map.put("encPw", encPw);
		
		return mapper.resetPw(map);
	}
	
	
	// 탈퇴 회원 복구
	@Override
	public int restorationNo(int inputNo) {// 지수씨 어디갔지.... 왜 안들어와.... 도망갔나봐요 어쩌죠
		
		return mapper.restorationNo(inputNo);
	}
	
	@Override
	public int deleteNo(int inputNo) {
		
		
		return mapper.deleteNo(inputNo);
	}
}
