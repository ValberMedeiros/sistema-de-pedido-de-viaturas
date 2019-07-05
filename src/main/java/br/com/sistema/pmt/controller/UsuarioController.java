package br.com.sistema.pmt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.sistema.pmt.Service.UsuarioLogado;
import br.com.sistema.pmt.model.PostoGraduacao;
import br.com.sistema.pmt.model.Role;
import br.com.sistema.pmt.model.Usuarios;
import br.com.sistema.pmt.repository.PostoGraduacaoRepository;
import br.com.sistema.pmt.repository.RolesRepository;
import br.com.sistema.pmt.repository.UsuarioRepository;
import br.com.sistema.pmt.repository.filter.Filter;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository ur;
    
    @Autowired
    private RolesRepository rr;
    
    @Autowired
    private PostoGraduacaoRepository pr;

    @Autowired
    private UsuarioLogado ul;

    @RequestMapping(method = RequestMethod.GET, value = "/usuarios")
    public ModelAndView inicio(@ModelAttribute("filtro")Filter filtro){
        ModelAndView modelAndView = new ModelAndView("lista/usuario");
    	String pesquisa = filtro.getPesquisa() == null? "" : filtro.getPesquisa();
    	List<Usuarios> usuariosIt = ur.findByNomeCompletoIgnoreCaseContainingOrderByNomeCompleto(pesquisa);
    	modelAndView.addObject("usuarios", usuariosIt);    	
        return modelAndView;
    }

    @GetMapping("usuarios/cadastro")
    public ModelAndView cadastroUsuario(){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrousuario");
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        Iterable<Role> listRoles = rr.findAll();
        Usuarios usuarios = new Usuarios();
        modelAndView.addObject("usuarios", usuarios);
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("roles", listRoles);

        return modelAndView;
    }

    @PostMapping("usuarios/salvarUsuario")
    public ModelAndView salvarUsuario(@ModelAttribute("filtro")Filter filtro, @Validated Usuarios usuarios, Errors erros){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrousuario");
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        Iterable<Role> listRoles = rr.findAll();
        usuarios.setPassword(new BCryptPasswordEncoder().encode(usuarios.getPassword()));
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("usuarios", usuarios);
        modelAndView.addObject("roles", listRoles);
        
        if(erros.hasErrors()) {
        	return modelAndView;
        }
        ur.save(usuarios);
        modelAndView.addObject("mensagem", "Usuário salvo com sucesso");
        modelAndView.addObject( new Usuarios());
        return modelAndView;
    }

    @GetMapping("usuarios/editar/{idusuario}")
    public ModelAndView editarUsuario(@ModelAttribute("filtro")Filter filtro, @PathVariable("idusuario") Long idpessoa, Errors erros){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrousuario");
        Optional<Usuarios> usuarios = ur.findById(idpessoa);
        Iterable<Role> listRoles = rr.findAll();
        modelAndView.addObject("usuarios", usuarios.get());
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        Optional<PostoGraduacao> postoGraduacaoSelect = pr.findById(usuarios.get().getPostoGraduacao().getPostoGraduacao_id());
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("roles", listRoles);
        modelAndView.addObject("postoSelecionado", postoGraduacaoSelect.get());
        return modelAndView;
    }

    @GetMapping("usuarios/deletar/{idusuario}")
    public ModelAndView deletarUsuario(@ModelAttribute("filtro")Filter filtro, @PathVariable("idusuario") Long idUsuario){
        ModelAndView modelAndView = new ModelAndView("redirect:/usuarios");
        Optional<Usuarios> usuario = ur.findById(idUsuario);
        ur.delete(usuario.get());
        Iterable<Usuarios> usuariosIt = ur.findAll();
        modelAndView.addObject("usuarios", usuariosIt);
        return modelAndView;
    }

    @GetMapping("usuarios/alterarsenha")
    public ModelAndView telaAlterarSenha(String senhaAtual, String novaSenha){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastronovasenha");
        return modelAndView;
    }

    @PostMapping("usuarios/alterarsenha")
    public ModelAndView alterarSenha(String senhaAtual, String novaSenha){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastronovasenha");
        Usuarios usuario = ur.findByusername(ul.getUsuarioLogado().toString());

        if(new BCryptPasswordEncoder().matches(senhaAtual, usuario.getPassword())){
            usuario.setPassword(new BCryptPasswordEncoder().encode(novaSenha));
            ur.save(usuario);
            modelAndView.addObject("mensagem", "Senha alterada com sucesso");
            return modelAndView;
        }else{
            modelAndView.addObject("mensagem", "Senha atual incorreta");
            return modelAndView;
        }
    }
}
