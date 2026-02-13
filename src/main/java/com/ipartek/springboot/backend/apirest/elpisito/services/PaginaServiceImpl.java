package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Pagina;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.PaginaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaginaServiceImpl implements GeneralService<Pagina> {

    @Autowired
    private PaginaRepository paginaRepository;

    @Override
	public List<Pagina> findAll() {
		return paginaRepository.findAll();
	}

	@Override
	public List<Pagina> findAllByActivo(Integer active) {
		return paginaRepository.findAllByActivo(active);
	}

	@Override
	public Pagina findById(Long id) {
		return paginaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La página con id " + id + " no existe"));
	}

	@Override
	public Pagina save(Pagina objeto) {
		return paginaRepository.save(objeto);
	}

	@Override
	public Pagina deleteById(Long id) {
		Pagina pagina = findById(id); 
		
		if (pagina.getActivo().equals(1))
            pagina.setActivo(0);
        else
            pagina.setActivo(1);

        return paginaRepository.save(pagina);

	}

}
