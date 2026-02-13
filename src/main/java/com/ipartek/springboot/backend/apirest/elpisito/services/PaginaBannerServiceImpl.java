package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Banner;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.BannerRepository;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.PaginaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaginaBannerServiceImpl {

    @Autowired
    private PaginaRepository paginaRepository;

    @Autowired
    private BannerRepository bannerRepository;

    private BannerDTO toDTO(Banner banner, Long paginaId) {
        return new BannerDTO(banner.getId(), banner.getTitular(), banner.getClaim(), banner.getLink(), paginaId);
    }

    public BannerDTO addBannerToPagina(Long paginaId, Long bannerId) {
        var pagina = paginaRepository.findById(paginaId).orElseThrow(() -> new EntityNotFoundException("La pagina con id " + paginaId + " no existe"));
        var banner = bannerRepository.findById(bannerId).orElseThrow(() -> new EntityNotFoundException("El banner con id " + bannerId + " no existe"));

        pagina.getBannersPagina().add(banner);
        paginaRepository.save(pagina);

        return toDTO(banner, paginaId);
    }

    public BannerDTO deleteBannerToPagina(Long paginaId, Long bannerId) {
        var pagina = paginaRepository.findById(paginaId).orElseThrow(() -> new EntityNotFoundException("La pagina con id " + paginaId + " no existe"));
        var banner = bannerRepository.findById(bannerId).orElseThrow(() -> new EntityNotFoundException("El banner con id " + bannerId + " no existe"));

        pagina.getBannersPagina().remove(banner);
        paginaRepository.save(pagina);

        return toDTO(banner, paginaId);
    }

    public List<Banner> findBannersPagina(Long paginaId) {
        var pagina = paginaRepository.findById(paginaId).orElseThrow(() -> new EntityNotFoundException("La pagina con id " + paginaId + " no existe"));
        return List.copyOf(pagina.getBannersPagina());
    }

    public List<BannerIdDTO> findIdsBannersPagina(Long paginaId) {
        var pagina = paginaRepository.findById(paginaId).orElseThrow(() -> new EntityNotFoundException("La pagina con id " + paginaId + " no existe"));
        return pagina.getBannersPagina().stream().map(banner -> new BannerIdDTO(banner.getId())).toList();
    }

}    
