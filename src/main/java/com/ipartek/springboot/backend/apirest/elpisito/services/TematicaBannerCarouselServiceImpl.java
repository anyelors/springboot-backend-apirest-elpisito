package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerCarouselIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerCarouselImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.BannerCarousel;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mappers.BannerCarouselMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.BannerCarouselRepository;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.TematicaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TematicaBannerCarouselServiceImpl {

	@Autowired
	private TematicaRepository tematicaRepository;

	@Autowired
	private BannerCarouselRepository bannerCarouselRepository;

	@Autowired
	private BannerCarouselMapper bannerCarouselMapper;

	@Autowired
	private ImagenServiceImpl imagenService;

	public BannerCarouselImagenDTO addBannerCarouselToTematica(Long tematicaId, Long bannerCarouselId) {
		var tematica = tematicaRepository.findById(tematicaId)
				.orElseThrow(() -> new EntityNotFoundException("La tematica con id " + tematicaId + " no existe"));
		var bannerCarousel = bannerCarouselRepository.findById(bannerCarouselId)
				.orElseThrow(() -> new EntityNotFoundException(
						"El banner carousel con id " + bannerCarouselId + " no existe"));

		tematica.getBannersCarousel().add(bannerCarousel);
		tematicaRepository.save(tematica);

		BannerCarouselImagenDTO dto = bannerCarouselMapper.toDto(bannerCarousel);

		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.BANNER_CAROUSEL, dto.getId());
		dto.setImagenes(imagenesInmobiliariaDtos);

		return dto;
	}

	public BannerCarouselImagenDTO deleteBannerCarouselToTematica(Long tematicaId, Long bannerCarouselId) {
		var tematica = tematicaRepository.findById(tematicaId)
				.orElseThrow(() -> new EntityNotFoundException("La tematica con id " + tematicaId + " no existe"));
		var bannerCarousel = bannerCarouselRepository.findById(bannerCarouselId)
				.orElseThrow(() -> new EntityNotFoundException(
						"El banner carousel con id " + bannerCarouselId + " no existe"));

		tematica.getBannersCarousel().remove(bannerCarousel);
		tematicaRepository.save(tematica);

		BannerCarouselImagenDTO dto = bannerCarouselMapper.toDto(bannerCarousel);

		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.BANNER_CAROUSEL, dto.getId());
		dto.setImagenes(imagenesInmobiliariaDtos);

		return dto;
	}

	public List<BannerCarouselImagenDTO> findBannersCarouselTematica(Long tematicaId) {
		var tematica = tematicaRepository.findById(tematicaId)
				.orElseThrow(() -> new EntityNotFoundException("La tematica con id " + tematicaId + " no existe"));

		List<BannerCarousel> bannersCarousel = new ArrayList<>(tematica.getBannersCarousel());
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

	public List<BannerCarouselIdDTO> findIdsBannersCarouselTematica(Long tematicaId) {
		var tematica = tematicaRepository.findById(tematicaId)
				.orElseThrow(() -> new EntityNotFoundException("La tematica con id " + tematicaId + " no existe"));

		List<BannerCarousel> bannersCarousel = new ArrayList<>(tematica.getBannersCarousel());
		return bannerCarouselMapper.toIdDtoList(bannersCarousel);
	}

}
