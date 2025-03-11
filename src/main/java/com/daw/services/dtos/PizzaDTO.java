package com.daw.services.dtos;

import java.util.List;

import com.daw.persistence.entities.Oferta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PizzaDTO {
		
	private Integer id;
	private String nombre;
	private String descripcion;
	private Double precio;
	private Boolean vegetariana;
	private Boolean vegana;
	private Boolean disponible;
	private List<Oferta> ofertasActivas;

}
