package com.ipartek.springboot.backend.apirest.elpisito.entities;

import java.io.Serial;
import java.io.Serializable;

import com.ipartek.springboot.backend.apirest.elpisito.utilities.Rol;

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
@Table(name="usuarios")
public class Usuario implements Serializable{

	@Serial
	private static final long serialVersionUID = 6501048567006247146L;
	
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

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	@Column(nullable = false)
	private Integer activo = 1;
	
}
