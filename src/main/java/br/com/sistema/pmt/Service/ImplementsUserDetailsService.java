package br.com.sistema.pmt.Service;

import br.com.sistema.pmt.model.Usuarios;
import br.com.sistema.pmt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ImplementsUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository ur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = ur.findByusername(username);

        if(usuarios == null){
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
        return usuarios;
    }
}
