package com.ljs.book.main.book.model.service;

import java.util.List;

import com.ljs.book.main.book.model.dto.Book;

public interface BookService {

	/** 책 등록
	 * @param bookInput
	 * @return
	 */
	int insertBook(Book bookInput);

	/** 책 조회
	 * @return
	 */
	List<Book> selectList();

	/** 제목 검색
	 * @param bookTitle 
	 * @param bookTitle
	 * @return
	 */
	List<Book> searchBook(String bookTitle);

}
