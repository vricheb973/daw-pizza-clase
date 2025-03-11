package com.daw.services.mappers;

import java.util.ArrayList;
import java.util.List;

import com.daw.persistence.entities.Oferta;
import com.daw.persistence.entities.Pizza;
import com.daw.services.dtos.PizzaDTO;

public class PizzaMapper {
	
	public static PizzaDTO toDto(Pizza pizza) {
		PizzaDTO dto = new PizzaDTO();
		
		dto.setId(pizza.getId());
		dto.setNombre(pizza.getNombre());
		dto.setDescripcion(pizza.getDescripcion());
		dto.setPrecio(pizza.getPrecio());
		dto.setVegetariana(pizza.getVegetariana());
		dto.setVegana(pizza.getVegana());
		dto.setDisponible(pizza.getDisponible());
		
		List<Oferta> ofertas = new ArrayList<Oferta>();

		for(Oferta off : pizza.getOfertas()) {
			if(off.getActiva()) {
				ofertas.add(off);
			}
		}
		
		dto.setOfertasActivas(ofertas);
		
		return dto;
	}

}
