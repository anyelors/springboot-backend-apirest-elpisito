package com.ipartek.springboot.backend.apirest.elpisito.utilities;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUsuarioFromDTO(Usuario usuarioRequest, @MappingTarget Usuario usuarioEntity);

}
