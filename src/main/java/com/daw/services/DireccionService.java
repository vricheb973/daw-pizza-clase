package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Direccion;
import com.daw.persistence.repositories.DireccionRepository;

@Service
public class DireccionService {

	@Autowired
	private DireccionRepository direccionRepository;

	public List<Direccion> findAll() {
		return this.direccionRepository.findAll();
	}

	public boolean existsDireccion(int idDireccion) {
		return this.direccionRepository.existsById(idDireccion);
	}

	public Optional<Direccion> findById(int idDireccion) {
		return this.direccionRepository.findById(idDireccion);
	}

	public Direccion create(Direccion direccion) {
		marcarInactivas(direccion.getIdCliente());
		
		direccion.setActiva(true);

		return this.direccionRepository.save(direccion);
	}

	public Direccion save(Direccion direccion) {
		return this.direccionRepository.save(direccion);
	}

	public boolean delete(int idDireccion) {
		boolean result = false;

		if (this.direccionRepository.existsById(idDireccion)) {
			this.direccionRepository.deleteById(idDireccion);
			result = true;
		}

		return result;
	}

	public void marcarInactivas(int idCliente) {
		List<Direccion> direcciones = this.direccionRepository.findByIdCliente(idCliente);

		for (Direccion dir : direcciones) {
			dir.setActiva(false);
			this.direccionRepository.save(dir);
		}
	}

	public Direccion marcarDesmarcarActivas(int idDireccion) {
		Direccion direccion = this.direccionRepository.findById(idDireccion).get();
		
		if(direccion.getActiva()) {
			direccion.setActiva(false);
		}
		else {
			marcarInactivas(direccion.getIdCliente());
			direccion.setActiva(true);
		}
		
		return this.direccionRepository.save(direccion);
	}
	
}
