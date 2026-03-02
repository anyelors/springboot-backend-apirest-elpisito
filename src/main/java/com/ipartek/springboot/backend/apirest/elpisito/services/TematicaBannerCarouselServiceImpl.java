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

	private BannerCarouselImagenDTO toDTO(BannerCarousel bannerCarousel, ImagenServiceImpl imagenService) {
		return bannerCarouselMapper.toDto(bannerCarousel, imagenService);
	}

	public BannerCarouselImagenDTO addBannerCarouselToTematica(Long tematicaId, Long bannerCarouselId) {
		var tematica = tematicaRepository.findById(tematicaId)
				.orElseThrow(() -> new EntityNotFoundException("La tematica con id " + tematicaId + " no existe"));
		var bannerCarousel = bannerCarouselRepository.findById(bannerCarouselId)
				.orElseThrow(() -> new EntityNotFoundException(
						"El banner carousel con id " + bannerCarouselId + " no existe"));

		tematica.getBannersCarousel().add(bannerCarousel);
		tematicaRepository.save(tematica);

		return toDTO(bannerCarousel, imagenService);
	}

	public BannerCarouselImagenDTO deleteBannerCarouselToTematica(Long tematicaId, Long bannerCarouselId) {
		var tematica = tematicaRepository.findById(tematicaId)
				.orElseThrow(() -> new EntityNotFoundException("La tematica con id " + tematicaId + " no existe"));
		var bannerCarousel = bannerCarouselRepository.findById(bannerCarouselId)
				.orElseThrow(() -> new EntityNotFoundException(
						"El banner carousel con id " + bannerCarouselId + " no existe"));

		tematica.getBannersCarousel().remove(bannerCarousel);
		tematicaRepository.save(tematica);

		return toDTO(bannerCarousel, imagenService);
	}

	public List<BannerCarouselImagenDTO> findBannersCarouselTematica(Long tematicaId) {
		var tematica = tematicaRepository.findById(tematicaId)
				.orElseThrow(() -> new EntityNotFoundException("La tematica con id " + tematicaId + " no existe"));

		List<BannerCarousel> bannersCarousel = new ArrayList<>(tematica.getBannersCarousel());

		List<Long> ids = bannersCarousel.stream()
				.map(BannerCarousel::getId)
				.toList();

		Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER_CAROUSEL,
				ids);

		return bannerCarouselMapper.toDtoBulk(bannersCarousel, mapaImagenes);

	}

	public List<BannerCarouselIdDTO> findIdsBannersCarouselTematica(Long tematicaId) {
		var tematica = tematicaRepository.findById(tematicaId)
				.orElseThrow(() -> new EntityNotFoundException("La tematica con id " + tematicaId + " no existe"));

		List<BannerCarousel> bannersCarousel = new ArrayList<>(tematica.getBannersCarousel());
		return bannerCarouselMapper.toIdDtoList(bannersCarousel);
	}

}
