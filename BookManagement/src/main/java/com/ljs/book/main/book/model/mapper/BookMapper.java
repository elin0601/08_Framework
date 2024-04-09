package com.ljs.book.main.book.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ljs.book.main.book.model.dto.Book;

@Mapper
public interface BookMapper {

	/** 책 등록
	 * @param map 
	 * @param bookInput
	 * @return
	 */
	int insertBook(Map<String, Object> map);

	/** 책 조회
	 * @return
	 */
	List<Book> selectList();

	/** 제목 조회
	 * @param bookTitle
	 * @return
	 */
	List<Book> searchBook();

}
