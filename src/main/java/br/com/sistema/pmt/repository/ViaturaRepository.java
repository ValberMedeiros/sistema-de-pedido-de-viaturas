package br.com.sistema.pmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sistema.pmt.model.Motorista;
import br.com.sistema.pmt.model.Viatura;

public interface ViaturaRepository extends CrudRepository<Viatura, Long>{
	
	List<Viatura> findByPlacaIgnoreCaseContainingOrderByPlaca(String pesquisa);

	@Query("select u from Viatura u where statusViatura =2")
	List<Viatura> findViaturaProntas();
	
}
