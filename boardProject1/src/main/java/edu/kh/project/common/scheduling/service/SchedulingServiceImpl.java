package edu.kh.project.common.scheduling.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.project.common.scheduling.mapper.SchedulingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingServiceImpl implements SchedulingService{
	
	private final SchedulingMapper mapper;
	
	// DB 이미지 조회
	@Override
	public List<String> selectImageList() {
		return mapper.selectImageList();
	}

}
