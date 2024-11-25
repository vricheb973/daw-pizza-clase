package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Cliente;
import com.daw.persistence.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	//CRUDs
	public List<Cliente> findAll(){
		return this.clienteRepository.findAll();
	}
	
	public boolean existsCliente(int idCliente){
		return this.clienteRepository.existsById(idCliente);
	}
	
	public Optional<Cliente> findById(int idCliente){
		return this.clienteRepository.findById(idCliente);
	}
	
	public Cliente create(Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}
	
	public Cliente save(Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}
	
	public boolean delete(int idCliente) {
		boolean result = false;
		
		if(this.clienteRepository.existsById(idCliente)) {
			this.clienteRepository.deleteById(idCliente);
			result = true;
		}
		
		return result;
	}
	
	public List<Cliente> getByTelefono(String telefono) {
		return this.clienteRepository.findByTelefonoContaining(telefono);
	}

}
