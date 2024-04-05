package edu.kh.project.myPage.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// @Builder : 빌더 패턴을 이용해 객체 생성 및 초기화를 쉽게 진행

@Getter
@Setter
@Builder
public class UploadFile {
	
	private int fileNo;
	private String filePath;
	private String fileOriginalName;
	private String fileRename;
	private String fileUploadDate;
	private int memberNo;
	
	private String memberNickname; // join으로 가져옴

}
