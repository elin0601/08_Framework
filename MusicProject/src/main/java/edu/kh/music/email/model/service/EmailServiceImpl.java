package edu.kh.music.email.model.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.music.email.model.mapper.EmailMapper;
import edu.kh.music.member.model.dto.Member;
import edu.kh.music.member.model.mapper.MemberMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	
	private final EmailMapper mapper;
	
	
	private final JavaMailSender mailSender;
	private final SpringTemplateEngine engine; // html -> java로 변환
	
	// 이메일 보내기
	@Override
	public String sendEmail(String string, String authEmail) {
		
		String authKey = createAuthKey();
		
		try {
			
			String message = null;
			
			switch(string) {
			case "sendMail" : message = "[[회원 가입 인증 번호 입니다.]]";
			break;
			}
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			mimeMessageHelper.setTo(authEmail);
			mimeMessageHelper.setSubject(message);
			mimeMessageHelper.setText(loadHtml(authKey, string), true);
			
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("authKey", authKey);
		map.put("authEmail", authEmail);
		
		int result = mapper.updateAuthKey(map);
		
		if(result == 0) result = mapper.insertAuthKey(map);
		
		if(result == 0) return null;
		
		return authKey;
	}
	
	public String loadHtml(String authKey, String string) {
		
		Context context = new Context();
		context.setVariable("authKey", authKey);
		return engine.process("email/" + string, context);
	}
	
	
	//--------- 인증 번호 생성 ---------- 
	
	public String createAuthKey() {
		
		String key= "";
		
		int[] temp = new int[6];
		
		// 인증 번호 숫자 6자s
		for(int i = 0; i < 6; i++) {
			int num = (int)(Math.random() * 10);
			temp[i] = num;
			key+=num;
		}
		
		return key;
	}
	
	
	// 이메일 인증 번호 확인
	@Override
	public int checkAuthKey(Map<String, Object> map) {
		
		Member member = new Member();
		
		int result = mapper.deleteAuthKey(member.getMemberEmail());
		
		if(result == 0) return mapper.checkAuthKey(map);
		 
		else return mapper.updateAuthKey(map);
		
	}
	
	
	
}
