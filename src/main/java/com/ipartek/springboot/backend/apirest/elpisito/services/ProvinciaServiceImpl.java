package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Provincia;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.ProvinciaRepository;

@Service
public class ProvinciaServiceImpl implements GeneralService<Provincia> {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Override
    public List<Provincia> findAll() {
        return provinciaRepository.findAll();
    }

    @Override
    public List<Provincia> findAllByActivo(Integer active) {
        return provinciaRepository.findAllByActivo(active);
    }

    @Override
    public Provincia findById(Long id) {
        return provinciaRepository.findById(id).orElse(null);
    }

    @Override
    public Provincia save(Provincia objeto) {
        return provinciaRepository.save(objeto);
    }

    @Override
    public Provincia deleteById(Long id) {
        Provincia provincia = provinciaRepository.findById(id).orElse(null);
        
        if (provincia.getActivo().equals(1))
            provincia.setActivo(0);
        else
            provincia.setActivo(1);

        return provinciaRepository.save(provincia);
    }

}
