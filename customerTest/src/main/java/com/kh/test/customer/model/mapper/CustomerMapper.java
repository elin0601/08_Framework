package com.kh.test.customer.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.test.customer.model.dto.Customer;

@Mapper
public interface CustomerMapper {

	int insert(Customer ct);

	int update(Customer ct);

	Customer select(Customer ct);

}
