package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.InmuebleMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.InmuebleRepository;
import com.ipartek.springboot.backend.apirest.elpisito.utilities.EntidadImagen;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InmuebleServiceImpl {

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Autowired
    private InmuebleServiceImpl inmuebleService;

    @Autowired
    private ImagenServiceImpl imagenService;

    @Autowired
    private InmuebleMapper inmuebleMapper;

    public List<InmuebleImagenDTO> findAllBulk() {

        List<Inmueble> inmuebles = inmuebleRepository.findAll();
        List<Long> ids = inmuebles
        .stream()
        .map(Inmueble::getId)
        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);

    }

    public List<InmuebleImagenDTO> findAllActiveBulk(Integer isActivo) {

        List<Inmueble> inmuebles = inmuebleRepository.findAllByActivo(isActivo);
        List<Long> ids = inmuebles
        .stream()
        .map(Inmueble::getId)
        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);

    }

    public List<InmuebleImagenDTO> findAllPortadaBulk(Integer isActivo, Integer isPortada) {

        List<Inmueble> inmuebles = inmuebleRepository.findByActivoAndPortada(1, 1);
        List<Long> ids = inmuebles
        .stream()
        .map(Inmueble::getId)
        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);

    }

    public List<InmuebleImagenDTO> findInmueblesInmobiliariaBulk(Long idInmobiliaria) {

        Inmueble inmueble = inmuebleRepository.findById(idInmobiliaria).orElseThrow(() -> new EntityNotFoundException("La inmobiliaria con id " + idInmobiliaria + " no existe"));

        List<Inmueble> inmuebles = inmuebleRepository.findByInmobiliariaId(inmueble.getInmobiliaria().getId());
        List<Long> ids = inmuebles
        .stream()
        .map(Inmueble::getId)
        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);

    }

    

}
