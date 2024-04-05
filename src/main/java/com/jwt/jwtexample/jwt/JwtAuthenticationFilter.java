package com.jwt.jwtexample.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String token = getTokenFromRequest(request);
		
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		
		final String AUTH_HEADER = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (StringUtils.hasText(AUTH_HEADER) && AUTH_HEADER.startsWith("Bearer ")) {
			return AUTH_HEADER.substring(7);
		}
		return null;
	}
}
