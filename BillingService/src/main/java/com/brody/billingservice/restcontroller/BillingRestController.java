package com.brody.billingservice.restcontroller;

import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.brody.billingservice.entity.Bill;
import com.brody.billingservice.feign.CustomerRestClient;
import com.brody.billingservice.feign.ProductItemRestClient;
import com.brody.billingservice.model.Customer;
import com.brody.billingservice.model.Product;
import com.brody.billingservice.repository.BillRepository;
import com.brody.billingservice.repository.ProductItemRepository;

@RestController
public class BillingRestController {
	
	private BillRepository billRepository;
	private ProductItemRepository productItemRepository;
	private CustomerRestClient customerRestClient;
	private ProductItemRestClient productRestClient;
	
	
	
	public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository,
			CustomerRestClient customerRestClient, ProductItemRestClient productRestClient) {
		
		this.billRepository = billRepository;
		this.productItemRepository = productItemRepository;
		this.customerRestClient = customerRestClient;
		this.productRestClient = productRestClient;
	}



	@GetMapping(path="/fullBill/{id}")
	public Bill getBillById(@PathVariable Long id) {
		
		Bill bill = billRepository.findById(id).get();
		
		Customer customer = customerRestClient.getCustomerById(bill.getId());
		bill.setCustomer(customer);
		
		bill.getProductItems().forEach(pi ->{
			Product product = productRestClient.getProductById(pi.getId());
			//pi.setProduct(product);
			pi.setProductName(product.getName());
		});
		
		return bill;
	}

}
