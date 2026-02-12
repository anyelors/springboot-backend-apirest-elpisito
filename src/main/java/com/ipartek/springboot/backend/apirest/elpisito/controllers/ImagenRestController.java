package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Imagen;
import com.ipartek.springboot.backend.apirest.elpisito.services.ImagenServiceImpl;
import com.ipartek.springboot.backend.apirest.elpisito.utilities.EntidadImagen;

@RestController
@RequestMapping("/api")
public class ImagenRestController {

    @Autowired
    private ImagenServiceImpl imagenService;

    @GetMapping("/imagen-inmueble/{id}")
	public ResponseEntity<List<ImagenDTO>> findImagenesInmueble(@PathVariable Long id) {
		List<Imagen> listaImagenes = imagenService.getImagenes(EntidadImagen.INMUEBLE, id); 
		List<ImagenDTO> listaImagenesDTO = listaImagenes.stream()
		.map(imagen -> new ImagenDTO(imagen.getId(), imagen.getNombre(), imagen.getAltImagen()))
		.toList();
		
		return ResponseEntity.ok(listaImagenesDTO);
	}

    @GetMapping("/imagen-banner/{id}")
	public ResponseEntity<List<ImagenDTO>> findImagenesBanner(@PathVariable Long id) {
		List<Imagen> listaImagenes = imagenService.getImagenes(EntidadImagen.BANNER, id); 
		List<ImagenDTO> listaImagenesDTO = listaImagenes.stream()
		.map(imagen -> new ImagenDTO(imagen.getId(), imagen.getNombre(), imagen.getAltImagen()))
		.toList();
		
		return ResponseEntity.ok(listaImagenesDTO);
	}

    @GetMapping("/imagen-inmobiliaria/{id}")
	public ResponseEntity<List<ImagenDTO>> findImagenesInmobiliaria(@PathVariable Long id) {
		List<Imagen> listaImagenes = imagenService.getImagenes(EntidadImagen.INMOBILIARIA, id); 
		List<ImagenDTO> listaImagenesDTO = listaImagenes.stream()
		.map(imagen -> new ImagenDTO(imagen.getId(), imagen.getEntidadImagen()+"/"+imagen.getId()+"/"+imagen.getNombre(), imagen.getAltImagen()))
		.toList();
		
		return ResponseEntity.ok(listaImagenesDTO);
	}

    @GetMapping("/imagen-banner-carousel/{id}")
	public ResponseEntity<List<ImagenDTO>> findImagenesBannerCarousel(@PathVariable Long id) {
		List<Imagen> listaImagenes = imagenService.getImagenes(EntidadImagen.BANNER_CAROUSEL, id); 
		List<ImagenDTO> listaImagenesDTO = listaImagenes.stream()
		.map(imagen -> new ImagenDTO(imagen.getId(), imagen.getNombre(), imagen.getAltImagen()))
		.toList();
		
		return ResponseEntity.ok(listaImagenesDTO);
	}

    @PostMapping("/imagen")
	public ResponseEntity<Imagen> create(@RequestBody Imagen imagen) {
		return ResponseEntity.status(HttpStatus.CREATED).body(imagenService.save(imagen));
	}

    @PutMapping("/imagen")
	public ResponseEntity<Imagen> update(@RequestBody Imagen imagen) {
		return ResponseEntity.status(HttpStatus.CREATED).body(imagenService.save(imagen));
	}

	@DeleteMapping("/imagen/{id}")
	public ResponseEntity<Imagen> delete(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(imagenService.deleteById(id));
	}

}
