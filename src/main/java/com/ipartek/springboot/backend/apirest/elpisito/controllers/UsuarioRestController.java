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
		List<Usuario> usuario = usuarioService.findAll();
		List<UsuarioDTO> usuariosDTO = usuario.stream()
				.map(u -> new UsuarioDTO(u.getId(), u.getNombre(), u.getRol().name())).toList();

		return ResponseEntity.ok(usuariosDTO);
	}

	@GetMapping("/usuarios-activos/{active}")
	public ResponseEntity<List<UsuarioDTO>> findAllActivo(@PathVariable Integer active) {
		List<Usuario> usuario = usuarioService.findAllByActivo(active);
		List<UsuarioDTO> usuariosDTO = usuario.stream()
				.map(u -> new UsuarioDTO(u.getId(), u.getNombre(), u.getRol().name())).toList();

		return ResponseEntity.ok(usuariosDTO);
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
		Usuario usuario = usuarioService.findById(id);
		UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getRol().name());

		return ResponseEntity.ok(usuarioDTO);
	}

	@PostMapping("/usuario")
	public ResponseEntity<UsuarioDTO> create(@RequestBody Usuario usuario) {
		Usuario usuarioF = usuarioService.save(usuario);
		UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioF.getId(), usuarioF.getNombre(), usuarioF.getRol().name());

		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
	}

	@PutMapping("/usuario")
	public ResponseEntity<UsuarioDTO> update(@RequestBody Usuario usuario) {
		Usuario usuarioF = usuarioService.save(usuario);
		UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioF.getId(), usuarioF.getNombre(), usuarioF.getRol().name());

		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
	}

	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDTO> deleteById(@PathVariable Long id) {
		Usuario usuario = usuarioService.deleteById(id);
		UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getRol().name());

		return ResponseEntity.ok(usuarioDTO);
	}

}
