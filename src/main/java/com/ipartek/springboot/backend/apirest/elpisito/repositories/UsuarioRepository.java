package com.ipartek.springboot.backend.apirest.elpisito.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	List<Usuario> findAllByActivo(Integer isActivo);

}
