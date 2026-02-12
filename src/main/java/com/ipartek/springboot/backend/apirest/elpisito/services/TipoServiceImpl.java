package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Tipo;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.TipoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoServiceImpl implements GeneralService<Tipo> {

	@Autowired
	private TipoRepository tipoRepository;

	@Override
	public List<Tipo> findAll() {
		return tipoRepository.findAll();
	}

	@Override
	public List<Tipo> findAllByActivo(Integer active) {
		return tipoRepository.findAllByActivo(active);
	}

	@Override
	public Tipo findById(Long id) {
		return tipoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El tipo de inmueble con id " + id + " no existe"));
	}

	@Override
	public Tipo save(Tipo objeto) {
		return tipoRepository.save(objeto);
	}

	@Override
	public Tipo deleteById(Long id) {
		Tipo tipo = findById(id); 
		
		if (tipo.getActivo().equals(1))
            tipo.setActivo(0);
        else
            tipo.setActivo(1);

        return tipoRepository.save(tipo);

	}

}
