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
import com.daw.services.ClienteService;
import com.daw.services.DireccionService;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {
	
	@Autowired
	private DireccionService direccionService;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Direccion>> list(){
		return ResponseEntity.ok(this.direccionService.findAll());
	}
	
	@GetMapping("/{idDireccion}")
	public ResponseEntity<Direccion> findById(@PathVariable int idDireccion) {
		Optional<Direccion> direccion = this.direccionService.findById(idDireccion);
		if(direccion.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(direccion.get());
	}
	
	@PostMapping
	public ResponseEntity<Direccion> create(@RequestBody Direccion direccion){
		if(!this.clienteService.existsCliente(direccion.getIdCliente())) {
			return ResponseEntity.notFound().build();
		}
		
		return new ResponseEntity<Direccion>(this.direccionService.create(direccion), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idDireccion}")
	public ResponseEntity<Direccion> update(@PathVariable int idDireccion, @RequestBody Direccion direccion){
		if(idDireccion != direccion.getId()) {
			return ResponseEntity.badRequest().build();
		}
		else if(!this.direccionService.existsDireccion(idDireccion)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.direccionService.save(direccion));
	}
	
	@DeleteMapping("/{idDireccion}")
	public ResponseEntity<Direccion> delete(@PathVariable int idDireccion){
		if(this.direccionService.delete(idDireccion)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{idDireccion}/activa")
	public ResponseEntity<Direccion> marcarDesmarcarActiva(@PathVariable int idDireccion){
		if(this.direccionService.existsDireccion(idDireccion)) {
			return ResponseEntity.ok(this.direccionService.marcarDesmarcarActivas(idDireccion));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	
	

}
