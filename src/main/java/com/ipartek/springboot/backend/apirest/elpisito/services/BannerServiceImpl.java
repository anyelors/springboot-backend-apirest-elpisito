package com.ipartek.springboot.backend.apirest.elpisito.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Banner;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.BannerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BannerServiceImpl implements GeneralService<Banner> {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public java.util.List<Banner> findAll() {
        return bannerRepository.findAll();
    }

    @Override
    public java.util.List<Banner> findAllByActivo(Integer active) {
        return bannerRepository.findAllByActivo(active);
    }

    @Override
    public Banner findById(Long id) {
        return bannerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El banner con id " + id + " no existe"));
    }

    @Override
    public Banner save(Banner objeto) {
        return bannerRepository.save(objeto);
    }

    @Override
    public Banner deleteById(Long id) {
        Banner banner = findById(id);

        if (banner.getActivo() == 1) { 
            banner.setActivo(0); 
        } else { 
            banner.setActivo(1); 
        } 
        
        return bannerRepository.save(banner);
    }

}
