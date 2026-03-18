package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.TematicaDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Tematica;
import com.ipartek.springboot.backend.apirest.elpisito.mappers.TematicaMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.TematicaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TematicaServiceImpl {

	@Autowired
	private TematicaRepository tematicaRepository;

	@Autowired
	private TematicaMapper tematicaMapper;

	public List<TematicaDTO> findAllDto() {
		List<Tematica> tematicas = tematicaRepository.findAll();
		List<TematicaDTO> tematicaDTOs = tematicaMapper.toDtoList(tematicas);

		for (TematicaDTO tematicaDTO : tematicaDTOs) {
			tematicaDTO.setNroBanners(tematicaDTO.getBannersCarousel().size());
		}

		return tematicaDTOs;
	}

	public List<Tematica> findAllByActivo(Integer active) {
		return tematicaRepository.findAllByActivo(active);
	}

	public Tematica findById(Long id) {
		return tematicaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La temática con id " + id + " no existe"));
	}

	public Tematica save(Tematica objeto) {
		return tematicaRepository.save(objeto);
	}

	public Tematica deleteById(Long id) {
		Tematica tematica = findById(id);

		if (tematica.getActivo().equals(1))
			tematica.setActivo(0);
		else
			tematica.setActivo(1);

		return tematicaRepository.save(tematica);
	}

	@Transactional(rollbackFor = Exception.class)
	public Tematica actualizarTematica(Long id) {
		Tematica tematica = findById(id);
		tematicaRepository.desactivarAll(id);

		return tematica;
	}

	public Tematica findActual() {
		return tematicaRepository.findByActual(1).getFirst();
	}

}
