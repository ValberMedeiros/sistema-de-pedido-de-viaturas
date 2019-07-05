package br.com.sistema.pmt.repository;

import br.com.sistema.pmt.model.StatusPedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sistema.pmt.model.Pedido;

import java.util.List;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{
    List<Pedido> findByDestinoIgnoreCaseContainingOrderById(String pesquisa);

    @Query("select u from Pedido u where status_pedido =2")
    List<Pedido> findAllByStatusPedidoAprovados();

    @Query("select u from Pedido u where status_pedido =1")
    List<Pedido> findAllByStatusPedidoMotorista();
}
