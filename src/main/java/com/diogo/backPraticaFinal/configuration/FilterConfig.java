package com.diogo.backPraticaFinal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.diogo.backPraticaFinal.configuration.filter.PrivateFilter;
import com.diogo.backPraticaFinal.services.TokenService;

@Configuration
public class FilterConfig {

	@Autowired
	private TokenService tokenService;

	@Bean
	public FilterRegistrationBean<PrivateFilter> tokenFilter() {
		FilterRegistrationBean<PrivateFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new PrivateFilter(tokenService));
		registrationBean.addUrlPatterns("/api/*"); 
		return registrationBean;
	}
}