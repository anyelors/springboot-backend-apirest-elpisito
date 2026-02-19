package com.ipartek.springboot.backend.apirest.elpisito.mapper;

import java.util.List;
import java.util.Map;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmobiliariaIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmobiliariaImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmobiliaria;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.services.ImagenServiceImpl;

@Mapper(componentModel = "spring")
public interface InmobiliariaMapper {

    @Mapping(target = "imagenes", ignore = true)
	InmobiliariaImagenDTO toDto(Inmobiliaria inmobiliaria, @Context ImagenServiceImpl imagenService);

	List<InmobiliariaIdDTO> toIdDtoList(List<Inmobiliaria> inmobiliarias);

    List<InmobiliariaImagenDTO> toDTOList(List<Inmobiliaria> inmobiliarias, @Context ImagenServiceImpl imagenService);

    default List<InmobiliariaImagenDTO> toDtoBulk(List<Inmobiliaria> inmobiliarias, Map<Long, List<ImagenDTO>> imagenesMap) {
		List<InmobiliariaImagenDTO> dtos = toDTOList(inmobiliarias, null);
		for (InmobiliariaImagenDTO dto : dtos) {
			dto.setImagenes(imagenesMap.getOrDefault(dto.getId(), List.of()));
		}

		return dtos;
	}

    @AfterMapping
	default void cargarImagenes(Inmobiliaria inmobiliaria, @MappingTarget BannerImagenDTO dto,
			@Context ImagenServiceImpl imagenService) {
		if (imagenService != null) {
			List<ImagenDTO> imagenes = imagenService.getImagenes(EntidadImagen.INMOBILIARIA, inmobiliaria.getId());
			dto.setImagenes(imagenes);
		}
	}

}
