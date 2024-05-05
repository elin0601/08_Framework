package edu.kh.music.myPage.model.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.music.board.model.dto.Board;
import edu.kh.music.board.model.dto.Comment;
import edu.kh.music.board.model.dto.Pagination;
import edu.kh.music.common.util.Utility;
import edu.kh.music.member.model.dto.Member;
import edu.kh.music.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService {

	private final MyPageMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath;
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath;
	
	
	
	// 비밀번호 변경
	@Override
	public int changePw(String currentPw, String newPw, int memberNo) {

		String result = mapper.selectPw(memberNo);
		
		if(!bcrypt.matches(currentPw, result)) {
			return 0;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberNo", memberNo);
		map.put("newPw", bcrypt.encode(newPw));
		
		return mapper.changePw(map);
	}
	
	
	// 회원 탈퇴
	@Override
	public int secession(String memberPw, Member loginMember) {
		
		int memberNo = loginMember.getMemberNo();
		
		String pw = mapper.selectPw(memberNo);
		
		if(!bcrypt.matches(memberPw, pw)) return 0;
		
		return mapper.secession(memberNo);
	}
	
	
	
	@Override
	public int updateProfile(Member member, MultipartFile profileImg, int memberNo, Member loginMember) throws IllegalStateException, IOException {
		
		int result1 = mapper.updateInfo(member);
		
		String updatePath = null;
		String rename = null;
		
		if(!profileImg.isEmpty()) {
			
			rename = Utility.fileRename(profileImg.getOriginalFilename());
			
			updatePath = profileWebPath + rename;
		}
		
		Member mem = Member.builder()
						.memberNo(memberNo)
						.profileImg(updatePath)
						.build();
		
		int result2 = mapper.updateProfile(mem);
		
		if(result2 > 0) {
			if(!profileImg.isEmpty()) {
				profileImg.transferTo(new File(profileFolderPath + rename));
			}
			
			member.setProfileImg(updatePath);
		}
		int result = result1 + result2;
		
		return result;
	}
	
	
	@Override
	public Map<String, Object> selectMyBoard(int memberNo, int cp) {

		int listCount = mapper.getListCount(memberNo);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.selectMyBoard(memberNo, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
	}
	
	
	
	@Override
	public Map<String, Object> selectMyComment(int memberNo, int cp) {

		int listCount = mapper.getCommentCount(memberNo);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Comment> commentList = mapper.selectMyComment(memberNo, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("commentList", commentList);
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
