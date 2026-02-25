package com.ipartek.springboot.backend.apirest.elpisito.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// Esta clase sentraliza toda la configuracion de Spring Security

	@Autowired
	JWTValidationFilter jwtFilter;
	@Autowired
	JWTAuthenticationEntryPoint entryPoint;
	@Autowired
	JWTAccessDeniedHandler deniedHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return null;

	}

}
