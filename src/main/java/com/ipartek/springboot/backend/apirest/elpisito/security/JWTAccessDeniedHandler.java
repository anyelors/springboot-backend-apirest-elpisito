package com.ipartek.springboot.backend.apirest.elpisito.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

	// 403 Forbiden --> autenticación sin permisos

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		response.sendError(HttpServletResponse.SC_FORBIDDEN,
				"Petición no autorizada. No tiene permisos para utilizar el recurso");

	}

}
