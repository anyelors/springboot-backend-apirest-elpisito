package com.ipartek.springboot.backend.apirest.elpisito.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "banners")
public class Banner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	private String titular;

	@Column
	private String claim;

	@Column
	private String link;

	@Column
	private Integer activo;

	@PrePersist
	public void prePersist() {
		if (this.activo == null)
			this.activo = 1;
	}

	@ManyToMany(mappedBy = "bannersPagina")
	private Set<Pagina> paginasBanner;

}
