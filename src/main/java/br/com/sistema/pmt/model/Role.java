package br.com.sistema.pmt.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nomeRole;

    public String getNomeRole() {
    	return nomeRole;
    }
    
    public void setNomeRole(String nomeRole) {
    	this.nomeRole = nomeRole;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getAuthority() {
		return this.nomeRole;
	}

}
