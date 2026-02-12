package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Operacion;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.OperacionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OperacionServiceImpl implements GeneralService<Operacion> {

    @Autowired
    private OperacionRepository operacionRepository;

	@Override
	public List<Operacion> findAll() {
        return operacionRepository.findAll();
	}

	@Override
	public List<Operacion> findAllByActivo(Integer active) {
        return operacionRepository.findAllByActivo(active);
	}

	@Override
	public Operacion findById(Long id) {
        return operacionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La Operación con id " + id + " no existe"));
	}

	@Override
	public Operacion save(Operacion objeto) {
        return operacionRepository.save(objeto);
	}

	@Override
	public Operacion deleteById(Long id) {
        Operacion operacion = findById(id);

        if (operacion.getActivo() == 1) { 
            operacion.setActivo(0); 
        } else { 
            operacion.setActivo(1); 
        } 
        
        return operacionRepository.save(operacion);
	}

}
