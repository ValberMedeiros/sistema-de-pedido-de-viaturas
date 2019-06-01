package br.com.sistema.pmt.controller;

import br.com.sistema.pmt.model.Usuarios;
import br.com.sistema.pmt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository ur;

    @RequestMapping(method = RequestMethod.GET, value = "/usuarios")
    public ModelAndView inicio(){
        ModelAndView modelAndView = new ModelAndView("lista/usuario");
        Iterable<Usuarios> usuariosIt = ur.findAll();
        modelAndView.addObject("usuarios", usuariosIt);
        return modelAndView;
    }

    @GetMapping("/usuarios/cadastro")
    public ModelAndView cadastroUsuario(){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrousuario");
        modelAndView.addObject("usuariosobj", new Usuarios());
        return modelAndView;
    }

    @PostMapping("**/salvarUsuario")
    public ModelAndView salvarUsuario(Usuarios usuarios){
        ModelAndView modelAndView = new ModelAndView("lista/usuario");
        usuarios.setPassword(new BCryptPasswordEncoder().encode(usuarios.getPassword()));
        ur.save(usuarios);
        Iterable<Usuarios> usuariosIt = ur.findAll();
        modelAndView.addObject("usuarios", usuariosIt);
        return modelAndView;
    }

    @GetMapping("/usuarios/editar/{idusuario}")
    public ModelAndView editarUsuario(@PathVariable("idusuario") Long idpessoa){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrousuario");
        Optional<Usuarios> usuarios = ur.findById(idpessoa);
        modelAndView.addObject("usuariosobj", usuarios.get());
        return modelAndView;
    }

    @GetMapping("usuarios/deletar/{idusuario}")
    public ModelAndView deletarUsuario(@PathVariable("idusuario") Long idUsuario){
        ModelAndView modelAndView = new ModelAndView("lista/usuario");
        Optional<Usuarios> usuario = ur.findById(idUsuario);
        ur.delete(usuario.get());
        Iterable<Usuarios> usuariosIt = ur.findAll();
        modelAndView.addObject("usuarios", usuariosIt);
        return modelAndView;
    }
}
