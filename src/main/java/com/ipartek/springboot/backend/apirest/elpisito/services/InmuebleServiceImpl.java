package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmobiliaria;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Operacion;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Poblacion;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Tipo;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.InmuebleMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.InmobiliariaRepository;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.InmuebleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InmuebleServiceImpl {

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Autowired
    private InmobiliariaRepository inmobiliariaRepository;

    @Autowired
    private ImagenServiceImpl imagenService;

    @Autowired
    private TipoServiceImpl tipoService;

    @Autowired
    private PoblacionServiceImpl poblacionService;

    @Autowired
    private OperacionServiceImpl operacionService;

    @Autowired
    private InmuebleMapper inmuebleMapper;

    public List<InmuebleImagenDTO> findAllBulk() {

        List<Inmueble> inmuebles = inmuebleRepository.findAll();
        List<Long> ids = inmuebles.stream()
                        .map(Inmueble::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);

    }

    public List<InmuebleImagenDTO> findAllActiveBulk(Integer isActivo) {

        List<Inmueble> inmuebles = inmuebleRepository.findAllByActivo(isActivo);
        List<Long> ids = inmuebles.stream()
                        .map(Inmueble::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);

    }

    public List<InmuebleImagenDTO> findAllPortadaBulk(Integer isActivo, Integer isPortada) {

        List<Inmueble> inmuebles = inmuebleRepository.findByActivoAndPortada(1, 1);
        List<Long> ids = inmuebles.stream()
                        .map(Inmueble::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);

    }

    public List<InmuebleImagenDTO> findInmueblesInmobiliariaBulk(Long idInmobiliaria) {

        Inmobiliaria inmobiliaria = inmobiliariaRepository.findById(idInmobiliaria).orElseThrow(() -> new EntityNotFoundException("La inmobiliaria con id " + idInmobiliaria + " no existe"));

        List<Inmueble> inmuebles = inmuebleRepository.findByInmobiliariaId(inmobiliaria.getId());
        List<Long> ids = inmuebles.stream()
                        .map(Inmueble::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);

    }

    public List<InmuebleImagenDTO> finderBulk(Long tipoId, Long poblacionId, Long operacionId) {

        Tipo tipo = tipoService.findById(tipoId);
        Poblacion poblacion = poblacionService.findById(poblacionId);
        Operacion operacion = operacionService.findById(operacionId);

        List<Inmueble> inmuebles = inmuebleRepository.findByTipoAndPoblacionAndOperacionAndActivo(tipo, poblacion, operacion, 1);
        List<Long> ids = inmuebles.stream()
                        .map(Inmueble::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);

    }

    public InmuebleImagenDTO findById(Long id) {
        Inmueble inmueble = inmuebleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El inmueble con id " + id + " no existe"));
		return inmuebleMapper.toDto(inmueble, imagenService);
	}

    public InmuebleImagenDTO save(Inmueble inmueble) {
        Inmueble inmuebleGuardado = inmuebleRepository.save(inmueble);
        return inmuebleMapper.toDto(inmuebleGuardado, imagenService);
    }

    public Inmueble deleteById(Long id) {
        Inmueble inmueble = inmuebleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El inmueble con id " + id + " no existe"));
        inmuebleRepository.delete(inmueble);
        return inmueble;
    }
    

}
