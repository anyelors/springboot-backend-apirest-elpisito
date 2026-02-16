package com.ipartek.springboot.backend.apirest.elpisito.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "tematicas")
public class Tematica {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(unique = true)
	private String nombre;

	@Column
	private Integer activo;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "tematica_bannercarousel", 
        joinColumns = { @jakarta.persistence.JoinColumn(name = "tematica_id") }, 
        inverseJoinColumns = { @jakarta.persistence.JoinColumn(name = "banner_carousel_id") })
    private java.util.Set<BannerCarousel> bannersCarousel;

	@PrePersist
	public void prePersist() {
		if (this.activo == null)
			this.activo = 1;
	}

}
