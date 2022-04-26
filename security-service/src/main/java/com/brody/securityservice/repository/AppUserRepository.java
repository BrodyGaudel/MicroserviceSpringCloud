package com.brody.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brody.securityservice.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	AppUser findByUsername(String username);
	

}
