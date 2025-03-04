package com.daw.web.controllers;

import java.util.List;

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

import com.daw.persistence.entities.Pedido;
import com.daw.services.ClienteService;
import com.daw.services.PedidoService;
import com.daw.services.PizzaPedidoService;
import com.daw.services.PizzaService;
import com.daw.services.dtos.PedidoDTO;
import com.daw.services.dtos.PizzaPedidoInputDTO;
import com.daw.services.dtos.PizzaPedidoOutputDTO;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PizzaPedidoService pizzaPedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PizzaService pizzaService;

	//CRUDs de Pedido
	@GetMapping
	public ResponseEntity<List<PedidoDTO>> list(){
		return ResponseEntity.ok(this.pedidoService.findAll());
	}
	
	@GetMapping("/{idPedido}")
	public ResponseEntity<PedidoDTO> findById(@PathVariable int idPedido) {		
		if(this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.ok(this.pedidoService.findById(idPedido));
		}

		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<PedidoDTO> create(@RequestBody Pedido pedido){
		if(!this.clienteService.existsCliente(pedido.getIdCliente())) {
			return ResponseEntity.notFound().build();
		}
		
		return new ResponseEntity<PedidoDTO>(this.pedidoService.create(pedido), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idPedido}")
	public ResponseEntity<PedidoDTO> update(@PathVariable int idPedido, @RequestBody Pedido pedido){
		if(idPedido != pedido.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if(!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.pedidoService.update(pedido));
	}
	
	@DeleteMapping("/{idPedido}")
	public ResponseEntity<PedidoDTO> delete(@PathVariable int idPedido){
		if(this.pedidoService.delete(idPedido)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
	
	//CRUDs de PizzaPedido
	@GetMapping("/{idPedido}/pizzas")
	public ResponseEntity<List<PizzaPedidoOutputDTO>> listPizzas(@PathVariable int idPedido) {
		return ResponseEntity.ok(this.pizzaPedidoService.findByIdPedido(idPedido));
	}
	
	@GetMapping("/{idPedido}/pizzas/{idPizzaPedido}")
	public ResponseEntity<PizzaPedidoOutputDTO> findByIdPizza(@PathVariable int idPedido, @PathVariable int idPizzaPedido){
		if(!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if(!this.pizzaPedidoService.existsPizzaPedido(idPizzaPedido)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.pizzaPedidoService.findDTO(idPizzaPedido));
	}
	
	@PostMapping("/{idPedido}/pizzas")
	public ResponseEntity<PizzaPedidoOutputDTO> addPizza(@PathVariable int idPedido, @RequestBody PizzaPedidoInputDTO dto){
		if(!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if(!this.pizzaService.existsPizza(dto.getIdPizza())) {
			return ResponseEntity.notFound().build();
		}
		
		return new ResponseEntity<PizzaPedidoOutputDTO>(this.pedidoService.addModPizza(dto), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idPedido}/pizzas/{idPizzaPedido}")
	public ResponseEntity<PizzaPedidoOutputDTO> modPizza(@PathVariable int idPedido, @PathVariable int idPizzaPedido, @RequestBody PizzaPedidoInputDTO dto){
		if(!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if(!this.pizzaPedidoService.existsPizzaPedido(idPizzaPedido)) {
			return ResponseEntity.notFound().build();
		}
		if(!this.pizzaService.existsPizza(dto.getIdPizza())) {
			return ResponseEntity.notFound().build();
		}
		if(idPizzaPedido != dto.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if(idPedido != dto.getIdPedido()) {
			return ResponseEntity.badRequest().build();
		}		
		
		return ResponseEntity.ok(this.pedidoService.addModPizza(dto));
	}
	
	@DeleteMapping("/{idPedido}/pizzas/{idPizzaPedido}")
	public ResponseEntity<PizzaPedidoOutputDTO> deletePizza(@PathVariable int idPedido, @PathVariable int idPizzaPedido) {
		if(!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if(this.pedidoService.deletePizza(idPizzaPedido)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
	
	//Query methods
	@GetMapping("/domicilio")
	public ResponseEntity<List<PedidoDTO>> listDomicilio(){
		return ResponseEntity.ok(this.pedidoService.getPedidosDomicilio());
	}

	@GetMapping("/llevar")
	public ResponseEntity<List<PedidoDTO>> listLlevar(){
		return ResponseEntity.ok(this.pedidoService.getPedidosLlevar());
	}
	
	@GetMapping("/local")
	public ResponseEntity<List<PedidoDTO>> listLocal(){
		return ResponseEntity.ok(this.pedidoService.getPedidosLocal());
	}
	
	@GetMapping("/hoy")
	public ResponseEntity<List<PedidoDTO>> listHoy(){
		return ResponseEntity.ok(this.pedidoService.getPedidosHoy());
	}
	
	@GetMapping("/cliente/{idCliente}")
	public ResponseEntity<List<PedidoDTO>> listByCliente(@PathVariable int idCliente){
		return ResponseEntity.ok(this.pedidoService.getByCliente(idCliente));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
