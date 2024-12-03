package com.daw.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pedido;
import com.daw.persistence.repositories.PedidoRepository;
import com.daw.services.dtos.PedidoDTO;
import com.daw.services.mappers.PedidoMapper;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	//CRUDs
	public List<PedidoDTO> findAll(){
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();
		
		for(Pedido p : this.pedidoRepository.findAll()) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}

		return pedidosDTO;
	}
	
	public PedidoDTO findById(int idPedido){
		return PedidoMapper.toDto(this.pedidoRepository.findById(idPedido).get());
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
