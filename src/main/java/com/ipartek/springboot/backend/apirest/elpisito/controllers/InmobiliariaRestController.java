package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmobiliariaImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmobiliaria;
import com.ipartek.springboot.backend.apirest.elpisito.services.InmobiliariaServiceImpl;

@RestController
@RequestMapping("/api")
public class InmobiliariaRestController {

    @Autowired
    private InmobiliariaServiceImpl inmobiliariaService;

    @GetMapping("/inmobiliarias")
	public ResponseEntity<List<InmobiliariaImagenDTO>> findAll() {
		return ResponseEntity.ok(inmobiliariaService.findAllBulk());
	}

    @GetMapping("/inmobiliarias-activas/{active}")
	public ResponseEntity<List<InmobiliariaImagenDTO>> findAllActivo(@PathVariable Integer active) {
		return ResponseEntity.ok(inmobiliariaService.findAllActiveBulk(active));
	}

    @GetMapping("/inmobiliaria/{id}")
	public ResponseEntity<InmobiliariaImagenDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(inmobiliariaService.findById(id));
	}

    @PostMapping("/inmobiliaria")
	public ResponseEntity<InmobiliariaImagenDTO> create(@RequestBody Inmobiliaria inmobiliaria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(inmobiliariaService.save(inmobiliaria));
	}

    @PutMapping("/inmobiliaria")
	public ResponseEntity<InmobiliariaImagenDTO> update(@RequestBody Inmobiliaria inmobiliaria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(inmobiliariaService.save(inmobiliaria));
	}

	@PutMapping("/inmobiliaria-activate/{id}")
	public ResponseEntity<InmobiliariaImagenDTO> delete(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(inmobiliariaService.deleteById(id));
	}

}
