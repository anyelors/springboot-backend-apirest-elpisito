package com.ipartek.springboot.backend.apirest.elpisito.exceptions;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.time.LocalDateTime;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ErrorResponseDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

	private ResponseEntity<ErrorResponseDTO> build(HttpStatus status, String message, Exception ex,
			HttpServletRequest req) {

		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(LocalDateTime.now(), status.value(),
				status.getReasonPhrase(), message, ex.getMessage(), req.getRequestURI());

		return ResponseEntity.status(status).body(errorResponseDTO);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	private ResponseEntity<ErrorResponseDTO> dataIntegrityViolation(DataIntegrityViolationException ex,
			HttpServletRequest req) {
		String elMensaje = "Violación de la integridad relacional de la BBDD.";
		return build(HttpStatus.CONFLICT, elMensaje, ex, req);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	private ResponseEntity<ErrorResponseDTO> EmptyResultDataAcces(EmptyResultDataAccessException ex,
			HttpServletRequest req) {
		String elMensaje = "Estás intentando actualizar un item no existente en la BBDD.";
		return build(HttpStatus.NOT_FOUND, elMensaje, ex, req);
	}

	@ExceptionHandler(DataAccessResourceFailureException.class)
	private ResponseEntity<ErrorResponseDTO> dataAccessResourceFailure(DataAccessResourceFailureException ex,
			HttpServletRequest req) {
		String elMensaje = "La BBDD está caida o inoperativa.";
		return build(HttpStatus.SERVICE_UNAVAILABLE, elMensaje, ex, req);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	private ResponseEntity<ErrorResponseDTO> entityNotFound(EntityNotFoundException ex, HttpServletRequest req) {
		String elMensaje = "Estás intentando acceder a una tabla que no existe en la BBDD.";
		return build(HttpStatus.SERVICE_UNAVAILABLE, elMensaje, ex, req);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<ErrorResponseDTO> methodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpServletRequest req) {
		String elMensaje = "El dato que estás intentando utilizar tiene un formato no valido.";
		return build(HttpStatus.BAD_REQUEST, elMensaje, ex, req);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	private ResponseEntity<ErrorResponseDTO> constraintViolation(ConstraintViolationException ex,
			HttpServletRequest req) {
		String elMensaje = "Estás intentando crear un registro en la BBDD con formato incorrecto.";
		return build(HttpStatus.BAD_REQUEST, elMensaje, ex, req);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	private ResponseEntity<ErrorResponseDTO> illegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
		String elMensaje = "Estás intentando crear o modificar un registro en la BBDD que excede, longitud o rango.";
		return build(HttpStatus.BAD_REQUEST, elMensaje, ex, req);
	}

	@ExceptionHandler(FileAlreadyExistsException.class)
	private ResponseEntity<ErrorResponseDTO> fileAlreadyExists(FileAlreadyExistsException ex, HttpServletRequest req) {
		String elMensaje = "Estás intentando almacenar un archivo cuyo nombre ya existe.";
		return build(HttpStatus.INTERNAL_SERVER_ERROR, elMensaje, ex, req);
	}

	@ExceptionHandler(NoSuchFieldException.class)
	private ResponseEntity<ErrorResponseDTO> noSuchField(NoSuchFieldException ex, HttpServletRequest req) {
		String elMensaje = "Estás intentando acceder a un cuya ruta no existe.";
		return build(HttpStatus.INTERNAL_SERVER_ERROR, elMensaje, ex, req);
	}

	@ExceptionHandler(FileSystemException.class)
	private ResponseEntity<ErrorResponseDTO> fileSystem(FileSystemException ex, HttpServletRequest req) {
		String elMensaje = "Estás intentando acceder a un cuya ruta no existe.";
		return build(HttpStatus.INTERNAL_SERVER_ERROR, elMensaje, ex, req);
	}

	@ExceptionHandler(DataAccessException.class)
	private ResponseEntity<ErrorResponseDTO> dataAccess(DataAccessException ex, HttpServletRequest req) {
		String elMensaje = "Fallo al intentar acceder a la BBDD.";
		return build(HttpStatus.INTERNAL_SERVER_ERROR, elMensaje, ex, req);
	}

}
