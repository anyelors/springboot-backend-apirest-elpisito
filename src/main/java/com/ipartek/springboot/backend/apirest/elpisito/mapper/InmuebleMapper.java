package com.ipartek.springboot.backend.apirest.elpisito.mapper;

import java.util.List;
import java.util.Map;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.dtos.InmuebleImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.services.ImagenServiceImpl;
import com.ipartek.springboot.backend.apirest.elpisito.utilities.EntidadImagen;

@Mapper(componentModel = "spring")
public interface InmuebleMapper {

    @Mapping(target = "imagenes", ignore = true)
    InmuebleImagenDTO toDTO(Inmueble inmueble, @Context ImagenServiceImpl imagenService);

    List<InmuebleImagenDTO> toDTOList(List<Inmueble> inmuebles, @Context ImagenServiceImpl imagenService);

    default List<InmuebleImagenDTO> toDtoBulk(List<Inmueble> inmuebles, Map<Long, List<ImagenDTO>> imagenesMap) {
        List<InmuebleImagenDTO> dtos = toDTOList(inmuebles, null);
        for (InmuebleImagenDTO dto : dtos) {
            List<ImagenDTO> imagenes = imagenesMap.getOrDefault(dto.getId(), List.of());
            dto.setImagenes(imagenes);
        }
        return dtos;
    }

    @AfterMapping
    default void cargarImagenes(Inmueble inmueble, @MappingTarget InmuebleImagenDTO dto, @Context ImagenServiceImpl imagenService) {
        if (imagenService != null) {
            List<ImagenDTO> imagenes = imagenService.getImagenes(EntidadImagen.INMUEBLE, inmueble.getId());
            dto.setImagenes(imagenes);
        }
    }

}
