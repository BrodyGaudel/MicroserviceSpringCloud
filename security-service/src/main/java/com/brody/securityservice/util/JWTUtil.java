package com.brody.securityservice.util;

public class JWTUtil {
	
	public static final String SECRET = "mySecret1234";
	public static final String AUTH_HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final long EXPIRE_ACCESS_TOKEN = 120000; 
	public static final long EXPIRE_REFRESH_TOKEN = 9000000;

}
