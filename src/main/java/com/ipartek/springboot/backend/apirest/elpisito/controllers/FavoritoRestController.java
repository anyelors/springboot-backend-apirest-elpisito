package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.services.FavoritoServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;

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
    

}
