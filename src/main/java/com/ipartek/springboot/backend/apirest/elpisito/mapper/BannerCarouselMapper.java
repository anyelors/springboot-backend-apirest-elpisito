package com.ipartek.springboot.backend.apirest.elpisito.mapper;

import java.util.List;
import java.util.Map;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerCarouselIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerCarouselImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.BannerCarousel;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.services.ImagenServiceImpl;

@Mapper(componentModel = "spring")
public interface BannerCarouselMapper {

    @Mapping(target = "imagenes", ignore = true)
	BannerCarouselImagenDTO toDto(BannerCarousel bannerCarousel, @Context ImagenServiceImpl imagenService);

	List<BannerCarouselIdDTO> toIdDtoList(List<BannerCarousel> bannerCarousels);

    List<BannerCarouselImagenDTO> toDTOList(List<BannerCarousel> bannerCarousels, @Context ImagenServiceImpl imagenService);

    default List<BannerCarouselImagenDTO> toDtoBulk(List<BannerCarousel> bannerCarousels, Map<Long, List<ImagenDTO>> imagenesMap) {
		List<BannerCarouselImagenDTO> dtos = toDTOList(bannerCarousels, null);
		for (BannerCarouselImagenDTO dto : dtos) {
			dto.setImagenes(imagenesMap.getOrDefault(dto.getId(), List.of()));
		}

		return dtos;
	}

    @AfterMapping
	default void cargarImagenes(BannerCarousel bannerCarousel, @MappingTarget BannerCarouselImagenDTO dto,
			@Context ImagenServiceImpl imagenService) {
		if (imagenService != null) {
			List<ImagenDTO> imagenes = imagenService.getImagenes(EntidadImagen.BANNER_CAROUSEL, bannerCarousel.getId());
			dto.setImagenes(imagenes);
		}
	}

}
