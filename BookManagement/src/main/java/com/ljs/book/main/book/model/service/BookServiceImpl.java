package com.ljs.book.main.book.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ljs.book.main.book.model.dto.Book;
import com.ljs.book.main.book.model.mapper.BookMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
	
	private final BookMapper mapper;
	
	// 책 등록
	@Override
	public int insertBook(Book bookInput) {
		
		return mapper.insertBook(bookInput);
	}

	@Override
	public List<Book> selectList() {
		
		return mapper.selectList();
	}
	
	
	// 제목 검색
	@Override
	public List<Book> searchBook(String bookTitle) {
		
		return mapper.searchBook(bookTitle);
	}
	
	// 삭제
	@Override
	public int delete(int bookNo) {
		
		return mapper.delete(bookNo);
	}
	
	
	// 가격 수정
	@Override
	public int update(Book book) {
		
		return mapper.update(book);
	}
}
