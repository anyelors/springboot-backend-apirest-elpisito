package com.ipartek.springboot.backend.apirest.elpisito.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Imagen;
import com.ipartek.springboot.backend.apirest.elpisito.utilities.EntidadImagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    List<Imagen> findByEntidadImagenAndEntidadId(EntidadImagen entidadImagen, Long id);

    Optional<Imagen> findFirstByEntidadImagenAndEntidadId(EntidadImagen entidadImagen, Long id);

}
