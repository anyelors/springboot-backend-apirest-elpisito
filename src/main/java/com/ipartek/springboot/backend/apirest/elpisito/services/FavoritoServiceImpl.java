package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.InmuebleMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.InmuebleRepository;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FavoritoServiceImpl {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Autowired
    private ImagenServiceImpl imagenService;

    @Autowired
    private InmuebleMapper inmuebleMapper;

    private Usuario findUsuarioById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("El usuario con id " + usuarioId + " no existe"));
    }

    private Inmueble findInmuebleById(Long inmuebleId) {
        return inmuebleRepository.findById(inmuebleId)
            .orElseThrow(() -> new EntityNotFoundException("El inmueble con id " + inmuebleId + " no existe"));
    }

    public InmuebleImagenDTO addFavorito(Long usuarioId, Long inmuebleId) {
        var usuario = findUsuarioById(usuarioId);
        var inmueble = findInmuebleById(inmuebleId);

        usuario.getInmueblesFavoritos().add(inmueble);
        usuarioRepository.save(usuario);

        return inmuebleMapper.toDto(inmueble, imagenService);
    }

    public InmuebleImagenDTO deleteFavorito(Long usuarioId, Long inmuebleId) {
        var usuario = findUsuarioById(usuarioId);
        var inmueble = findInmuebleById(inmuebleId);

        usuario.getInmueblesFavoritos().remove(inmueble);
        usuarioRepository.save(usuario);

        return inmuebleMapper.toDto(inmueble, imagenService);
    }

    public List<InmuebleImagenDTO> findFavoritos(Long usuarioId) {
        var usuario = findUsuarioById(usuarioId);

        // Convertimos el Set a List para poder trabajar con streams
        List<Inmueble> inmuebles = new ArrayList<>(usuario.getInmueblesFavoritos());

        List<Long> ids = inmuebles.stream()
                        .map(Inmueble::getId)
                        .toList();

        Map<Long, List<ImagenDTO>> mapaImagenes = imagenService.getImagenesPorEntidadBulk(EntidadImagen.INMUEBLE, ids);

        return inmuebleMapper.toDtoBulk(inmuebles, mapaImagenes);
    }

    public List<InmuebleIdDTO> findIdsFavoritos(Long usuarioId) {
        var usuario = findUsuarioById(usuarioId);

        // Convertimos el Set a List para poder trabajar con streams
        List<Inmueble> inmuebles = new ArrayList<>(usuario.getInmueblesFavoritos());
        return inmuebleMapper.toIdDtoList(inmuebles);
    }

}
