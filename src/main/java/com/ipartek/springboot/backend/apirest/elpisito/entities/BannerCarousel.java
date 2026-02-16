package com.ipartek.springboot.backend.apirest.elpisito.entities;

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
@Table(name = "banners_carousel")
public class BannerCarousel {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	private String titular;

	@Column
	private String claim;

    @Column
	private Integer activo;

	@ManyToMany(mappedBy = "bannersCarousel")
	private java.util.Set<Tematica> tematicas;

	@PrePersist
	public void prePersist() {
		if (this.activo == null)
			this.activo = 1;
	}

}
