package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.UsuarioDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;
import com.ipartek.springboot.backend.apirest.elpisito.mappers.UsuarioMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioServiceImpl {

	// UsuarioRepository Automaticamente hereda todos los metodos de JpaRepository y
	// metodos propios
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<UsuarioDTO> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarioMapper.toDtoList(usuarios);
	}

	public List<UsuarioDTO> findAllByActivo(Integer isActivo) {
		return usuarioMapper.toDtoList(usuarioRepository.findAllByActivo(isActivo));
	}

	public UsuarioDTO findById(Long id) {
		return usuarioMapper.toDto(usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("El usuario con id " + id + " no existe")));
	}

	public UsuarioDTO findByName(String nombre) {
		return usuarioMapper.toDto(usuarioRepository.findByNombre(nombre)
				.orElseThrow(() -> new EntityNotFoundException("El usuario con el nombre " + nombre + " no existe")));
	}

	public UsuarioDTO save(Usuario objeto) {

		objeto.setPasswordOpen(objeto.getPassword());
		objeto.setPassword(passwordEncoder.encode(objeto.getPassword()));
		Usuario usuario = usuarioRepository.save(objeto);

		return usuarioMapper.toDto(usuario);
	}

	public UsuarioDTO deleteById(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("El usuario con id " + id + " no existe"));
		usuarioRepository.deleteById(id);

		return usuarioMapper.toDto(usuario);
	}

	public Usuario completaUsuarioRequestRespetandoNullus(Usuario usuarioRequest) {
		Usuario usuarioBD = usuarioRepository.findById(usuarioRequest.getId()).orElseThrow(
				() -> new EntityNotFoundException("El usuario con id " + usuarioRequest.getId() + " no existe"));

		usuarioMapper.updateUsuarioFromDto(usuarioRequest, usuarioBD);
		return usuarioBD;
	}

}
