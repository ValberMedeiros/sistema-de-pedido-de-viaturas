package br.com.sistema.pmt.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistema.pmt.model.Usuarios;
import br.com.sistema.pmt.repository.UsuarioRepository;

@Service
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository ur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = ur.findByusername(username);

        if(usuarios == null){
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
        return new User(usuarios.getUsername(), usuarios.getPassword(), usuarios.isEnabled(),
        		true, true,
        		true, usuarios.getAuthorities());
    }
}
