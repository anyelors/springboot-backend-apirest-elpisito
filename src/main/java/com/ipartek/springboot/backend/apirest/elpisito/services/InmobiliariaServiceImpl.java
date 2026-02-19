package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmobiliariaImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmobiliaria;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.InmobiliariaMapper;
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
        List<Long> ids = inmobiliarias.stream()
                        .map(Inmobiliaria::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER, ids);

        return inmobiliariaMapper.toDtoBulk(inmobiliarias, mapaImagenes);

    }

    public List<InmobiliariaImagenDTO> findAllActiveBulk(Integer isActivo) {

        List<Inmobiliaria> inmobiliarias = inmobiliariaRepository.findAllByActivo(isActivo);
        List<Long> ids = inmobiliarias.stream()
                        .map(Inmobiliaria::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.BANNER, ids);

        return inmobiliariaMapper.toDtoBulk(inmobiliarias, mapaImagenes);

    }

    public InmobiliariaImagenDTO findById(Long id) {
        Inmobiliaria inmobiliaria = inmobiliariaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La inmobiliaria con id " + id + " no existe"));
		return inmobiliariaMapper.toDto(inmobiliaria, imagenService);
	}

    public InmobiliariaImagenDTO save(Inmobiliaria inmobiliaria) {
        Inmobiliaria inmobiliariaSave = inmobiliariaRepository.save(inmobiliaria);
        return inmobiliariaMapper.toDto(inmobiliariaSave, imagenService);
    }

    public InmobiliariaImagenDTO deleteById(Long id) {
        Inmobiliaria inmobiliaria = inmobiliariaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El banner con id " + id + " no existe"));
        inmobiliariaRepository.delete(inmobiliaria);
        return inmobiliariaMapper.toDto(inmobiliaria, imagenService);
    }

}


