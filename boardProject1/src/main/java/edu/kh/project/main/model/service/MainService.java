package edu.kh.project.main.model.service;

public interface MainService {

	/** 비밀번호 초기화
	 * @param inputNo
	 * @return result
	 */
	int resetPw(int inputNo);

	/** 탈퇴 회원 복구
	 * @param inputNo
	 * @return
	 */
	int restorationNo(int inputNo);

	/** 회원 영구 삭제
	 * @param inputNo
	 * @return
	 */
	int deleteNo(int inputNo);

}
