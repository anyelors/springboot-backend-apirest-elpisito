package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.UsuarioDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;
import com.ipartek.springboot.backend.apirest.elpisito.services.UsuarioServiceImpl;

@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@GetMapping("/usuarios")
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		return ResponseEntity.ok(usuarioService.findAll());
	}

	@GetMapping("/usuarios-activos/{active}")
	public ResponseEntity<List<UsuarioDTO>> findAllActivo(@PathVariable Integer active) {
		return ResponseEntity.ok(usuarioService.findAllByActivo(active));
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
	}

	@PostMapping("/usuario")
	public ResponseEntity<UsuarioDTO> create(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
	}

	@PutMapping("/usuario")
	public ResponseEntity<UsuarioDTO> update(@RequestBody Usuario usuario) {
		Usuario usuarioCompleto = usuarioService.completaUsuarioRequestRespetandoNullus(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioCompleto));
	}

	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDTO> deleteById(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioService.deleteById(id));
	}

}
