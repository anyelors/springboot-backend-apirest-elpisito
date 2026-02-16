package com.ipartek.springboot.backend.apirest.elpisito.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "poblaciones")
public class Poblacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Incremental para MySQL
	@Column
	private Long id;

	@Column
	private String nombre;

	@Column
    private String cp;

	@Column
	private Integer activo;

	@ManyToOne
	@JoinColumn(name = "provincia")
	private Provincia provincia;

	@JsonIgnore
	@OneToMany(mappedBy = "poblacion")
	private List<Inmueble> inmuebles;

	@PrePersist
	public void prePersist() {
		if (this.activo == null)
			this.activo = 1;
	}

}
