package com.ljs.book.main.book.model.dto;

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
public class Book {
	
	private int bookNo; // 책 번호
	private String bookTitle; // 책 제목
	private String bookWriter; // 글쓴이
	private int bookPrice; // 가격
	private String regDate; // 등록 날짜

}
