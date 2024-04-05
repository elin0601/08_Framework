package edu.kh.project.myPage.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;

public interface MyPageService {

	/** 회원 정보 수정
	 * @param inputMember
	 * @param memberAddress
	 * @return result
	 */
	int updateInfo(Member inputMember, String[] memberAddress);

	/** 비밀번호 수정
	 * @param currentPw
	 * @param newPw
	 * @param memberNo
	 * @return result
	 */
	int changePw(String currentPw, String newPw, int memberNo);

	/** 회원 탈퇴
	 * @param memberPw
	 * @param memberNo
	 * @return result
	 */
	int secession(String memberPw, int memberNo);

	/** 파일 업로드 테스트 1
	 * @param uploadFile
	 * @return path
	 */
	String fileUpload1(MultipartFile uploadFile) throws IllegalStateException, IOException ;

	/** 파일 업로드 테스트2 (+ DB)
	 * @param uploadFile
	 * @param memberNo
	 * @return
	 */
	int fileUpload2(MultipartFile uploadFile, int memberNo) throws IllegalStateException, IOException;

}
