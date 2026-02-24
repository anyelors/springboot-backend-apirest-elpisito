package com.ipartek.springboot.backend.apirest.elpisito.exceptions;



public class FileStorageException extends RuntimeException{
	
	private static final long serialVersionUID = 9159472488392273434L;

	public FileStorageException(String mensaje) {
		
		super(mensaje);
	}

}
