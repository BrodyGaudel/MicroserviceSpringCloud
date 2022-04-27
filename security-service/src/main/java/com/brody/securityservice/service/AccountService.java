package com.brody.securityservice.service;

import java.util.List;

import com.brody.securityservice.entity.AppRole;
import com.brody.securityservice.entity.AppUser;

public interface AccountService {
	AppUser addNewUser(AppUser appUser);
	AppRole addNewRole(AppRole appRole);
	void addRoleToUser(String username, String rolename);
	AppUser loadUserByUsername(String userbame);
	List<AppUser> listUsers();
}
