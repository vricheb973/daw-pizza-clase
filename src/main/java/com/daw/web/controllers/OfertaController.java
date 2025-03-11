package com.daw.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Direccion;
import com.daw.persistence.entities.Oferta;
import com.daw.services.OfertaService;
import com.daw.services.PizzaService;

@RestController
@RequestMapping("/ofertas")
public class OfertaController {
	
	@Autowired
	private OfertaService ofertaService;

	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping
	public ResponseEntity<List<Oferta>> list(){
		return ResponseEntity.ok(this.ofertaService.findAll());
	}
	
	@GetMapping("/{idOferta}")
	public ResponseEntity<Oferta> findById(@PathVariable int idOferta) {
		Optional<Oferta> oferta = this.ofertaService.findEntityById(idOferta);
		
		if(oferta.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oferta.get());
	}
	
	@PostMapping
	public ResponseEntity<Oferta> create(@RequestBody Oferta oferta){
		if(!this.pizzaService.existsPizza(oferta.getIdPizza())) {
			return ResponseEntity.notFound().build();
		}
		
		return new ResponseEntity<Oferta>(this.ofertaService.create(oferta), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idOferta}")
	public ResponseEntity<Oferta> update(@PathVariable int idOferta, @RequestBody Oferta oferta){
		if(!this.ofertaService.existsOferta(idOferta)) {
			return ResponseEntity.notFound().build();
		}
		if(idOferta != oferta.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if(!this.pizzaService.existsPizza(oferta.getIdPizza())) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.ofertaService.save(oferta));
	}
	
	@DeleteMapping("/{idOferta}")
	public ResponseEntity<Direccion> delete(@PathVariable int idOferta){
		if(this.ofertaService.delete(idOferta)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{idOferta}/activa")
	public ResponseEntity<Oferta> marcarDesmarcarActiva(@PathVariable int idOferta){
		if(this.ofertaService.existsOferta(idOferta)) {
			return ResponseEntity.ok(this.ofertaService.marcarDesmarcarActivas(idOferta));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	
	

}
