package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerDTO;
import com.ipartek.springboot.backend.apirest.elpisito.services.PaginaBannerServiceImpl;

@RestController
@RequestMapping("/api")
public class PaginaBannerRestController {

    @Autowired
    private PaginaBannerServiceImpl paginaBannerService;

    @PostMapping("/pagina-banner")
    public ResponseEntity<BannerDTO> addBannerToPagina(@RequestParam Long paginaId, @RequestParam Long bannerId) {
        BannerDTO bannerDTO = paginaBannerService.addBannerToPagina(paginaId, bannerId);
        return ResponseEntity.ok(bannerDTO);
    }

    @DeleteMapping("/pagina-banner")
    public ResponseEntity<BannerDTO> deleteBannerToPagina(@RequestParam Long paginaId, @RequestParam Long bannerId) {
        BannerDTO bannerDTO = paginaBannerService.deleteBannerToPagina(paginaId, bannerId);
        return ResponseEntity.ok(bannerDTO);
    }

}
