package com.ipartek.springboot.backend.apirest.elpisito.dtos;

import java.util.List;

import com.ipartek.springboot.backend.apirest.elpisito.entities.BannerCarousel;

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
	private List<BannerCarousel> bannersCarousel;
	private Integer nroBanners;

}
