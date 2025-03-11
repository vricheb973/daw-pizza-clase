package com.daw.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Oferta;

public interface OfertaRepository extends ListCrudRepository<Oferta, Integer> {
	
	List<Oferta> findByIdPizza(int idPizza);
	Optional<Oferta> findByIdPizzaAndActivaTrue(int idPizza);

}
