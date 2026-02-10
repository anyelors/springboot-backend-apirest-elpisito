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

import com.ipartek.springboot.backend.apirest.elpisito.entities.Poblacion;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Provincia;
import com.ipartek.springboot.backend.apirest.elpisito.services.ProvinciaServiceImpl;

@RestController
@RequestMapping("/api")
public class ProvinciaRestController {

    @Autowired
    private ProvinciaServiceImpl  provinciaService;

    @GetMapping("/provincias")
	public ResponseEntity<List<Provincia>> findAll() {
		return ResponseEntity.ok(provinciaService.findAll());
	}

    @GetMapping("/provincias-activos/{active}")
	public ResponseEntity<List<Provincia>> findAllActivo(@PathVariable Integer active) {
		return ResponseEntity.ok(provinciaService.findAllByActivo(active));
	}

    @GetMapping("/provincia/{id}")
	public ResponseEntity<Provincia> findById(@PathVariable Long id) {
		return ResponseEntity.ok(provinciaService.findById(id));
	}

    @PostMapping("/provincia")
	public ResponseEntity<Provincia> create(@RequestBody Provincia provincia) {
		return ResponseEntity.status(HttpStatus.CREATED).body(provinciaService.save(provincia));
	}

    @PutMapping("/provincia")
	public ResponseEntity<Provincia> update(@RequestBody Provincia provincia) {
		return ResponseEntity.status(HttpStatus.CREATED).body(provinciaService.save(provincia));
	}

	@PutMapping("/provincia-activate/{id}")
	public ResponseEntity<Provincia> delete(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(provinciaService.deleteById(id));
	}

}
