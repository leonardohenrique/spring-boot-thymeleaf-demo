package com.example.thymeleafdemo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.thymeleafdemo.domain.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();

	Page<Customer> findAllByOrderByNameAsc(Pageable pageable);

}
