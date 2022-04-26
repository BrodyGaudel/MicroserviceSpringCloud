package com.brody.securityservice.sec.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brody.securityservice.entity.AppRole;
import com.brody.securityservice.entity.AppUser;
import com.brody.securityservice.sec.service.AccountService;

@RestController
public class AccountRestController {
	
	private AccountService accountService;

	public AccountRestController(AccountService accountService) {
		
		this.accountService = accountService;
	}
	
	@GetMapping(path="/users")
	public List<AppUser> appUsers(){
		return  accountService.listUsers();
	}
	
	@PostMapping(path="/users/save")
	public AppUser saveUser(@RequestBody AppUser appUser) {
		return  accountService.addNewUser(appUser);
	}
	
	@PostMapping(path="/role/save")
	public AppRole saveRole(@RequestBody AppRole appRole) {
		return  accountService.addNewRole(appRole);
	}
	
	@PostMapping(path="/role/user")
	public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
		accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
	}
	
	

}

class RoleUserForm{
	private String username;
	private String roleName;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
