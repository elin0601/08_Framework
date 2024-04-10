package com.ljs.book.main.book.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ljs.book.main.book.model.dto.Book;

@Mapper
public interface BookMapper {

	/** 책 등록
	 * @param bookInput 
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
	List<Book> searchBook(String bookTitle);

	/** 삭제
	 * @param bookNo
	 * @return
	 */
	int delete(int bookNo);

	/** 가격 수정
	 * @param book
	 * @return
	 */
	int update(Book book);

}
