package com.kh.test.customer.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.test.customer.model.dto.Customer;
import com.kh.test.customer.model.mapper.CustomerMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	
	private final CustomerMapper mapper;
	
	@Override
	public int insert(Customer ct) {
		return mapper.insert(ct);
	}

}
