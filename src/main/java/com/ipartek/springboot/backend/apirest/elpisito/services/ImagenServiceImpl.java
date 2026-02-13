package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Imagen;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.ImagenRepository;
import com.ipartek.springboot.backend.apirest.elpisito.utilities.EntidadImagen;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ImagenServiceImpl {

	@Autowired
	private ImagenRepository imagenRepository;

	private ImagenDTO toDTO(Imagen imagen) {
		String url = "/api/imagen/" + imagen.getEntidadImagen().name().toLowerCase() + "/" + imagen.getEntidadId() + "/" + imagen.getNombre();
		ImagenDTO imagenDTO = new ImagenDTO(imagen.getId(),url,imagen.getAltImagen());

		return imagenDTO;
	}

	public List<ImagenDTO> getImagenes(EntidadImagen entidadImagen, Long id) {
		List<Imagen> listaImagenes = imagenRepository.findByEntidadImagenAndEntidadId(entidadImagen, id);
		List<ImagenDTO> listaImagenesDTO = listaImagenes.stream()
				.map(imagen -> toDTO(imagen))
				.toList();

		return listaImagenesDTO;
	}

	public ImagenDTO getImagen(EntidadImagen entidadImagen, Long id) {
		Imagen imagen = imagenRepository.findFirstByEntidadImagenAndEntidadId(entidadImagen, id).orElseThrow(() -> new EntityNotFoundException("Imagen de la " + entidadImagen + " con id " + id + " no existe"));
		return toDTO(imagen);
	}

	public Imagen findById(Long id) {
		return imagenRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Imagen no encontrada con id: " + id));
	}

	public ImagenDTO save(Imagen objeto) {
		return toDTO(imagenRepository.save(objeto));
	}

	public ImagenDTO deleteById(Long id) {
		Imagen imagen = findById(id);
		imagenRepository.deleteById(imagen.getId());
		return toDTO(imagen);
	}

}
