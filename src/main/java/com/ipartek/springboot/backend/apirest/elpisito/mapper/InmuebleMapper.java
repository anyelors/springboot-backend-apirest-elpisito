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
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.services.ImagenServiceImpl;

@Mapper(componentModel = "spring")
public interface InmuebleMapper {

    @Mapping(target = "imagenes", ignore = true)
    InmuebleImagenDTO toDto(Inmueble inmueble, @Context ImagenServiceImpl imagenService);

    List<InmuebleImagenDTO> toDTOList(List<Inmueble> inmuebles, @Context ImagenServiceImpl imagenService);

    default List<InmuebleImagenDTO> toDtoBulk(List<Inmueble> inmuebles, Map<Long, List<ImagenDTO>> imagenesMap) {
        List<InmuebleImagenDTO> dtos = toDTOList(inmuebles, null);
        for (InmuebleImagenDTO dto : dtos) {
            dto.setImagenes(imagenesMap.getOrDefault(dto.getId(), List.of()));
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

    Inmueble toEntity(InmuebleImagenDTO inmuebleImagenDTO);

}
