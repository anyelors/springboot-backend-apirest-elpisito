package com.ipartek.springboot.backend.apirest.elpisito.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.UsuarioDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUsuarioFromDTO(Usuario usuarioRequest, @MappingTarget Usuario usuarioEntity);

	UsuarioDTO toDTO(Usuario usuario);

	List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

}
