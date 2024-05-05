package edu.kh.music.board.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.music.board.exception.BoardInserException;
import edu.kh.music.board.exception.ImageDeleteException;
import edu.kh.music.board.exception.ImageUpdateException;
import edu.kh.music.board.model.dto.Board;
import edu.kh.music.board.model.dto.BoardImg;
import edu.kh.music.board.model.mapper.EditBoardMapper;
import edu.kh.music.common.util.Utility;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor=Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class EditBoardServiceImpl implements EditBoardService {

	private final EditBoardMapper mapper;
	
	@Value("${my.board.web-path}")
	private String webPath; 
	
	@Value("${my.board.folder-path}")
	private String folderPath;
	
	
	@Override
	public int boardInsert(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException {
		
		int result = mapper.boardInsert(inputBoard);
		
		if(result == 0) return 0;
		
		//-----------------------------------------------------------------
		
		int boardNo = inputBoard.getBoardNo();
		
		List<BoardImg> uploadList = new ArrayList<>();
		
		for(int i=0; i<images.size(); i++) {
			
			if(!images.get(i).isEmpty()) {
				
				String originalName = images.get(i).getOriginalFilename();
				String rename = Utility.fileRename(originalName);
				
				BoardImg img = BoardImg.builder()
								.imgOriginalName(originalName)
								.imgRename(rename)
								.imgPath(webPath)
								.boardNo(boardNo)
								.imgOrder(i)
								.uploadFile(images.get(i))
								.build();
				uploadList.add(img);
			}
			
		}
		
		if(uploadList.isEmpty()) {
			return boardNo;
		}
		//-----------------------------------------------------------------------
		result = mapper.insertUploadList(uploadList);
		
		
			if(result == uploadList.size()) {
				for( BoardImg img : uploadList) {
					img.getUploadFile().transferTo(new File(folderPath + img.getImgRename()));
				}
			}else {
				throw new BoardInserException("이미지가 정상 삽입되지 않음");
			}
		
		return boardNo;
	}
	
	
	// 게시글 삭제
	@Override
	public int deleteBoard(Board board) {
		return mapper.deleteBoard(board);
	}
	
	
	// 게시글 수정
	@Override
	public int boardUpdate(Board inputBoard, List<MultipartFile> images, String deleteOrder) throws IllegalStateException, IOException {
		
		int result = mapper.boardUpdate(inputBoard);
		
		if(result == 0) return 0;
		
		// 기존 o -> 삭제된 이미지( deleteOrder 이용)가 있는 경우 
		if(deleteOrder != null && !deleteOrder.equals("")) {	
			Map<String, Object> map = new HashMap<>();
			map.put("deleteOrder", deleteOrder);
			map.put("boardNo", inputBoard.getBoardNo());
			
			result = mapper.deleteImage(map);
			
			if(result == 0) {
				throw new ImageDeleteException();
			}
		}
		
		List<BoardImg> uploadList = new ArrayList<>();
		
		for(int i=0 ; i < images.size(); i++) {
			
			if(!images.get(i).isEmpty()) {
				String originalName = images.get(i).getOriginalFilename();
				String rename = Utility.fileRename(originalName);
				
				BoardImg img = BoardImg.builder()
								.imgOriginalName(originalName)
								.imgRename(rename)
								.imgPath(webPath)
								.boardNo(inputBoard.getBoardNo())
								.imgOrder(i)
								.uploadFile(images.get(i))
								.build();
				
				uploadList.add(img);
				
				result = mapper.updateImage(img);
				
				if(result == 0) {
					result = mapper.insertImage(img);
				}
				
			}
			
			if(result == 0) {
				throw new ImageUpdateException();
			}
			
		}
		
		if(uploadList.isEmpty()) {
			return result;
		}
		
		for(BoardImg img : uploadList) {
			img.getUploadFile().transferTo(new File(folderPath + img.getImgRename()));
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
