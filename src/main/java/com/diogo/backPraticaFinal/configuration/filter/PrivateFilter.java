package com.diogo.backPraticaFinal.configuration.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.diogo.backPraticaFinal.services.TokenService;

public class PrivateFilter implements Filter {

	private TokenService tokenService;

	//constructor to injetct the tokenServices
	public PrivateFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);

			if (!tokenService.isValidSession(token)) {

				// If the token service says that the token is not valid, then a 403 code must be sent
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			filterChain.doFilter(request, response);
		}else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
	}
}