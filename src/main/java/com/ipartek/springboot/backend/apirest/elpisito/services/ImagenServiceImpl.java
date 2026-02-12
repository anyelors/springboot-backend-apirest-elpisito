package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Imagen;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.ImagenRepository;
import com.ipartek.springboot.backend.apirest.elpisito.utilities.EntidadImagen;

@Service
public class ImagenServiceImpl {

	@Autowired
    private ImagenRepository imagenRepository;

	public List<Imagen> getImagenes(EntidadImagen entidadImagen, Long id) {
		return imagenRepository.findByEntidadImagenAndEntidadId(entidadImagen, id);
	}

	public Imagen findById(Long id) {
		return imagenRepository.findById(id).orElseThrow(() -> new RuntimeException("Imagen no encontrada con id: " + id));
	}

	public Imagen save(Imagen objeto) {
		return imagenRepository.save(objeto);
	}

	public Imagen deleteById(Long id) {
		Imagen imagen = findById(id);
		imagenRepository.deleteById(imagen.getId());
		return imagen;
	}

}
