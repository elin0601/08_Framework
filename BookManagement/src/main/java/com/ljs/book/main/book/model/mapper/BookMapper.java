package com.ljs.book.main.book.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ljs.book.main.book.model.dto.Book;

@Mapper
public interface BookMapper {

	/** 책 등록
	 * @param bookInput
	 * @return
	 */
	int insertBook(Book bookInput);

	/** 책 조회
	 * @return
	 */
	List<Book> selectList();

	/** 제목 조회
	 * @param bookTitle
	 * @return
	 */
	String searchBook(String bookTitle);

}
