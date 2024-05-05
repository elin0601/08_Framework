package edu.kh.music.board.model.dto;

import java.util.List;

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
@Builder
public class Board {
	
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriteDate;
	private String boardUpdateDate;
	private int readCount;
	private String boardDelFl;
	private int memberNo;
	private int boardCode;
	
	private String memberNickname;
	
	private String boardName;
	
	private int categoryNo;
	private String categoryName;
	
	
	private int commentCount;
	private int likeCount;
	
	private int likeCheck;
	
	private String profileImg;
	private String thumbnail;
	
	private List<Comment> commentList;
	private List<BoardImg> imageList;

}
