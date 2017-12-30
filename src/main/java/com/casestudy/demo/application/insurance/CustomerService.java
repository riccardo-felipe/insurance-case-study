package com.casestudy.demo.application.insurance;

import com.casestudy.demo.domain.insurance.Customer;
import com.casestudy.demo.domain.insurance.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CustomerService {
	private final CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public Customer findByEmail(String email) {
		return Optional
			.ofNullable(repository.findByEmail(email))
			.orElseThrow(() -> new EntityNotFoundException(String.format("customer %s not found", email)));
	}
}
