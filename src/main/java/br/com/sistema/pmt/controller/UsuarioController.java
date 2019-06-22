package br.com.sistema.pmt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.sistema.pmt.model.PostoGraduacao;
import br.com.sistema.pmt.model.Roles;
import br.com.sistema.pmt.model.Usuarios;
import br.com.sistema.pmt.repository.PostoGraduacaoRepository;
import br.com.sistema.pmt.repository.RolesRepository;
import br.com.sistema.pmt.repository.UsuarioRepository;
import br.com.sistema.pmt.repository.filter.UsuarioFilter;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository ur;

    @Autowired
    private RolesRepository rr;

    @Autowired
    private PostoGraduacaoRepository pr;

    @RequestMapping(method = RequestMethod.GET, value = "/usuarios")
    public ModelAndView inicio(@ModelAttribute("filtro")UsuarioFilter filtro){
        ModelAndView modelAndView = new ModelAndView("lista/usuario");
    	String pesquisa = filtro.getPesquisa() == null? "" : filtro.getPesquisa();
    	List<Usuarios> usuariosIt = ur.findByNomeCompletoContainingOrderByNomeCompleto(pesquisa);
    	modelAndView.addObject("usuarios", usuariosIt);
        return modelAndView;
    }

    @GetMapping("usuarios/cadastro")
    public ModelAndView cadastroUsuario(@ModelAttribute("filtro")UsuarioFilter filtro){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrousuario");
        Iterable<Roles> listRoles = rr.findAll();
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        modelAndView.addObject("usuariosobj", new Usuarios());
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("roles", listRoles);

        return modelAndView;
    }

    @PostMapping("usuarios/salvarUsuario")
    public ModelAndView salvarUsuario(@ModelAttribute("filtro")UsuarioFilter filtro, Usuarios usuarios){
        ModelAndView modelAndView = new ModelAndView("redirect:/usuarios");
        usuarios.setPassword(new BCryptPasswordEncoder().encode(usuarios.getPassword()));
        ur.save(usuarios);
        Iterable<Roles> listRoles = rr.findAll();
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        Iterable<Usuarios> usuariosIt = ur.findAll();
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("roles", listRoles);
        modelAndView.addObject("usuarios", usuariosIt);
        return modelAndView;
    }

    @GetMapping("usuarios/editar/{idusuario}")
    public ModelAndView editarUsuario(@ModelAttribute("filtro")UsuarioFilter filtro, @PathVariable("idusuario") Long idpessoa){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrousuario");
        Optional<Usuarios> usuarios = ur.findById(idpessoa);
        modelAndView.addObject("usuariosobj", usuarios.get());
        Iterable<Roles> listRoles = rr.findAll();
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        Optional<PostoGraduacao> postoGraduacaoSelect = pr.findById(usuarios.get().getPostoGraduacao().getPostoGraduacao_id());
        Optional<Roles> roleSelect = rr.findById(usuarios.get().getRoles().getRoles_id());
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("roles", listRoles);
        modelAndView.addObject("postoSelecionado", postoGraduacaoSelect.get());
        modelAndView.addObject("roleSelecionado", roleSelect.get());
        return modelAndView;
    }

    @GetMapping("usuarios/deletar/{idusuario}")
    public ModelAndView deletarUsuario(@ModelAttribute("filtro")UsuarioFilter filtro, @PathVariable("idusuario") Long idUsuario){
        ModelAndView modelAndView = new ModelAndView("redirect:/usuarios");
        Optional<Usuarios> usuario = ur.findById(idUsuario);
        ur.delete(usuario.get());
        Iterable<Usuarios> usuariosIt = ur.findAll();
        modelAndView.addObject("usuarios", usuariosIt);
        return modelAndView;
    }
    
    
}
