package com.daw.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "oferta")
@Getter
@Setter
@NoArgsConstructor
public class Oferta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_pizza")
	private int idPizza;
	
	private String nombre;
	
	private Boolean activa;
	
	private Double descuento;
	
	@ManyToOne
	@JoinColumn(name = "id_pizza", referencedColumnName = "id", insertable = false, updatable = false)
	private Pizza pizza;
	
	

}
