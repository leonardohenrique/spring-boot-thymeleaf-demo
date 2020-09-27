package com.example.thymeleafdemo.web;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.thymeleafdemo.domain.Customer;
import com.example.thymeleafdemo.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService service;

	@GetMapping
	public String findAll(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		final Page<Customer> customers = service.findAll(page, size);
		model.addAttribute("customers", customers);
		return "customer/index";
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@GetMapping("/add")
	public String add(Model model, Customer customer) {
		model.addAttribute("customer", customer);
		return "customer/add";
	}

	@PostMapping("/add")
	public String create(@Valid Customer customer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return add(model, customer);
		}

		service.save(customer);

		return "redirect:/customers";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		final Customer customer = service.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));

		model.addAttribute("customer", customer);
		return "customer/edit";
	}

	@PostMapping("/edit/{id}")
	public String update(@PathVariable Long id, @Valid Customer customer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			customer.setId(id);
			model.addAttribute("customer", customer);
			return "customer/edit";
		}

		service.save(customer);
		return "redirect:/customers";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model) {
		final Customer customer = service.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));

		service.delete(customer);

		return "redirect:/customers";
	}

}
