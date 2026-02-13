package com.ipartek.springboot.backend.apirest.elpisito.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "paginas")
public class Pagina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(unique = true)
	private String nombre;

	@Column
	private Integer activo;

	@PrePersist
	public void prePersist() {
		if (this.activo == null)
			this.activo = 1;
	}

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "pagina_banner", joinColumns = { @JoinColumn(name = "pagina_id") }, inverseJoinColumns = {
			@JoinColumn(name = "banner_id") })
	private Set<Banner> bannersPagina;

}
