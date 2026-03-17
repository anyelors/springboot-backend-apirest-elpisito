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
public class TematicaDTO {

	private Long id;
	private String nombre;
	private Integer actual;
	private Integer activo;
	private Integer nroBanners;

}
