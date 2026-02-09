package com.ipartek.springboot.backend.apirest.elpisito.dtos;

import java.time.LocalDateTime;

public record ErrorResponseDTO(LocalDateTime timeStamp, int status, String error, String msgPersonal, String message,
		String path) {

}
