package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerCarouselImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.BannerCarousel;
import com.ipartek.springboot.backend.apirest.elpisito.services.BannerCarouselServiceImpl;

@RestController
@RequestMapping("/api")
public class BannerCarouselRestController {

    @Autowired
    private BannerCarouselServiceImpl bannerCarouselServiceImpl;

    @GetMapping("/bannerscarousel")
	public ResponseEntity<List<BannerCarouselImagenDTO>> findAll() {
		return ResponseEntity.ok(bannerCarouselServiceImpl.findAllBulk());
	}

    @GetMapping("/bannerscarousel-activos/{active}")
	public ResponseEntity<List<BannerCarouselImagenDTO>> findAllActivo(@PathVariable Integer active) {
		return ResponseEntity.ok(bannerCarouselServiceImpl.findAllActiveBulk(active));
	}

    @GetMapping("/bannercarousel/{id}")
	public ResponseEntity<BannerCarouselImagenDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(bannerCarouselServiceImpl.findById(id));
	}

    @PostMapping("/bannercarousel")
	public ResponseEntity<BannerCarouselImagenDTO> create(@RequestBody BannerCarousel banner) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bannerCarouselServiceImpl.save(banner));
	}

    @PutMapping("/bannercarousel")
	public ResponseEntity<BannerCarouselImagenDTO> update(@RequestBody BannerCarousel banner) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bannerCarouselServiceImpl.save(banner));
	}

	@PutMapping("/bannercarousel-activate/{id}")
	public ResponseEntity<BannerCarouselImagenDTO> delete(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bannerCarouselServiceImpl.deleteById(id));
	}

}
