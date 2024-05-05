package edu.kh.music.board.exception;

public class BoardInserException extends RuntimeException {

	public BoardInserException() {
		super("게시글 삽입 중 예외 발생");
	}
	
	public BoardInserException(String message) {
		super(message);
	
	}
	
	

}
