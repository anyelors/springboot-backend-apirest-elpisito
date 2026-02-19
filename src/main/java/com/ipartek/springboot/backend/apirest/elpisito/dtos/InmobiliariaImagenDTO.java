package com.ipartek.springboot.backend.apirest.elpisito.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InmobiliariaImagenDTO {

    private Long id;
	private String nombre;
	private String representante;
	private String telefono;
    private Integer activo;

}
