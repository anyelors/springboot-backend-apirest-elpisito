package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.UsuarioDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.UsuarioMapper;
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

	public List<UsuarioDTO> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarioMapper.toDTOList(usuarios);
	}

	public List<UsuarioDTO> findAllByActivo(Integer isActivo) {
		return usuarioMapper.toDTOList(usuarioRepository.findAllByActivo(isActivo));
	}

	public UsuarioDTO findById(Long id) {
		return usuarioMapper.toDTO(usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("El usuario con id " + id + " no existe")));
	}

	public UsuarioDTO save(Usuario objeto) {
		objeto.setPasswordOpen(objeto.getPassword());
		Usuario usuario = usuarioRepository.save(objeto);

		return usuarioMapper.toDTO(usuario);
	}

	public UsuarioDTO deleteById(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("El usuario con id " + id + " no existe"));
		usuarioRepository.deleteById(id);

		return usuarioMapper.toDTO(usuario);
	}

	public Usuario completaUsuarioRequestRespetandoNullus(Usuario usuarioRequest) {
		Usuario usuarioBD = usuarioRepository.findById(usuarioRequest.getId()).orElseThrow(
				() -> new EntityNotFoundException("El usuario con id " + usuarioRequest.getId() + " no existe"));

		usuarioMapper.updateUsuarioFromDTO(usuarioRequest, usuarioBD);
		return usuarioBD;
	}

}
