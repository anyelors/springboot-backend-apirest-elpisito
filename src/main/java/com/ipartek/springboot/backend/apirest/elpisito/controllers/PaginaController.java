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

import com.ipartek.springboot.backend.apirest.elpisito.entities.Pagina;
import com.ipartek.springboot.backend.apirest.elpisito.services.PaginaServiceImpl;

@RestController
@RequestMapping("/api")
public class PaginaController {

    @Autowired
    private PaginaServiceImpl paginaService;

    @GetMapping("/paginas")
	public ResponseEntity<List<Pagina>> findAll() {
		return ResponseEntity.ok(paginaService.findAll());
	}

    @GetMapping("/paginas-activas/{active}")
	public ResponseEntity<List<Pagina>> findAllActivas(@PathVariable Integer active) {
		return ResponseEntity.ok(paginaService.findAllByActivo(active));
	}

    @GetMapping("/pagina/{id}")
	public ResponseEntity<Pagina> findById(@PathVariable Long id) {
		return ResponseEntity.ok(paginaService.findById(id));
	}

    @PostMapping("/pagina")
	public ResponseEntity<Pagina> create(@RequestBody Pagina pagina) {
		return ResponseEntity.status(HttpStatus.CREATED).body(paginaService.save(pagina));
	}

    @PutMapping("/pagina")
	public ResponseEntity<Pagina> update(@RequestBody Pagina pagina) {
		return ResponseEntity.status(HttpStatus.CREATED).body(paginaService.save(pagina));
	}

	@PutMapping("/pagina-activate/{id}")
	public ResponseEntity<Pagina> delete(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(paginaService.deleteById(id));
	}

}
