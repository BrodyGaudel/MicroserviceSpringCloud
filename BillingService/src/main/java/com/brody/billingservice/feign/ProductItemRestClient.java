package com.brody.billingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.brody.billingservice.model.Product;

@FeignClient(name = "IVENTORY-SERVICE")
public interface ProductItemRestClient {
	
	/*@GetMapping(path="/customers")
	PagedModel<Product> pageProduct(@RequestParam(value="page") int page,
			@RequestParam(value="size") int size);*/
	
	@GetMapping(path="/products")
	PagedModel<Product> pageProducts();
	
	@GetMapping(path="/products/{id}")
	Product getProductById(@PathVariable(name = "id") Long id);
}
