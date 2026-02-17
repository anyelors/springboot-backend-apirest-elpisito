package com.ipartek.springboot.backend.apirest.elpisito.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipartek.springboot.backend.apirest.elpisito.utilities.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "usuarios")
public class Usuario implements Serializable {

	@Serial
	private static final long serialVersionUID = 6501048567006247146L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Incremental para MySQL
	@Column
	private Long id;

	@Column(unique = true, nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String password;

	@Column(name = "password_open")
	private String passwordOpen;

	@Column(unique = true, nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Rol rol;

	@Column(nullable = false)
	private Integer activo;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "usuario_inmueble", 
				joinColumns = { @JoinColumn(name = "usuario_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "inmueble_id") })
	private Set<Inmueble> inmueblesFavoritos;

	@PrePersist
	public void prePersist() {
		if (this.activo == null)
			this.activo = 1;
		if (this.rol == null)
			this.rol = Rol.ROLE_USUARIO;
	}

}
