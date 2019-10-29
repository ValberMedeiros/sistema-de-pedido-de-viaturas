package br.com.sistema.pmt.controller;

import br.com.sistema.pmt.model.Combustivel;
import br.com.sistema.pmt.model.StatusViatura;
import br.com.sistema.pmt.model.Viatura;
import br.com.sistema.pmt.repository.ViaturaRepository;
import br.com.sistema.pmt.repository.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ViaturaController {
	
	@Autowired
	public ViaturaRepository vr;
	
	
    @RequestMapping(method = RequestMethod.GET, value = "/viaturas")
    public ModelAndView inicio(@ModelAttribute("filtro")Filter filtro){
        ModelAndView modelAndView = new ModelAndView("lista/viaturas");
        String pesquisa = filtro.getPesquisa() == null? "" : filtro.getPesquisa();
        List<Viatura> viaturas = vr.findByPlacaIgnoreCaseContainingOrderByPlaca(pesquisa);
        modelAndView.addObject("viaturas", viaturas);

        return modelAndView;
    }
    
    @GetMapping("viaturas/cadastro")
    public ModelAndView cadastroViatura(){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastroviatura");
        modelAndView.addObject("viatura", new Viatura());
        return modelAndView;
    }
    
    @PostMapping("viaturas/salvarViatura")
    public ModelAndView salvarViatura(@Validated Viatura viatura, Errors erros){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastroviatura");
        
        modelAndView.addObject("viatura", viatura);
        
        if(erros.hasErrors()) {
        	return modelAndView;
        }
        
        vr.save(viatura);
        modelAndView.addObject("mensagem", "Viatura salva com sucesso");
        modelAndView.addObject("viatura", new Viatura());
        return modelAndView;
    }
    
    @GetMapping("viaturas/editar/{idviatura}")
    public ModelAndView editarViatura(@PathVariable("idviatura") Long idviatura) {
    	ModelAndView modelAndView = new ModelAndView("cadastro/cadastroviatura");
    	Optional<Viatura> viaturaEdit = vr.findById(idviatura);
    	modelAndView.addObject("viatura", viaturaEdit);
    	return modelAndView;
    }
    
    @GetMapping("viaturas/deletar/{idviatura}")
    public ModelAndView deletarViatura(@PathVariable("idviatura") Long idviatura) {
    	ModelAndView modelAndView = new ModelAndView("redirect:/viaturas");
    	vr.deleteById(idviatura);
    	return modelAndView;
    }
    
    
    @ModelAttribute("todosCobustivel")
	public List<Combustivel> todosCobustivel(){
		return Arrays.asList(Combustivel.values());
	}
    
    @ModelAttribute("todosStatusViatura")
    public List<StatusViatura> todosStatusViatura(){
    	return Arrays.asList(StatusViatura.values());
    }
    
    @ModelAttribute("todasViaturas")
    public List<Viatura> todasViaturas(){
    	List<Viatura> todasViaturas = (List<Viatura>) vr.findAll();
    	return todasViaturas;
    }
}
