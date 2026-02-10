package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.util.List;

public interface GeneralService<T> {

	List<T> findAll();

	List<T> findAllByActivo(Integer active);

	T findById(Long id);

	T save(T objeto);

	T deleteById(Long id);

}
