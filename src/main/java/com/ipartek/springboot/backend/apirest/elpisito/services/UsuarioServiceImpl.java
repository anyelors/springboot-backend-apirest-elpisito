package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements GeneralService<Usuario> {

	// UsuarioRepository Automaticamente hereda todos los metodos de JpaRepository y
	// metodos propios
	@Autowired
	private UsuarioRepository usuarioRepository;

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
				.orElseThrow(() -> new RuntimeException("El usuario con id " + id + " no existe"));
	}

	@Override
	public Usuario save(Usuario objeto) {
		objeto.setPasswordOpen(objeto.getPassword());
		return usuarioRepository.save(objeto);
	}

	@Override
	public void deleteById(Long id) {
		usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("El usuario con id " + id + " no existe"));
		usuarioRepository.deleteById(id);
	}

}
