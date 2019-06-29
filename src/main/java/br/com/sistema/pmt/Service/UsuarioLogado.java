package br.com.sistema.pmt.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogado {
	
	public String getUsuarioLogado() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
		    String nome = ((UserDetails)principal).getUsername();
		    return nome;
		} else {
		    String nome = principal.toString();
		    return nome;
		}
	}	
}
