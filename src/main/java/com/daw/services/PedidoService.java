package com.daw.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pedido;
import com.daw.persistence.entities.PizzaPedido;
import com.daw.persistence.repositories.PedidoRepository;
import com.daw.services.dtos.PedidoDTO;
import com.daw.services.dtos.PizzaPedidoInputDTO;
import com.daw.services.dtos.PizzaPedidoOutputDTO;
import com.daw.services.mappers.PedidoMapper;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PizzaPedidoService pizzaPedidoService;

	// CRUDs
	public List<PedidoDTO> findAll() {
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();

		for (Pedido p : this.pedidoRepository.findAll()) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}

		return pedidosDTO;
	}

	public PedidoDTO findById(int idPedido) {
		return PedidoMapper.toDto(this.pedidoRepository.findById(idPedido).get());
	}

	public Optional<Pedido> findByIdEntity(int idPedido) {
		return this.pedidoRepository.findById(idPedido);
	}

	public boolean existsPedido(int idPedido) {
		return this.pedidoRepository.existsById(idPedido);
	}

	public PedidoDTO create(Pedido pedido) {
		pedido.setFecha(LocalDateTime.now());
		pedido.setTotal(0.0);

		pedido = this.pedidoRepository.save(pedido);

		pedido.setCliente(this.clienteService.findEntityById(pedido.getIdCliente()).get());
		pedido.setPizzaPedidos(new ArrayList<PizzaPedido>());

		return PedidoMapper.toDto(pedido);
	}

	// Lo mismo prácticamente
	public PedidoDTO update(Pedido pedido) {
		pedido = this.pedidoRepository.save(pedido);

		pedido.setCliente(this.clienteService.findEntityById(pedido.getIdCliente()).get());
		pedido.setPizzaPedidos(new ArrayList<PizzaPedido>());

		return PedidoMapper.toDto(pedido);
	}

	public boolean delete(int idPedido) {
		boolean result = false;

		if (this.pedidoRepository.existsById(idPedido)) {
			this.pedidoRepository.deleteById(idPedido);
			result = true;
		}

		return result;
	}

	public PizzaPedidoOutputDTO addModPizza(PizzaPedidoInputDTO inputDTO) {
		PizzaPedidoOutputDTO outDTO = this.pizzaPedidoService.save(inputDTO);

		this.actualizarTotal(inputDTO.getIdPedido());

		return outDTO;
	}

	// DELETE PIZZA
	// Método intermedio que llame al pizzaPedidoService y lo borre
	// Actualizamos precio
	// Devolvemos un true si se ha borrado

	public boolean deletePizza(int idPizzaPedido) {
		boolean result = this.pizzaPedidoService.delete(idPizzaPedido);

		if (result) {
			this.actualizarTotal(idPizzaPedido);
		}

		return result;
	}

	public void actualizarTotal(int idPedido) {
		Pedido pedido = this.pedidoRepository.findById(idPedido).get();
		Double total = 0.0;

		for (PizzaPedido pp : pedido.getPizzaPedidos()) {
			total += pp.getPrecio();
		}

		pedido.setTotal(total);

		this.pedidoRepository.save(pedido);
	}
	
	//Query methods
	public List<PedidoDTO> getPedidosDomicilio() {
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();

		for (Pedido p : this.pedidoRepository.findByMetodo("D")) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}

		return pedidosDTO;
	}
	
	public List<PedidoDTO> getPedidosLocal() {
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();

		for (Pedido p : this.pedidoRepository.findByMetodo("L")) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}

		return pedidosDTO;
	}
	
	public List<PedidoDTO> getPedidosLlevar() {
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();

		for (Pedido p : this.pedidoRepository.findByMetodo("R")) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}

		return pedidosDTO;
	}
	
	public List<PedidoDTO> getPedidosHoy() {
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();
		
		LocalDateTime inicio = LocalDateTime.of(LocalDate.now(), LocalTime.NOON);
		LocalDateTime fin = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(01, 00));

		for (Pedido p : this.pedidoRepository.findByFechaBetween(inicio, fin)) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}

		return pedidosDTO;
	}
	
	public List<PedidoDTO> getByCliente(int idCliente) {
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();

		for (Pedido p : this.pedidoRepository.findByIdCliente(idCliente)) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}

		return pedidosDTO;
	}

}
