package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Imagen;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.ImagenMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.ImagenRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ImagenServiceImpl {

	@Autowired
	private ImagenRepository imagenRepository;

	@Autowired
	private ImagenMapper imagenMapper;

	public Map<Long, List<ImagenDTO>> getImagenesPorEntidadBulk(EntidadImagen entidadImagen, List<Long> ids) {

		List<Imagen> imagenes = imagenRepository.findByEntidadImagenAndEntidadIdIn(entidadImagen, ids);
		List<ImagenDTO> imagenesDTO = imagenMapper.toDtoList(imagenes);

		//ImagenDTO es un record se debe hacer referencia directamente al campo entidadId ya que no existe el getEntidadId()
		return imagenesDTO.stream()
				.collect(Collectors.groupingBy(ImagenDTO::entidadId));
		
	}

	private ImagenDTO toDto(Imagen imagen) {
		String url = "/api/imagen/" + imagen.getEntidadImagen().name().toLowerCase() 
					+ "/" + imagen.getEntidadId() 
					+ "/" + imagen.getNombre();
		ImagenDTO imagenDTO = new ImagenDTO(imagen.getId(),url,imagen.getAltImagen(), imagen.getEntidadId());

		return imagenDTO;
	}

	public List<ImagenDTO> getImagenes(EntidadImagen entidadImagen, Long id) {
		List<Imagen> listaImagenes = imagenRepository.findByEntidadImagenAndEntidadId(entidadImagen, id);
		List<ImagenDTO> listaImagenesDTO = listaImagenes.stream()
				.map(imagen -> toDto(imagen))
				.toList();

		return listaImagenesDTO;
	}

	public ImagenDTO getImagen(EntidadImagen entidadImagen, Long id) {
		Imagen imagen = imagenRepository.findFirstByEntidadImagenAndEntidadId(entidadImagen, id).orElseThrow(() -> new EntityNotFoundException("Imagen de la " + entidadImagen + " con id " + id + " no existe"));
		return toDto(imagen);
	}

	public Imagen findById(Long id) {
		return imagenRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Imagen no encontrada con id: " + id));
	}

	public ImagenDTO save(Imagen objeto) {
		return toDto(imagenRepository.save(objeto));
	}

	public ImagenDTO deleteById(Long id) {
		Imagen imagen = findById(id);
		imagenRepository.deleteById(imagen.getId());
		return toDto(imagen);
	}

}
