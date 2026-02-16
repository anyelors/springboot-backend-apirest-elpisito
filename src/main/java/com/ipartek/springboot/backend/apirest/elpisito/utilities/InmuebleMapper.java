package com.ipartek.springboot.backend.apirest.elpisito.utilities;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.services.ImagenServiceImpl;

@Mapper(componentModel = "spring")
public interface InmuebleMapper {

    @Mapping(target = "imagenes", ignore = true)
    InmuebleImagenDTO toDTO(Inmueble inmueble, @Context ImagenServiceImpl imagenService);

}
