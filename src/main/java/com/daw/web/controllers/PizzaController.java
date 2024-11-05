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
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Pizza;
import com.daw.services.PizzaService;

@RestController
public class PizzaController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping
	public ResponseEntity<List<Pizza>> list(){
		return ResponseEntity.ok(this.pizzaService.getAll());
	}
	
	@GetMapping("/{idPizza}")
	public ResponseEntity<Pizza> find(@PathVariable int idPizza){
		Optional<Pizza> optPizza = this.pizzaService.getPizza(idPizza);
		
		if(optPizza.isPresent()) {
			return ResponseEntity.ok(optPizza.get()); 
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Pizza> create(@RequestBody Pizza pizza){
		return new ResponseEntity<Pizza>(this.pizzaService.create(pizza), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idPizza}")
	public ResponseEntity<Pizza> update(@PathVariable int idPizza, @RequestBody Pizza pizza){
		if(idPizza != pizza.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if(!this.pizzaService.exists(idPizza)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.pizzaService.save(pizza)); 
	}
	
	@DeleteMapping("/{idPizza}")
	public ResponseEntity<Pizza> delete(@PathVariable int idPizza){
		if(this.pizzaService.delete(idPizza)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
