package com.daw.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pedido;
import com.daw.persistence.repositories.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	//CRUDs
	public List<Pedido> findAll(){
		return this.pedidoRepository.findAll();
	}
	
	public Optional<Pedido> findById(int idPedido){
		return this.pedidoRepository.findById(idPedido);
	}
	
	public boolean existsPedido(int idPedido){
		return this.pedidoRepository.existsById(idPedido);
	}
	
	public Pedido create(Pedido pedido) {
		pedido.setFecha(LocalDateTime.now());
		pedido.setTotal(0.0);
		
		return this.pedidoRepository.save(pedido);
	}

	public Pedido update(Pedido pedido) {		
		return this.pedidoRepository.save(pedido);
	}
	
	public boolean delete(int idPedido) {
		boolean result = false;
		
		if(this.pedidoRepository.existsById(idPedido)) {
			this.pedidoRepository.deleteById(idPedido);
			result = true;
		}
		
		return result;
	}
	
}
