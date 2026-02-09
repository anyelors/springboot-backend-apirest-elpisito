package com.ipartek.springboot.backend.apirest.elpisito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Usuario> findAll() {
		return usuarioService.findAll();
	}

	@GetMapping("/usuarios-activos/{active}")
	public List<Usuario> findAllActivo(@PathVariable Integer active) {
		return usuarioService.findAllByActivo(active);
	}

	@GetMapping("/usuario/{id}")
	public Usuario findById(@PathVariable Long id) {
		return usuarioService.findById(id);
	}

	@PostMapping("/usuario")
	public UsuarioDTO create(@RequestBody Usuario usuario) {
		Usuario usuarioF = usuarioService.save(usuario);
		return new UsuarioDTO(usuarioF.getId(), usuarioF.getNombre(), usuarioF.getRol().name());
	}

	@PutMapping("/usuario")
	public UsuarioDTO update(@RequestBody Usuario usuario) {
		Usuario usuarioF = usuarioService.save(usuario);
		return new UsuarioDTO(usuarioF.getId(), usuarioF.getNombre(), usuarioF.getRol().name());
	}

	@DeleteMapping("/usuario/{id}")
	public void deleteById(@PathVariable Long id) {
		usuarioService.deleteById(id);
	}

}
