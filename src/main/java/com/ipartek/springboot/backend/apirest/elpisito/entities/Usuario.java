package com.ipartek.springboot.backend.apirest.elpisito.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Incremental para MySQL
	@Column
	private Long id;
	
	@Column(unique=true, nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String password;
	
	@Column(name="password_open")
	private String passwordOpen;
	
	@Column(unique=true, nullable = false)
	private String email;
	
	@Column
	private Integer activo = 1;
	
}
