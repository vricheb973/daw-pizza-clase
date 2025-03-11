package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Oferta;
import com.daw.persistence.repositories.OfertaRepository;

@Service
public class OfertaService {
	
	@Autowired
	private OfertaRepository ofertaRepository;
	
	//CRUDs
	public List<Oferta> findAll(){
		return this.ofertaRepository.findAll();
	}
	
	public boolean existsOferta(int idOferta){
		return this.ofertaRepository.existsById(idOferta);
	}
	
	public Optional<Oferta> findEntityById(int idOferta){
		return this.ofertaRepository.findById(idOferta);
	}
	
	public Oferta create(Oferta oferta) {
		marcarInactivas(oferta.getIdPizza());
		
		oferta.setActiva(true);
		
		return this.ofertaRepository.save(oferta);
	}
	
	public Oferta save(Oferta oferta) {
		Oferta ofertaBD = this.ofertaRepository.findById(oferta.getId()).get();
		
		oferta.setActiva(ofertaBD.getActiva());
		
		return this.ofertaRepository.save(oferta);
	}
	
	public boolean delete(int idOferta) {
		boolean result = false;
		
		if(this.ofertaRepository.existsById(idOferta)) {
			this.ofertaRepository.deleteById(idOferta);
			result = true;
		}
		
		return result;
	}
	
	public List<Oferta> findByIdPizza(int idPizza){
		return this.ofertaRepository.findByIdPizza(idPizza);
	}
	
	public void marcarInactivas(int idPizza) {
		List<Oferta> ofertas = this.ofertaRepository.findByIdPizza(idPizza);
		
		for(Oferta off : ofertas) {
			off.setActiva(false);
			this.ofertaRepository.save(off);
		}
	}
	
	public Oferta marcarDesmarcarActivas(int idOferta) {
		Oferta ofertaBD = this.ofertaRepository.findById(idOferta).get();
		
		this.marcarInactivas(ofertaBD.getIdPizza());
		
		if(ofertaBD.getActiva()) {
			ofertaBD.setActiva(false);
		}
		else {
			ofertaBD.setActiva(true);
		}
		
		return this.ofertaRepository.save(ofertaBD);
	}
	
	public Optional<Oferta> findActivaByIdPizza(int idPizza){
		return this.ofertaRepository.findByIdPizzaAndActivaTrue(idPizza);
	}
	
	

}
