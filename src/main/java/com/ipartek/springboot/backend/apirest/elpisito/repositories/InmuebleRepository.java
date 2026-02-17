package com.ipartek.springboot.backend.apirest.elpisito.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Inmueble;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Operacion;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Poblacion;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Tipo;

@Repository
public interface InmuebleRepository extends JpaRepository<Inmueble, Long> {

    List<Inmueble> findAllByActivo(Integer isActivo);

    List<Inmueble> findByActivoAndPortada(Integer isActivo, Integer isPortada);

    List<Inmueble> findByInmobiliariaId(Long idInmobiliaria);

    List<Inmueble> findByTipoAndPoblacionAndOperacionAndActivo(Tipo tipo, Poblacion poblacion, Operacion operacion, Integer isActivo); 

}
