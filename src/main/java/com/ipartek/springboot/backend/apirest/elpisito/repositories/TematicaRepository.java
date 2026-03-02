package com.ipartek.springboot.backend.apirest.elpisito.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ipartek.springboot.backend.apirest.elpisito.entities.Tematica;

@Repository
public interface TematicaRepository extends JpaRepository<Tematica, Long> {

	List<Tematica> findAllByActivo(Integer isActivo);

	List<Tematica> findByActual(Integer actual);

	@Modifying
	@Query("UPDATE Tematica t SET t.actual = 0")
	void desactivarAll();

}
