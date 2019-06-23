package br.com.sistema.pmt.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.pmt.model.Usuarios;

@Repository
public interface UsuarioRepository  extends CrudRepository<Usuarios, Long> {
    Usuarios findByusername(String username);
    
    List<Usuarios> findByNomeCompletoIgnoreCaseContainingOrderByNomeCompleto(String pesquisa);
}
