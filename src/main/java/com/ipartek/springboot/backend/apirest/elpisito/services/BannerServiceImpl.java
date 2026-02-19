package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Banner;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.BannerMapper;
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
        List<Long> ids = banners.stream()
                        .map(Banner::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER, ids);

        return bannerMapper.toDtoBulk(banners, mapaImagenes);

    }

    public List<BannerImagenDTO> findAllActiveBulk(Integer isActivo) {

        List<Banner> banners = bannerRepository.findAllByActivo(isActivo);
        List<Long> ids = banners.stream()
                        .map(Banner::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER, ids);

        return bannerMapper.toDtoBulk(banners, mapaImagenes);

    }

    public BannerImagenDTO findById(Long id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El banner con id " + id + " no existe"));
		return bannerMapper.toDto(banner, imagenService);
	}

    public BannerImagenDTO save(Banner banner) {
        Banner bannerGuardado = bannerRepository.save(banner);
        return bannerMapper.toDto(bannerGuardado, imagenService);
    }

    public BannerImagenDTO deleteById(Long id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El banner con id " + id + " no existe"));
        bannerRepository.delete(banner);
        return bannerMapper.toDto(banner, imagenService);
    }

}
