package com.brody.billingservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.brody.billingservice.entity.ProductItem;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>{

	public Collection<ProductItem> findByBillId(Long id);
}
