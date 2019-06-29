package br.com.sistema.pmt.controller;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.sistema.pmt.Service.UsuarioLogado;
import br.com.sistema.pmt.model.Pedido;
import br.com.sistema.pmt.repository.PedidoRepository;
import br.com.sistema.pmt.repository.UsuarioRepository;
import br.com.sistema.pmt.repository.filter.Filter;

@Controller
public class PedidoController {
	
	@Autowired
	PedidoRepository pr;

	@Autowired
	UsuarioRepository ur;

	@Autowired
	UsuarioLogado ul;
	
	@RequestMapping(method = RequestMethod.GET, value = "/pedidos")
    public ModelAndView inicio(@ModelAttribute("filtro")Filter filtro){
        ModelAndView modelAndView = new ModelAndView("lista/pedidos");
    	
        return modelAndView;
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/pedidos/cadastro")
	public ModelAndView cadastrarPedido() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropedido");
		modelAndView.addObject(new Pedido());
		return modelAndView;
	}
	
	@PostMapping("/pedidos/salvarPedido")
	public ModelAndView salvarPedido(@Validated Pedido pedido, Errors errors) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropedido");
		LocalDate dataDoPedidoNovo = LocalDate.now();
		pedido.setDataDoPedido(dataDoPedidoNovo);
		if(errors.hasErrors()) {
        	return modelAndView;
        }
		Period periodoEntreDataDoPedidoEDataDoEmbarque = Period.between(pedido.getDataDoPedido(), pedido.getDataDeEmbarque());
		pedido.setUsuarioRemetente(ur.findByusername(ul.getUsuarioLogado()));
		modelAndView.addObject("pedido", pedido);
		if(periodoEntreDataDoPedidoEDataDoEmbarque.toString().equals("P1D")){
			errors.reject("Os pedidos deverão ser feitos com 24 horas úteis de antecedencia à data de embarque", "Os pedidos deverão ser feitos com 24 horas úteis de antecedencia à data de embarque");
			return modelAndView;
		}else if(periodoEntreDataDoPedidoEDataDoEmbarque.toString().equals("P0D")){
			errors.reject("Os pedidos deverão ser feitos com 24 horas úteis de antecedencia à data de embarque", "Os pedidos deverão ser feitos com 24 horas úteis de antecedencia à data de embarque");
			return modelAndView;
		}else if(periodoEntreDataDoPedidoEDataDoEmbarque.toString().equals("P3D")){
			if(pedido.getDataDeEmbarque().getDayOfWeek().toString().equals("MONDAY") && pedido.getDataDoPedido().getDayOfWeek().toString().equals("FRIDAY")){
				errors.reject("Os pedidos deverão ser feitos com 24 horas úteis de antecedencia à data de embarque", "Os pedidos deverão ser feitos com 24 horas úteis de antecedencia à data de embarque");
				return modelAndView;
			}else{
				pr.save(pedido);
				modelAndView.addObject("mensagem", "Pedido salvo com sucesso!");
				return modelAndView;
			}
		}
		pr.save(pedido);
		modelAndView.addObject("mensagem", "Pedido salvo com sucesso!");
		return modelAndView;
	}
	
}
