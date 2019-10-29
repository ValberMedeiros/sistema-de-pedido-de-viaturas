package br.com.sistema.pmt.sistemadepedidodeviaturas;

import br.com.sistema.pmt.Service.UsuarioLogado;
import br.com.sistema.pmt.model.Usuarios;
import br.com.sistema.pmt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@Autowired
	private UsuarioLogado ul;
	
	@Autowired
	private UsuarioRepository ur;
	
    @RequestMapping("/")
    public ModelAndView index(){
    	ModelAndView modelAndView = new ModelAndView("index");
    	String nomeUsuarioLogado = ul.getUsuarioLogado();
    	Usuarios usuarioLogado = ur.findByusername(nomeUsuarioLogado);
    	modelAndView.addObject("nomeusuario", usuarioLogado);
        return modelAndView;
    }
   
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
