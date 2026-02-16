package com.ipartek.springboot.backend.apirest.elpisito.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "inmuebles")
public class Inmueble {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

    @Column
    private String via;

    @Column
    private String claim;

    @Column(name = "nombre_via")
    private String nombreVia;

    @Column
    private String numero;

    @Column
    private String planta;

    @Column
    private String puerta;

    @Column
    private String apertura;

    @Column
    private String orientacion;

    @Column(name = "superficie_util")
    private Double superficieUtil;

    @Column(name = "superficie_construida")
    private Double superficieConstruida;

    @Column
    private Double precio;

    @Column
    private Integer habitaciones;

    @Column
	private Integer banhos;

    @Column(length = 3500)
	private String descripcion;

    @Column
	private String calefaccion;

    @Column
	private Integer amueblado;

    @Column
	private Integer balcones;

    @Column
	private Integer garajes;

    @Column
	private Integer piscina;

    @Column
	private Integer trastero;

    @Column
	private Integer ascensor;

    @Column
	private Integer jardin;

    @Column
	private Integer tendedero;

    @Column
	private Integer portada;

    @Column
	private Integer oportunidad;

    @Column
	private Integer activo;

    @ManyToOne
    @JoinColumn(name = "tipo")
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "operacion")
    private Operacion operacion;

    @ManyToOne
    @JoinColumn(name = "poblacion")
    private Poblacion poblacion;

    @ManyToMany(mappedBy = "inmueblesFavoritos")
    private Set<Usuario> usuariosQueLoFavoritean;

    @ManyToOne
    @JoinColumn(name = "inmobiliaria")
    private Inmobiliaria inmobiliaria;

	@PrePersist
	public void prePersist() {
		if (this.activo == null)
			this.activo = 1;

        if (this.portada == null)
			this.portada = 0;

        if (this.oportunidad == null)
			this.oportunidad = 0;
	}

}
