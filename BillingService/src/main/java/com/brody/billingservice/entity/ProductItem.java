package com.brody.billingservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.brody.billingservice.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class ProductItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double quantity;
	private double price;
	private long productID;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne
	private Bill bill;
	
	@Transient
	private Product product;
	
	@Transient
	private String productName;

	public ProductItem(Long id, double quantity, double price, long productID) {
		
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.productID = productID;
	}
	
	

	public ProductItem() {
		
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	

	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	@Override
	public String toString() {
		return "ProductItem [id=" + id + ", quantity=" + quantity + ", price=" + price + ", productID=" + productID
				+ "]";
	}
}
