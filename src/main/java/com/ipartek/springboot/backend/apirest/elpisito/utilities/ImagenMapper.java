package com.ipartek.springboot.backend.apirest.elpisito.utilities;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Imagen;

@Mapper(componentModel = "spring")
public interface ImagenMapper {

    @Mapping(target = "url", expression = "java(construirUrl(imagen))")
    @Mapping(target = "alt", expression = "java(imagen.getAltImagen())")
    ImagenDTO toDTO(Imagen imagen);

    List<ImagenDTO> toDTOList(List<Imagen> imagenes);

    default String construirUrl(Imagen imagen) {
        return "/api/imagenes/" + imagen.getEntidadImagen() + "/" + imagen.getEntidadId() + "/" + imagen.getNombre();
    }

}
