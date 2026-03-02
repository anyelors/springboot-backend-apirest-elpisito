package com.ipartek.springboot.backend.apirest.elpisito.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
	// Esta clase va a centralizar toda la configuración de Spring Security

	@Autowired
	JWTValidationFilter jwtFilter;
	@Autowired
	JWTAuthenticationEntryPoint entryPoint; // 401
	@Autowired
	JWTAccessDeniedHandler deniedHandler; // 403

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// En una API Rest no existen la sesiones!!!
		http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Indicamos qué end points son accesibles para qué roles
		http.authorizeHttpRequests(auth -> {

			// Podemos hacer infinitas combinaciones utilizando el SELECTOR y el AUTORIZADOR
			// Podemos "jugar" con el selector : anyRequest(), requestMatchers()...
			// Podemos "jugar con el autorizador; permitAll(), authenticated(), hasRole(),
			// hasAnyRole();
			// Haciendo combinaciones entre selector y autorizador crearemos autorizaciones
			// "a la carta"
			// El obligatorio para Spring Security que en la BBDD esté anotado el Rol como
			// "ROLE_LOQUESEA"
			// y en este método "LOQUESEA"

			// auth.requestMatchers("/api/provincias").hasRole("USUARIO");
			// auth.requestMatchers("/api/tipos").hasAnyRole("SUPERADMIN","ADMIN");

			auth.requestMatchers(HttpMethod.POST, "/api/inmueble").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/inmueble").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/inmuebles").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.DELETE, "/api/inmueble/**").hasAnyRole("SUPERADMIN", "ADMIN");
			// auth.requestMatchers(HttpMethod.GET,"/api/inmuebles-inmobiliaria/**").hasAnyRole("SUPERADMIN","ADMIN","USUARIO");

			auth.requestMatchers(HttpMethod.POST, "/api/poblacion").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/poblacion").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/poblaciones").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/poblacion/**").hasAnyRole("SUPERADMIN", "ADMIN");
			// auth.requestMatchers(HttpMethod.GET,"/api/poblaciones-activas").hasAnyRole("SUPERADMIN","ADMIN");

			auth.requestMatchers(HttpMethod.POST, "/api/provincia").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/provincia").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/provincias").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/provincias-activas").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/provincia/**").hasAnyRole("SUPERADMIN", "ADMIN");// DELETE

			auth.requestMatchers(HttpMethod.POST, "/api/tipo").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/tipo").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/tipos").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/tipo/**").hasAnyRole("SUPERADMIN", "ADMIN");
			// auth.requestMatchers(HttpMethod.GET,"/api/tipos-activos").hasAnyRole("SUPERADMIN","ADMIN");

			auth.requestMatchers(HttpMethod.POST, "/api/operacion").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/operacion").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/operaciones").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/operacion/**").hasAnyRole("SUPERADMIN", "ADMIN");
			// auth.requestMatchers(HttpMethod.GET,"/api/operaciones-activas").hasAnyRole("SUPERADMIN","ADMIN");

			// el POST no existe porque no podemos restringir que una persona se de de
			// alta...
			auth.requestMatchers(HttpMethod.PUT, "/api/usuario").hasRole("SUPERADMIN");
			// auth.requestMatchers(HttpMethod.GET,"/api/usuario/**").hasRole("SUPERADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/usuarios").hasRole("SUPERADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/usuarios-activos").hasRole("SUPERADMIN");
			auth.requestMatchers(HttpMethod.DELETE, "/api/usuario/**").hasAnyRole("SUPERADMIN", "ADMIN");

			auth.requestMatchers(HttpMethod.POST, "/api/tematica").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/tematica").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/tematicas").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/tematicas-activas").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/tematica/**").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/actualizar-tematica/**").hasRole("SUPERADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/tematica/**").hasAnyRole("SUPERADMIN", "ADMIN");

			auth.requestMatchers(HttpMethod.POST, "/api/banner").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/banner").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/banners").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/banner/**").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/banner/**").hasAnyRole("SUPERADMIN", "ADMIN");

			auth.requestMatchers(HttpMethod.POST, "/api/banner-carousel").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/banner-carousel").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/banners-carouseles").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/banner-carousel/**").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/banner-carousel/**").hasAnyRole("SUPERADMIN", "ADMIN");

			auth.requestMatchers(HttpMethod.POST, "/api/pagina").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/pagina").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/paginas").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.GET, "/api/paginas/**").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.PUT, "/api/pagina/**").hasAnyRole("SUPERADMIN", "ADMIN");

			auth.requestMatchers(HttpMethod.POST, "/api/pagina-banner").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.DELETE, "/api/pagina-banner").hasAnyRole("SUPERADMIN", "ADMIN");
			// auth.requestMatchers(HttpMethod.GET, "/api/banners-pagina").hasAnyRole("SUPERADMIN", "ADMIN");
			// auth.requestMatchers(HttpMethod.GET, "/api/bannersid-pagina/**").hasAnyRole("SUPERADMIN", "ADMIN");

			auth.requestMatchers(HttpMethod.POST, "/api/tematica-bannercarousel").hasAnyRole("SUPERADMIN", "ADMIN");
			auth.requestMatchers(HttpMethod.DELETE, "/api/tematica-bannercarousel").hasAnyRole("SUPERADMIN", "ADMIN");
			// auth.requestMatchers(HttpMethod.GET, "/api/bannerscarousel-tematica").hasAnyRole("SUPERADMIN", "ADMIN");
			// auth.requestMatchers(HttpMethod.GET, "/api/bannerscarouselid-tematica/**").hasAnyRole("SUPERADMIN", "ADMIN");

			// Esta linea de código se suele poner siempre al final
			// porque significa que todo lo que no hayamos "restringido arriba" estaá
			// permitido
			auth.anyRequest().permitAll();

		});

		// Indicamos qué objetos manejan las excepciones Spring Security
		http.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint) // 401
				.accessDeniedHandler(deniedHandler)); // 403

		// Indicamos a Spring Security que en lugar del "filtro chain" debe ejecutar
		// tu filtro JWT "personalizado". En nuestro caso el JWTValidationFilter.
		// Spring Security funciona con una cadena de filtros (Security Filter Chain)
		// Cada una de estos filtros tiene una responsabilidad:
		// 1) autenticación básica
		// 2) login de formularios
		// 3) manejo de sesiones
		// Nuestro filtro "personalizado" es un filtro que valida el token JWT

		// Un ejemplo poco común en una API Rest con JWT...
		// http.addFilterAfter(jwtFilter, BasicAuthenticationFilter.class);

		// Lo que estamos diciendo aquí es:
		// "ejecuta el jwtFilter ANTES de UsernamePasswordAuthenticationFilter.class
		// El UsernamePasswordAuthenticationFilter es el filtro que maneja el login
		// clásico
		// con usuario y contraseña
		// ¿Por qué "before":
		// 1) Queremos que el JWT sea validado antes de que Spring intente autenticar
		// por usuario/password
		// 2) Si el JWT es válido ya se establece la autenticación en el Security
		// Context
		// Request -> jwtFilter -> UsernamePasswordAuthenticationFilter -> resto de
		// filtros...filterChain.doFilter(request, response)
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // esta linea es muy común en API
		// Rest con JWT

		/*
		 * CORS (Cross-Origin Resource Sharing) es un mecanismo de seguridad de los navegadores web que utiliza cabeceras HTTP para permitir o restringir que un sitio web (dominio
		 * A) acceda a recursos de otro dominio diferente (dominio B). Evita que scripts maliciosos carguen datos sensibles de otros sitios sin autorización, siendo esencial en el
		 * desarrollo web moderno para peticiones AJAX/Fetch entre API distintas.
		 */
		// http.cors(Customizer.withDefaults());
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

		// El CSRF (Cross Site Request Forgery) o falsificacion en peticiones
		// de sitios cruzados es un tipo de EXPLOIT malicioso de un sitio Web en el que
		// comandos no autorizados son transmitidos por un usuario (sin que él lo sepa)
		// en el cual
		// el sitio Web confía
		// Desactivamos el CSRF porque nuestra API usa Tokens JWT
		http.csrf(csrf -> csrf.disable());

		return http.build();

	}// end securityFilterChain

	// Una aplición de Spring Security SOLO PUEDE TENER UN PASSWOERD ENCODER!!
	// y además ES OBLIGATORIO QUE LO TENGA
	// Este password encoder debe estar anotado como @Bean
	// Al estar anotado como @Bean está permanentemente activo en el contexto de
	// Spring
	// y, de esta forma, puede ser utilizado sin necesidad de hacer una instancia de
	// su clase

	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuracion) throws Exception {

		return configuracion.getAuthenticationManager();

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOrigins(List.of("http://localhost:4200"));// Dominio desde donde se sirve la Web y otros
		// dominios que nos interesen
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE")); // Solo podemos utilizar en las
		// peticiones los "verbos" (métodos)
		// especificados
		config.setAllowedHeaders(List.of("*")); // Permitidos todos los headers (es importante porque aquí llegan los
		// Tokens)
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;

	}

}

/*
 * DEJAMOS ESTE EJEMPO COMO CONFIGURACIÓN CORS UN POCO MÁS DETALLADA
 * 
 * @Bean CorsConfigurationSource corsConfigurationSource() {
 * 
 * 
 * CorsConfiguration config = new CorsConfiguration();
 * 
 * config.setAllowedOrigins(List.of("http://localhost:4200"));//Dominio desde donde se sirve la Web y otros dominios que nos interesen
 * config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE")); //Solo podemos utilizar en las peticiones los "verbos" (métodos) especificados
 * config.setAllowedHeaders(List.of("Authorization","Cache-Control", "Content-Type")); config.setAllowCredentials(true); config.setExposedHeader(List.of("Authorization")); //De
 * esta manera podemos acceder a la información de "Authorization" en un interceptor en Angular
 * 
 * UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**", config);
 * 
 * return source;
 * 
 * 
 * }
 * 
 */
