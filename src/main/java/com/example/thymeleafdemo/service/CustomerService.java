package com.example.thymeleafdemo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.thymeleafdemo.domain.Customer;
import com.example.thymeleafdemo.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository repository;

	public Page<Customer> findAll(int page, int size) {
		final PageRequest pageable = PageRequest.of(page, size);
		return repository.findAllByOrderByNameAsc(pageable);
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public void save(Customer customer) {
		repository.save(customer);
	}

	public void delete(Customer customer) {
		repository.delete(customer);
	}

}