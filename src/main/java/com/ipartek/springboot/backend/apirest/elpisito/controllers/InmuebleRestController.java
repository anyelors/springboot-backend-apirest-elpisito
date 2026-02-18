package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.services.InmuebleServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class InmuebleRestController {

    @Autowired
    private InmuebleServiceImpl inmuebleService;

    @GetMapping("/inmuebles")
	public ResponseEntity<List<InmuebleImagenDTO>> findAll() {
		return ResponseEntity.ok(inmuebleService.findAllBulk());
	}

    @GetMapping("/inmuebles-activos/{active}")
	public ResponseEntity<List<InmuebleImagenDTO>> findAllActivo(@PathVariable Integer active) {
		return ResponseEntity.ok(inmuebleService.findAllActiveBulk(active));
	}

    @GetMapping("/inmuebles-portada")
	public ResponseEntity<List<InmuebleImagenDTO>> findAllActivoPortada(Integer active, Integer portada) {
		return ResponseEntity.ok(inmuebleService.findAllPortadaBulk(active, portada));
	}

	@GetMapping("/inmuebles-inmobiliaria/{idInmobiliaria}")
	public ResponseEntity<List<InmuebleImagenDTO>> findAllInmueblesInmobiliaria(@PathVariable Long idInmobiliaria) {
		return ResponseEntity.ok(inmuebleService.findInmueblesInmobiliariaBulk(idInmobiliaria));
	}

	@GetMapping("/inmuebles/finder/{tipoId}/{poblacionId}/{operacionId}")
	public ResponseEntity<List<InmuebleImagenDTO>> finder(@PathVariable Long tipoId, @PathVariable Long poblacionId, @PathVariable Long operacionId) {
		return ResponseEntity.ok(inmuebleService.finderBulk(tipoId, poblacionId, operacionId));
	}

	@GetMapping("/inmueble/{id}")
	public ResponseEntity<InmuebleImagenDTO> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(inmuebleService.findById(id));
	}

	@PostMapping("/inmueble")
	public ResponseEntity<InmuebleImagenDTO> create(@RequestBody Inmueble inmueble) {
		return ResponseEntity.status(HttpStatus.CREATED).body(inmuebleService.save(inmueble));
	}

	@PutMapping("/inmueble")
	public ResponseEntity<InmuebleImagenDTO> update(@RequestBody Inmueble inmueble) {
		return ResponseEntity.status(HttpStatus.CREATED).body(inmuebleService.save(inmueble));
	}

	@DeleteMapping("/inmueble/{id}")
	public ResponseEntity<Inmueble> delete(@PathVariable Long id) {
		Inmueble inmueble = inmuebleService.deleteById(id);
		return ResponseEntity.ok(inmueble);
	}
	

}
