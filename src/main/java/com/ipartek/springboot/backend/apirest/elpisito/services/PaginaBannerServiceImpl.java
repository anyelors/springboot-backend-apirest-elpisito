package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Banner;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.BannerMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.BannerRepository;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.PaginaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaginaBannerServiceImpl {

    @Autowired
    private PaginaRepository paginaRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private ImagenServiceImpl imagenService;

    private BannerImagenDTO toDTO(Banner banner, ImagenServiceImpl imagenService) {
        return bannerMapper.toDto(banner, imagenService);
    }

    public BannerImagenDTO addBannerToPagina(Long paginaId, Long bannerId) {
        var pagina = paginaRepository.findById(paginaId).orElseThrow(() -> new EntityNotFoundException("La pagina con id " + paginaId + " no existe"));
        var banner = bannerRepository.findById(bannerId).orElseThrow(() -> new EntityNotFoundException("El banner con id " + bannerId + " no existe"));

        pagina.getBannersPagina().add(banner);
        paginaRepository.save(pagina);

        return toDTO(banner, imagenService);
    }

    public BannerImagenDTO deleteBannerToPagina(Long paginaId, Long bannerId) {
        var pagina = paginaRepository.findById(paginaId).orElseThrow(() -> new EntityNotFoundException("La pagina con id " + paginaId + " no existe"));
        var banner = bannerRepository.findById(bannerId).orElseThrow(() -> new EntityNotFoundException("El banner con id " + bannerId + " no existe"));

        pagina.getBannersPagina().remove(banner);
        paginaRepository.save(pagina);

        return toDTO(banner, imagenService);
    }

    public List<BannerImagenDTO> findBannersPagina(Long paginaId) {
        var pagina = paginaRepository.findById(paginaId).orElseThrow(() -> new EntityNotFoundException("La pagina con id " + paginaId + " no existe"));

        List<Banner> banners = new ArrayList<>(pagina.getBannersPagina());

        List<Long> ids = banners.stream()
                        .map(Banner::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER, ids);

        return bannerMapper.toDtoBulk(banners, mapaImagenes);
        
    }

    public List<BannerIdDTO> findIdsBannersPagina(Long paginaId) {
        var pagina = paginaRepository.findById(paginaId).orElseThrow(() -> new EntityNotFoundException("La pagina con id " + paginaId + " no existe"));

        List<Banner> banners = new ArrayList<>(pagina.getBannersPagina());
        return bannerMapper.toIdDtoList(banners);
    }

}    
