package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Poblacion;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.PoblacionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PoblacionServiceImpl implements GeneralService<Poblacion> {

	@Autowired
	private PoblacionRepository poblacionRepository;

	@Override
	public List<Poblacion> findAll() {
		return poblacionRepository.findAll();
	}

	@Override
	public List<Poblacion> findAllByActivo(Integer active) {
		return poblacionRepository.findAllByActivo(active);
	}

	@Override
	public Poblacion findById(Long id) {
		return poblacionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("La población con id " + id + " no existe"));
	}

	@Override
	public Poblacion save(Poblacion objeto) {
		return poblacionRepository.save(objeto);
	}

	@Override
	public Poblacion deleteById(Long id) {
		Poblacion poblacion = findById(id);

		if (poblacion.getActivo().equals(1))
			poblacion.setActivo(0);
		else
			poblacion.setActivo(1);

		return poblacionRepository.save(poblacion);
	}

}
