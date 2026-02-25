package com.ipartek.springboot.backend.apirest.elpisito.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

	// 401 --> Spring Security lo invoca
	// Un usuario no autenticado intenta acceder a una URI protegida
	// No se ha proporcionado un TOKEN valido

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Petición no autorizada. Usuario necesita autenticación");

	}

}
