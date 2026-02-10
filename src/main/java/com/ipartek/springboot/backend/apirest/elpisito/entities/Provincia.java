package com.ipartek.springboot.backend.apirest.elpisito.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
@Table(name = "provincias")
public class Provincia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Incremental para MySQL
	@Column
	private Long id;

	@Column
	private String nombre;

	@Column
	private Integer activo;

	@JsonIgnore
	@OneToMany(mappedBy = "provincia")
	private List<Poblacion> poblaciones;

	@PrePersist
	public void prePersist() {
		if (this.activo == null)
			this.activo = 1;
	}

}
