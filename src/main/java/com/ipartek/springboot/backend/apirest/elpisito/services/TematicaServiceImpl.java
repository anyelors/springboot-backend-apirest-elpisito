package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Tematica;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.TematicaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TematicaServiceImpl implements GeneralService<Tematica> {

    @Autowired
    private TematicaRepository tematicaRepository;

    @Override
    public List<Tematica> findAll() {
        return tematicaRepository.findAll();
    }

    @Override
    public List<Tematica> findAllByActivo(Integer active) {
        return tematicaRepository.findAllByActivo(active);
    }

    @Override
    public Tematica findById(Long id) {
        return tematicaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La temática con id " + id + " no existe"));
    }

    @Override
    public Tematica save(Tematica objeto) {
        return tematicaRepository.save(objeto);
    }

    @Override
    public Tematica deleteById(Long id) {
        Tematica tematica = findById(id); 
        
        if (tematica.getActivo().equals(1))
            tematica.setActivo(0);
        else
            tematica.setActivo(1);

        return tematicaRepository.save(tematica);
    }

}
