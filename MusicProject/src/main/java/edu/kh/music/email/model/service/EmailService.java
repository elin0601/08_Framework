package edu.kh.music.email.model.service;

import java.util.Map;

public interface EmailService {

	/** 이메일 보내기
	 * @param string
	 * @param authEmail
	 * @return
	 */
	String sendEmail(String string, String authEmail);

	/** 이메일 인증번호 확인
	 * @param map
	 * @return
	 */
	int checkAuthKey(Map<String, Object> map);

}
