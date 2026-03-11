package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mappers.InmuebleMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.InmuebleRepository;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FavoritoServiceImpl {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private InmuebleRepository inmuebleRepository;

	@Autowired
	private ImagenServiceImpl imagenService;

	@Autowired
	private InmuebleMapper inmuebleMapper;

	private Usuario findUsuarioById(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new EntityNotFoundException("El usuario con id " + usuarioId + " no existe"));
	}

	private Inmueble findInmuebleById(Long inmuebleId) {
		return inmuebleRepository.findById(inmuebleId)
				.orElseThrow(() -> new EntityNotFoundException("El inmueble con id " + inmuebleId + " no existe"));
	}

	public InmuebleImagenDTO addFavorito(Long usuarioId, Long inmuebleId) {
		var usuario = findUsuarioById(usuarioId);
		var inmueble = findInmuebleById(inmuebleId);

		usuario.getInmueblesFavoritos().add(inmueble);
		usuarioRepository.save(usuario);

		InmuebleImagenDTO elInmuebleImagenDTO = inmuebleMapper.toDto(inmueble);

		List<ImagenDTO> imagenesInmuebleDtos = imagenService.getImagenes(EntidadImagen.INMUEBLE, inmuebleId);
		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.INMOBILIARIA, elInmuebleImagenDTO.getInmobiliaria().getId());

		elInmuebleImagenDTO.setImagenes(imagenesInmuebleDtos);
		elInmuebleImagenDTO.getInmobiliaria().setImagenes(imagenesInmobiliariaDtos);

		return elInmuebleImagenDTO;
	}

	public InmuebleImagenDTO deleteFavorito(Long usuarioId, Long inmuebleId) {
		var usuario = findUsuarioById(usuarioId);
		var inmueble = findInmuebleById(inmuebleId);

		usuario.getInmueblesFavoritos().remove(inmueble);
		usuarioRepository.save(usuario);

		InmuebleImagenDTO elInmuebleImagenDTO = inmuebleMapper.toDto(inmueble);

		List<ImagenDTO> imagenesInmuebleDtos = imagenService.getImagenes(EntidadImagen.INMUEBLE, inmuebleId);
		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.INMOBILIARIA, elInmuebleImagenDTO.getInmobiliaria().getId());

		elInmuebleImagenDTO.setImagenes(imagenesInmuebleDtos);
		elInmuebleImagenDTO.getInmobiliaria().setImagenes(imagenesInmobiliariaDtos);

		return elInmuebleImagenDTO;
	}

	public List<InmuebleImagenDTO> findFavoritos(Long usuarioId) {
		var usuario = findUsuarioById(usuarioId);

		// Convertimos el Set a List para poder trabajar con streams
		List<Inmueble> inmuebles = new ArrayList<>(usuario.getInmueblesFavoritos());

		List<InmuebleImagenDTO> dtos = inmuebleMapper.toDtoList(inmuebles);

		List<Long> inmuebleIds = dtos.stream()
				.map(InmuebleImagenDTO::getId)
				.toList();

		List<Long> inmobiliariaIds = dtos.stream()
				.map(dto -> dto.getInmobiliaria().getId())
				.distinct()
				.toList();

		Map<Long, List<ImagenDTO>> imagenesInmuebleMap = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, inmuebleIds);

		Map<Long, List<ImagenDTO>> imagenesInmobiliariaMap = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMOBILIARIA, inmobiliariaIds);

		for (InmuebleImagenDTO dto : dtos) {
			dto.setImagenes(imagenesInmuebleMap.getOrDefault(dto.getId(), List.of()));
			dto.getInmobiliaria().setImagenes(imagenesInmobiliariaMap.getOrDefault(dto.getInmobiliaria().getId(), List.of()));
		}

		return dtos;

	}

	public List<InmuebleIdDTO> findIdsFavoritos(Long usuarioId) {
		var usuario = findUsuarioById(usuarioId);

		// Convertimos el Set a List para poder trabajar con streams
		List<Inmueble> inmuebles = new ArrayList<>(usuario.getInmueblesFavoritos());
		return inmuebleMapper.toIdDtoList(inmuebles);
	}

}
