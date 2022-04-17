package com.brody.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.brody.billingservice.entity.Bill;

@RepositoryRestResource
public interface BillRepository extends JpaRepository<Bill, Long> {

}
