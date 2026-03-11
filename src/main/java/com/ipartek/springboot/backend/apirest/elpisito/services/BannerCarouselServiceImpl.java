package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerCarouselImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.BannerCarousel;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mappers.BannerCarouselMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.BannerCarouselRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BannerCarouselServiceImpl {

	@Autowired
	private BannerCarouselRepository bannerCarouselRepository;

	@Autowired
	private BannerCarouselMapper bannerCarouselMapper;

	@Autowired
	private ImagenServiceImpl imagenService;

	public List<BannerCarouselImagenDTO> findAllBulk() {

		List<BannerCarousel> bannersCarousel = bannerCarouselRepository.findAll();
		List<BannerCarouselImagenDTO> dtos = bannerCarouselMapper.toDtoList(bannersCarousel);

		List<Long> ids = dtos.stream()
				.map(dto -> dto.getId())
				.distinct()
				.toList();

		Map<Long, List<ImagenDTO>> imagenesMap = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER_CAROUSEL, ids);

		for (BannerCarouselImagenDTO dto : dtos) {
			dto.setImagenes(imagenesMap.getOrDefault(dto.getId(), List.of()));
		}

		return dtos;

	}

	public List<BannerCarouselImagenDTO> findAllActiveBulk(Integer isActivo) {

		List<BannerCarousel> bannersCarousel = bannerCarouselRepository.findAllByActivo(isActivo);
		List<BannerCarouselImagenDTO> dtos = bannerCarouselMapper.toDtoList(bannersCarousel);

		List<Long> ids = dtos.stream()
				.map(dto -> dto.getId())
				.distinct()
				.toList();

		Map<Long, List<ImagenDTO>> imagenesMap = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER_CAROUSEL, ids);

		for (BannerCarouselImagenDTO dto : dtos) {
			dto.setImagenes(imagenesMap.getOrDefault(dto.getId(), List.of()));
		}

		return dtos;

	}

	public BannerCarouselImagenDTO findById(Long id) {
		BannerCarousel bannersCarousel = bannerCarouselRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("El banner carousel con id " + id + " no existe"));

		BannerCarouselImagenDTO dto = bannerCarouselMapper.toDto(bannersCarousel);

		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.BANNER_CAROUSEL, dto.getId());
		dto.setImagenes(imagenesInmobiliariaDtos);

		return dto;
	}

	public BannerCarouselImagenDTO save(BannerCarousel banner) {
		BannerCarousel bannerGuardado = bannerCarouselRepository.save(banner);
		BannerCarouselImagenDTO dto = bannerCarouselMapper.toDto(bannerGuardado);

		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.BANNER_CAROUSEL, dto.getId());
		dto.setImagenes(imagenesInmobiliariaDtos);

		return dto;
	}

	public BannerCarouselImagenDTO deleteById(Long id) {
		BannerCarousel bannersCarousel = bannerCarouselRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("El banner carousel con id " + id + " no existe"));
		bannerCarouselRepository.delete(bannersCarousel);

		BannerCarouselImagenDTO dto = bannerCarouselMapper.toDto(bannersCarousel);

		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.BANNER_CAROUSEL, dto.getId());
		dto.setImagenes(imagenesInmobiliariaDtos);

		return dto;
	}

}
