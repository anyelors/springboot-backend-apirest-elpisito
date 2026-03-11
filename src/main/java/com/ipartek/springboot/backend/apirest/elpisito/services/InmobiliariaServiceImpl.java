package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmobiliariaImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmobiliaria;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mappers.InmobiliariaMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.InmobiliariaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InmobiliariaServiceImpl {

	@Autowired
	private InmobiliariaRepository inmobiliariaRepository;

	@Autowired
	private InmobiliariaMapper inmobiliariaMapper;

	@Autowired
	private ImagenServiceImpl imagenService;

	public List<InmobiliariaImagenDTO> findAllBulk() {

		List<Inmobiliaria> inmobiliarias = inmobiliariaRepository.findAll();

		List<InmobiliariaImagenDTO> dtos = inmobiliariaMapper.toDtoList(inmobiliarias);

		List<Long> inmobiliariaIds = dtos.stream()
				.map(dto -> dto.getId())
				.distinct()
				.toList();

		Map<Long, List<ImagenDTO>> imagenesInmobiliariaMap = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMOBILIARIA, inmobiliariaIds);

		for (InmobiliariaImagenDTO dto : dtos) {
			dto.setImagenes(imagenesInmobiliariaMap.getOrDefault(dto.getId(), List.of()));
		}

		return dtos;

	}

	public List<InmobiliariaImagenDTO> findAllActiveBulk(Integer isActivo) {

		List<Inmobiliaria> inmobiliarias = inmobiliariaRepository.findAllByActivo(isActivo);

		List<InmobiliariaImagenDTO> dtos = inmobiliariaMapper.toDtoList(inmobiliarias);

		List<Long> inmobiliariaIds = dtos.stream()
				.map(dto -> dto.getId())
				.distinct()
				.toList();

		Map<Long, List<ImagenDTO>> imagenesInmobiliariaMap = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMOBILIARIA, inmobiliariaIds);

		for (InmobiliariaImagenDTO dto : dtos) {
			dto.setImagenes(imagenesInmobiliariaMap.getOrDefault(dto.getId(), List.of()));
		}

		return dtos;

	}

	public InmobiliariaImagenDTO findById(Long id) {
		Inmobiliaria inmobiliaria = inmobiliariaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La inmobiliaria con id " + id + " no existe"));
		InmobiliariaImagenDTO elInmuebleImagenDTO = inmobiliariaMapper.toDto(inmobiliaria);

		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.INMOBILIARIA, elInmuebleImagenDTO.getId());
		elInmuebleImagenDTO.setImagenes(imagenesInmobiliariaDtos);

		return elInmuebleImagenDTO;
	}

	public InmobiliariaImagenDTO save(Inmobiliaria inmobiliaria) {
		Inmobiliaria inmobiliariaSave = inmobiliariaRepository.save(inmobiliaria);

		InmobiliariaImagenDTO elInmuebleImagenDTO = inmobiliariaMapper.toDto(inmobiliariaSave);

		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.INMOBILIARIA, elInmuebleImagenDTO.getId());
		elInmuebleImagenDTO.setImagenes(imagenesInmobiliariaDtos);

		return elInmuebleImagenDTO;
	}

	public InmobiliariaImagenDTO deleteById(Long id) {
		Inmobiliaria inmobiliaria = inmobiliariaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La inmobiliaria con id " + id + " no existe"));
		inmobiliariaRepository.delete(inmobiliaria);

		InmobiliariaImagenDTO elInmuebleImagenDTO = inmobiliariaMapper.toDto(inmobiliaria);

		List<ImagenDTO> imagenesInmobiliariaDtos = imagenService.getImagenes(EntidadImagen.INMOBILIARIA, elInmuebleImagenDTO.getId());
		elInmuebleImagenDTO.setImagenes(imagenesInmobiliariaDtos);

		return elInmuebleImagenDTO;
	}

}
