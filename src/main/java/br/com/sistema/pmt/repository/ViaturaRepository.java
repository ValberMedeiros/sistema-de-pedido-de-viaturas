package br.com.sistema.pmt.repository;

import br.com.sistema.pmt.model.Viatura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ViaturaRepository extends CrudRepository<Viatura, Long>{
	
	List<Viatura> findByPlacaIgnoreCaseContainingOrderByPlaca(String pesquisa);

	@Query("select u from Viatura u where statusViatura =2")
	List<Viatura> findViaturaProntas();
	
}
