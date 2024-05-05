package edu.kh.music.email.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.music.member.model.dto.Member;

@Mapper
public interface EmailMapper {

	int updateAuthKey(Map<String, Object> map);

	int insertAuthKey(Map<String, Object> map);

	int checkAuthKey(Map<String, Object> map);

	int deleteAuthKey(String memberEmail);

}
