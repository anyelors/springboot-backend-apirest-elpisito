package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.InmuebleRepository;

@Service
public class InmuebleServiceImpl implements GeneralService<Inmueble> {

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Override
    public List<Inmueble> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<Inmueble> findAllByActivo(Integer active) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByActivo'");
    }

    @Override
    public Inmueble findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Inmueble save(Inmueble objeto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Inmueble deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    

}
