package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.services.PaginaBannerServiceImpl;

@RestController
@RequestMapping("/api")
public class PaginaBannerRestController {

    @Autowired
    private PaginaBannerServiceImpl paginaBannerService;

    @PostMapping("/pagina-banner")
    public ResponseEntity<BannerImagenDTO> addBannerToPagina(@RequestParam Long paginaId, @RequestParam Long bannerId) {
        BannerImagenDTO bannerDTO = paginaBannerService.addBannerToPagina(paginaId, bannerId);
        return ResponseEntity.ok(bannerDTO);
    }

    @DeleteMapping("/pagina-banner")
    public ResponseEntity<BannerImagenDTO> deleteBannerToPagina(@RequestParam Long paginaId, @RequestParam Long bannerId) {
        BannerImagenDTO bannerDTO = paginaBannerService.deleteBannerToPagina(paginaId, bannerId);
        return ResponseEntity.ok(bannerDTO);
    }

    @GetMapping("/pagina-banner/{id}")
    public ResponseEntity<List<BannerImagenDTO>> getBannersPagina(@PathVariable Long id) {
        var banners = paginaBannerService.findBannersPagina(id);
        return ResponseEntity.ok(banners);
    }

    @GetMapping("/paginaid-banner/{id}")
    public ResponseEntity<List<BannerIdDTO>> getFavoritosIds(@PathVariable Long id) {
        var favoritosIds = paginaBannerService.findIdsBannersPagina(id);
        return ResponseEntity.ok(favoritosIds);
    }

}
