package com.ipartek.springboot.backend.apirest.elpisito.mapper;

import java.util.List;
import java.util.Map;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Banner;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.services.ImagenServiceImpl;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    @Mapping(target = "imagenes", ignore = true)
	BannerImagenDTO toDto(Banner banner, @Context ImagenServiceImpl imagenService);

	List<BannerIdDTO> toIdDtoList(List<Banner> banners);

    List<BannerImagenDTO> toDTOList(List<Banner> banners, @Context ImagenServiceImpl imagenService);

    default List<BannerImagenDTO> toDtoBulk(List<Banner> banners, Map<Long, List<ImagenDTO>> imagenesMap) {
		List<BannerImagenDTO> dtos = toDTOList(banners, null);
		for (BannerImagenDTO dto : dtos) {
			dto.setImagenes(imagenesMap.getOrDefault(dto.getId(), List.of()));
		}

		return dtos;
	}

    @AfterMapping
	default void cargarImagenes(Banner banner, @MappingTarget BannerImagenDTO dto,
			@Context ImagenServiceImpl imagenService) {
		if (imagenService != null) {
			List<ImagenDTO> imagenes = imagenService.getImagenes(EntidadImagen.BANNER, banner.getId());
			dto.setImagenes(imagenes);
		}
	}

}
