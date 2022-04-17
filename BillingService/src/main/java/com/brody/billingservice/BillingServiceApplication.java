package com.brody.billingservice;

import java.util.Date;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import com.brody.billingservice.entity.Bill;
import com.brody.billingservice.entity.ProductItem;
import com.brody.billingservice.feign.CustomerRestClient;
import com.brody.billingservice.feign.ProductItemRestClient;
import com.brody.billingservice.model.Customer;
import com.brody.billingservice.model.Product;
import com.brody.billingservice.repository.BillRepository;
import com.brody.billingservice.repository.ProductItemRepository;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(
			BillRepository billRepository,
			ProductItemRepository productItemRepository,
			CustomerRestClient customerRestClient,
			ProductItemRestClient productItemRestClient) {
		
		return args -> {
			
			Customer customer = customerRestClient.getCustomerById(1L);
			Bill bill = new Bill();
			bill.setId(null);
			bill.setBillingDate(new Date());
			bill.setCustomerID(customer.getId());
			bill.setProductItems(null);
			bill.setCustomer(null);
			Bill savedBill = billRepository.save(bill);
			
			PagedModel<Product> productPagedModel = productItemRestClient.pageProducts();
			
			productPagedModel.forEach(p -> {
				
				ProductItem productItem = new ProductItem();
				productItem.setPrice(p.getPrice());
				productItem.setProductID(p.getId());
				productItem.setQuantity(1 + new Random().nextInt(100));
				productItem.setBill(savedBill);
				productItemRepository.save(productItem);
			});
			
			
			
		};
	}

}
