package br.com.sistema.pmt.repository;

import br.com.sistema.pmt.model.Motorista;
import br.com.sistema.pmt.model.Usuarios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MotoristaRepository extends CrudRepository<Motorista, Long> {
	List<Motorista> findByNomeCompletoIgnoreCaseContainingOrderByNomeCompleto(String pesquisa);
}
