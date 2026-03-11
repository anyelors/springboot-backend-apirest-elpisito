package com.ipartek.springboot.backend.apirest.elpisito.mappers;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleIdDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.services.FavoritoServiceImpl;

@Mapper(componentModel = "spring", uses = { InmobiliariaMapper.class })
public interface InmuebleMapper {

	@Mapping(target = "imagenes", ignore = true)
	InmuebleImagenDTO toDto(Inmueble inmueble);

	List<InmuebleImagenDTO> toDtoList(List<Inmueble> inmuebles);

	List<InmuebleIdDTO> toIdDtoList(List<Inmueble> inmuebles);

	@Mapping(target = "usuariosQueLoFavoritean", ignore = true)
	Inmueble toEntity(InmuebleImagenDTO inmuebleImagenDTO, @Context FavoritoServiceImpl favoritoService);

	List<Inmueble> toEntityList(List<InmuebleImagenDTO> dtos, @Context FavoritoServiceImpl favoritoService);

}
