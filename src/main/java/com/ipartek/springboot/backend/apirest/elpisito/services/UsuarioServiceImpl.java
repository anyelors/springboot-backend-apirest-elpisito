package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;
import com.ipartek.springboot.backend.apirest.elpisito.mapper.UsuarioMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioServiceImpl implements GeneralService<Usuario> {

	// UsuarioRepository Automaticamente hereda todos los metodos de JpaRepository y
	// metodos propios
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioMapper usuarioMapper;

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public List<Usuario> findAllByActivo(Integer isActivo) {
		return usuarioRepository.findAllByActivo(isActivo);
	}

	@Override
	public Usuario findById(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("El usuario con id " + id + " no existe"));
	}

	@Override
	public Usuario save(Usuario objeto) {
		objeto.setPasswordOpen(objeto.getPassword());
		return usuarioRepository.save(objeto);
	}

	@Override
	public Usuario deleteById(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("El usuario con id " + id + " no existe"));
		usuarioRepository.deleteById(id);

		return usuario;
	}

	public Usuario completaUsuarioRequestRespetandoNullus(Usuario usuarioRequest) {
		Usuario usuarioBD = usuarioRepository.findById(usuarioRequest.getId()).orElseThrow(
				() -> new EntityNotFoundException("El usuario con id " + usuarioRequest.getId() + " no existe"));

		usuarioMapper.updateUsuarioFromDTO(usuarioRequest, usuarioBD);
		return usuarioBD;
	}

}
