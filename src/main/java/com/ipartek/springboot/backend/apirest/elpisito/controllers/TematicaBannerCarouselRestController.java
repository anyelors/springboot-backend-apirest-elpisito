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

import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerCarouselIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.BannerCarouselImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.services.TematicaBannerCarouselServiceImpl;

@RestController
@RequestMapping("/api")
public class TematicaBannerCarouselRestController {

	@Autowired
	private TematicaBannerCarouselServiceImpl tematicaBannerCarouselService;

	@PostMapping("/tematica-bannercarousel")
	public ResponseEntity<BannerCarouselImagenDTO> addBannerCarouselToTematica(@RequestParam Long tematicaId,
			@RequestParam Long bannerCarouselId) {
		BannerCarouselImagenDTO bannerDTO = tematicaBannerCarouselService.addBannerCarouselToTematica(tematicaId,
				bannerCarouselId);
		return ResponseEntity.ok(bannerDTO);
	}

	@DeleteMapping("/tematica-bannercarousel")
	public ResponseEntity<BannerCarouselImagenDTO> deleteBannerCarouselToTematica(@RequestParam Long tematicaId,
			@RequestParam Long bannerCarouselId) {
		BannerCarouselImagenDTO bannerDTO = tematicaBannerCarouselService.deleteBannerCarouselToTematica(tematicaId,
				bannerCarouselId);
		return ResponseEntity.ok(bannerDTO);
	}

	@GetMapping("/bannerscarousel-tematica/{id}")
	public ResponseEntity<List<BannerCarouselImagenDTO>> findBannersCarouselTematica(@PathVariable Long id) {
		var banners = tematicaBannerCarouselService.findBannersCarouselTematica(id);
		return ResponseEntity.ok(banners);
	}

	@GetMapping("/bannerscarouselid-tematica/{id}")
	public ResponseEntity<List<BannerCarouselIdDTO>> findBannersCarouselIdTematica(@PathVariable Long id) {
		var favoritosIds = tematicaBannerCarouselService.findIdsBannersCarouselTematica(id);
		return ResponseEntity.ok(favoritosIds);
	}

}
