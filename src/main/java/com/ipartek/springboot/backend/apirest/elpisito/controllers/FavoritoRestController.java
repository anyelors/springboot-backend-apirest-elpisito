package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.services.FavoritoServiceImpl;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class FavoritoRestController {

    @Autowired
    private FavoritoServiceImpl favoritoService;

    @PostMapping("/favorito")
    public ResponseEntity<InmuebleImagenDTO> addFavorito(@RequestParam Long usuarioId, @RequestParam Long inmuebleId) {
        var inmuebleImagenDTO = favoritoService.addFavorito(usuarioId, inmuebleId);
        return ResponseEntity.ok(inmuebleImagenDTO);
    }

    @DeleteMapping("/favorito")
    public ResponseEntity<InmuebleImagenDTO> deleteFavorito(@RequestParam Long usuarioId, @RequestParam Long inmuebleId) {
        var inmuebleImagenDTO = favoritoService.deleteFavorito(usuarioId, inmuebleId);
        return ResponseEntity.ok(inmuebleImagenDTO);
    }

    @GetMapping("/favoritos-usuario/{usuarioId}")
    public ResponseEntity<List<InmuebleImagenDTO>> getFavoritos(@PathVariable Long usuarioId) {
        var favoritos = favoritoService.findFavoritos(usuarioId);
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/favoritosid-usuario/{usuarioId}")
    public ResponseEntity<List<InmuebleIdDTO>> getFavoritosIds(@PathVariable Long usuarioId) {
        var favoritosIds = favoritoService.findIdsFavoritos(usuarioId);
        return ResponseEntity.ok(favoritosIds);
    }

}
