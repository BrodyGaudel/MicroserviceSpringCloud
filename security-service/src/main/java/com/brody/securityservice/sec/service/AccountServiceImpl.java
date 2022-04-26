package com.brody.securityservice.sec.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brody.securityservice.entity.AppRole;
import com.brody.securityservice.entity.AppUser;
import com.brody.securityservice.repository.AppRoleRepository;
import com.brody.securityservice.repository.AppUserRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
	private AppUserRepository appUserRepository;
	private AppRoleRepository appRoleRepository;
	private PasswordEncoder passwordEncoder;

	public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
		
		this.appUserRepository = appUserRepository;
		this.appRoleRepository = appRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public AppUser addNewUser(AppUser appUser) {
		String pwd = appUser.getPassword();
		appUser.setPassword(passwordEncoder.encode(pwd));
		return appUserRepository.save(appUser);
	}

	@Override
	public AppRole addNewRole(AppRole appRole) {
		
		return appRoleRepository.save(appRole);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		
		AppUser appUser = appUserRepository.findByUsername(username);
		AppRole appRole = appRoleRepository.findByRoleName(rolename);
		appUser.getAppRoles().add(appRole);
	}

	@Override
	public AppUser loadUserByUsername(String userbame) {
		
		return appUserRepository.findByUsername(userbame);
	}

	@Override
	public List<AppUser> listUsers() {
	
		return appUserRepository.findAll();
	}

}
