package com.kh.test.customer.model.service;

import com.kh.test.customer.model.dto.Customer;

public interface CustomerService {

	int insert(Customer ct);

	int update(Customer ct);

	Customer select(Customer ct);

}
