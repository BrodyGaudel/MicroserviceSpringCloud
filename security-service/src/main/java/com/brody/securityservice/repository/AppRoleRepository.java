package com.brody.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brody.securityservice.entity.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long>{

	AppRole findByRoleName(String roleName);
}
