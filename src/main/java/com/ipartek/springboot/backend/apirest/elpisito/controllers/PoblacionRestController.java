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
import com.ipartek.springboot.backend.apirest.elpisito.services.PoblacionServiceImpl;

@RestController
@RequestMapping("/api")
public class PoblacionRestController {

	@Autowired
	private PoblacionServiceImpl poblacionService;

	@GetMapping("/poblaciones")
	public ResponseEntity<List<Poblacion>> findAll() {
		return ResponseEntity.ok(poblacionService.findAll());
	}

	@GetMapping("/poblaciones-activos/{active}")
	public ResponseEntity<List<Poblacion>> findAllActivo(@PathVariable Integer active) {
		return ResponseEntity.ok(poblacionService.findAllByActivo(active));
	}

	@GetMapping("/poblacion/{id}")
	public ResponseEntity<Poblacion> findById(@PathVariable Long id) {
		return ResponseEntity.ok(poblacionService.findById(id));
	}

	@PostMapping("/poblacion")
	public ResponseEntity<Poblacion> create(@RequestBody Poblacion poblacion) {
		return ResponseEntity.status(HttpStatus.CREATED).body(poblacionService.save(poblacion));
	}

	@PutMapping("/poblacion")
	public ResponseEntity<Poblacion> update(@RequestBody Poblacion poblacion) {
		return ResponseEntity.status(HttpStatus.CREATED).body(poblacionService.save(poblacion));
	}

	@PutMapping("/poblacion-activate/{id}")
	public ResponseEntity<Poblacion> delete(@PathVariable Long id) {
		Poblacion poblacion = poblacionService.findById(id);

		if (poblacion.getActivo().equals(1))
			poblacion.setActivo(0);
		else
			poblacion.setActivo(1);

		return ResponseEntity.status(HttpStatus.CREATED).body(poblacionService.save(poblacion));
	}

}
