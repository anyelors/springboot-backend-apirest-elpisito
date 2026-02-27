package com.ipartek.springboot.backend.apirest.elpisito.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	public static final String SECRET = "LKFSLSLSFJ998383ULSLSLSLSLLSSPKGSL111000E";

	public String generateToken(Usuario usuario) {

		return Jwts.builder()
				.setSubject(usuario.getNombre())
				.claim("userId", usuario.getId())
				.claim("rol", usuario.getRol())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
				.compact();

	}

	public Claims getClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(token).getBody();

	}

}
