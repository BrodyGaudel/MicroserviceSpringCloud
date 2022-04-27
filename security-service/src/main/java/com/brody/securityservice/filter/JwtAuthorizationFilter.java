package com.brody.securityservice.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brody.securityservice.util.JWTUtil;

public class JwtAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/refreshToken")) {
			filterChain.doFilter(request, response);
		}
		else {
			String authorizathionToken = request.getHeader(JWTUtil.AUTH_HEADER);
			if(authorizathionToken != null && authorizathionToken.startsWith(JWTUtil.PREFIX)) {
				try {
					String jwt = authorizathionToken.substring(JWTUtil.PREFIX.length());
					Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
					JWTVerifier jwtverifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = jwtverifier.verify(jwt);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					
					Collection<GrantedAuthority> authorities = new ArrayList<>();
					for(String r: roles) {
						authorities.add(new SimpleGrantedAuthority(r));
					}
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(username, null,authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				}catch(Exception e) {
					response.setHeader("error-message", e.getMessage());
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
				
			}
			else {
				filterChain.doFilter(request, response);
			}
			
		}
		
		
	}
	

}
