package com.ipartek.springboot.backend.apirest.elpisito.entities;

import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "imagenes")
public class Imagen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	private String nombre;

	@Enumerated(EnumType.STRING)
	@Column(name = "entidad_imagen")
	private EntidadImagen entidadImagen;

	@Column(name = "entidad_id")
	private Long entidadId;

	@Column(name = "alt_imagen")
	private String altImagen;

}
