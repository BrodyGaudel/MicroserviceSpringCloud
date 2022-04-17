package com.brody.billingservice.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.brody.billingservice.model.Customer;

@Entity
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date billingDate;
	
	@OneToMany(mappedBy = "bill")
	private Collection<ProductItem> productItems;
	
	private Long customerID;
	
	@Transient
	private Customer customer;

	

	public Bill(Long id, Date billingDate, Collection<ProductItem> productItems) {
		
		this.id = id;
		this.billingDate = billingDate;
		this.productItems = productItems;
	}

	public Bill() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public Collection<ProductItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(Collection<ProductItem> productItems) {
		this.productItems = productItems;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", billingDate=" + billingDate + ", productItems=" + productItems + ", customerID="
				+ customerID + "]";
	}

}
