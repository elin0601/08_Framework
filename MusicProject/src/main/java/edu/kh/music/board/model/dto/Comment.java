package edu.kh.music.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

	private int commentNo;
	private String commentContent;
	private String commentWriteDate;
	private String commentDelFl;
	
	
	private int memberNo;
	private int boardNo;
	private int parentCommentNo;
	
	private String memberNickname;
	private String profileImg;
	

}
