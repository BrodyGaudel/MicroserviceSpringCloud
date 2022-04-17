package com.brody.billingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.brody.billingservice.model.Customer;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {
	
	@GetMapping(path="customers/{id}")
	Customer getCustomerById(@PathVariable(name = "id") Long id);

}
