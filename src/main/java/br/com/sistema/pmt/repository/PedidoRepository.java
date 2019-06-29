package br.com.sistema.pmt.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.sistema.pmt.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{

}
