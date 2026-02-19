package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerCarouselImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.BannerCarousel;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.BannerCarouselMapper;
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
        List<Long> ids = bannersCarousel.stream()
                        .map(BannerCarousel::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER, ids);

        return bannerCarouselMapper.toDtoBulk(bannersCarousel, mapaImagenes);

    }

    public List<BannerCarouselImagenDTO> findAllActiveBulk(Integer isActivo) {

        List<BannerCarousel> banners = bannerCarouselRepository.findAllByActivo(isActivo);
        List<Long> ids = banners.stream()
                        .map(BannerCarousel::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER, ids);

        return bannerCarouselMapper.toDtoBulk(banners, mapaImagenes);

    }

    public BannerCarouselImagenDTO findById(Long id) {
        BannerCarousel banner = bannerCarouselRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El banner carousel con id " + id + " no existe"));
		return bannerCarouselMapper.toDto(banner, imagenService);
	}

    public BannerCarouselImagenDTO save(BannerCarousel banner) {
        BannerCarousel bannerGuardado = bannerCarouselRepository.save(banner);
        return bannerCarouselMapper.toDto(bannerGuardado, imagenService);
    }

    public BannerCarouselImagenDTO deleteById(Long id) {
        BannerCarousel banner = bannerCarouselRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El banner carousel con id " + id + " no existe"));
        bannerCarouselRepository.delete(banner);
        return bannerCarouselMapper.toDto(banner, imagenService);
    }

}
