package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Banner;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mappers.BannerMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.BannerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BannerServiceImpl {

	@Autowired
	private BannerRepository bannerRepository;

	@Autowired
	private ImagenServiceImpl imagenService;

	@Autowired
	private BannerMapper bannerMapper;

	public List<BannerImagenDTO> findAllBulk() {

		List<Banner> banners = bannerRepository.findAll();
		List<BannerImagenDTO> dtos = bannerMapper.toDtoList(banners);

		List<Long> ids = dtos.stream()
				.map(dto -> dto.getId())
				.distinct()
				.toList();

		Map<Long, List<ImagenDTO>> imagenesMap = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER, ids);

		for (BannerImagenDTO dto : dtos) {
			dto.setImagenes(imagenesMap.getOrDefault(dto.getId(), List.of()));
		}

		return dtos;

	}

	public List<BannerImagenDTO> findAllActiveBulk(Integer isActivo) {

		List<Banner> banners = bannerRepository.findAllByActivo(isActivo);
		List<BannerImagenDTO> dtos = bannerMapper.toDtoList(banners);

		List<Long> ids = dtos.stream()
				.map(dto -> dto.getId())
				.distinct()
				.toList();

		Map<Long, List<ImagenDTO>> imagenesMap = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER, ids);

		for (BannerImagenDTO dto : dtos) {
			dto.setImagenes(imagenesMap.getOrDefault(dto.getId(), List.of()));
		}

		return dtos;

	}

	public BannerImagenDTO findById(Long id) {
		Banner banner = bannerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El banner con id " + id + " no existe"));
		BannerImagenDTO dto = bannerMapper.toDto(banner);

		List<ImagenDTO> imagenesDtos = imagenService.getImagenes(EntidadImagen.BANNER, dto.getId());
		dto.setImagenes(imagenesDtos);

		return dto;
	}

	public BannerImagenDTO save(Banner banner) {
		Banner bannerGuardado = bannerRepository.save(banner);
		BannerImagenDTO dto = bannerMapper.toDto(bannerGuardado);

		List<ImagenDTO> imagenesDtos = imagenService.getImagenes(EntidadImagen.BANNER, dto.getId());
		dto.setImagenes(imagenesDtos);

		return dto;
	}

	public BannerImagenDTO deleteById(Long id) {
		Banner banner = bannerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El banner con id " + id + " no existe"));
		BannerImagenDTO dto = bannerMapper.toDto(banner);

		bannerRepository.delete(banner);

		List<ImagenDTO> imagenesDtos = imagenService.getImagenes(EntidadImagen.BANNER, dto.getId());
		dto.setImagenes(imagenesDtos);

		return dto;
	}

}
