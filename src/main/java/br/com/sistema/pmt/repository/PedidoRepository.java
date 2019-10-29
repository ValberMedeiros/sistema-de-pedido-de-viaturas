package br.com.sistema.pmt.repository;

import br.com.sistema.pmt.model.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{
    List<Pedido> findByDestinoIgnoreCaseContainingOrderByDataDeEmbarqueDesc(String pesquisa);

    @Query("select u from Pedido u where status_pedido =2")
    List<Pedido> findAllByStatusPedidoAprovadosOrderByDataDeEmbarqueDesc();

    @Query("select u from Pedido u where status_pedido =1")
    List<Pedido> findAllByStatusPedidoMotoristaOrderByDataDeEmbarqueDesc();
}
