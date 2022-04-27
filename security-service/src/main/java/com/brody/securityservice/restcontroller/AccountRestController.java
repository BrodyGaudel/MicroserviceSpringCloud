package com.brody.securityservice.restcontroller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brody.securityservice.entity.AppRole;
import com.brody.securityservice.entity.AppUser;
import com.brody.securityservice.service.AccountService;
import com.brody.securityservice.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AccountRestController {
	
	private AccountService accountService;

	public AccountRestController(AccountService accountService) {
		
		this.accountService = accountService;
	}
	
	@GetMapping(path="/users")
	@PostAuthorize("hasAuthority('USER')")
	public List<AppUser> appUsers(){
		return  accountService.listUsers();
	}
	
	@PostMapping(path="/users/save")
	@PostAuthorize("hasAuthority('ADMIN')")
	public AppUser saveUser(@RequestBody AppUser appUser) {
		return  accountService.addNewUser(appUser);
	}
	
	@PostMapping(path="/role/save")
	@PostAuthorize("hasAuthority('ADMIN')")
	public AppRole saveRole(@RequestBody AppRole appRole) {
		return  accountService.addNewRole(appRole);
	}
	
	@PostMapping(path="/role/user")
	@PostAuthorize("hasAuthority('ADMIN')")
	public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
		accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
	}
	
	@GetMapping(path="/refreshToken")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String auhToken = request.getHeader(JWTUtil.AUTH_HEADER);
		if(auhToken != null && auhToken.startsWith(JWTUtil.PREFIX)) {
			try {
				String jwt = auhToken.substring(JWTUtil.PREFIX.length());
				Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
				JWTVerifier jwtverifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jwtverifier.verify(jwt);
				String username = decodedJWT.getSubject();
				
				AppUser appUser = accountService.loadUserByUsername(username);
				String jwtAccessToken = JWT.create()
						.withSubject(appUser.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_REFRESH_TOKEN))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", appUser.getAppRoles().stream().map(AppRole::getRoleName).collect(Collectors.toList()))
						.sign(algorithm); 
				
				Map<String, String> idToken = new HashMap<>();
				idToken.put("acces-token", jwtAccessToken);
				idToken.put("refresh-token", jwt);
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), idToken);
			}catch(Exception e) {
				throw e;
			}
			
		}
		else {
			throw new RuntimeException("Refresh token required");
		}
	}
	
	@GetMapping(path="/profile")
	@PostAuthorize("hasAuthority('USER')")
	public AppUser profile(Principal principal) {
		return accountService.loadUserByUsername(principal.getName());
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
