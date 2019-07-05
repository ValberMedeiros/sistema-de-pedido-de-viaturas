package br.com.sistema.pmt.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.sistema.pmt.model.*;
import br.com.sistema.pmt.repository.MotoristaRepository;
import br.com.sistema.pmt.repository.ViaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.com.sistema.pmt.Service.UsuarioLogado;
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

	@Autowired
	MotoristaRepository mr;

	@Autowired
	ViaturaRepository vr;
	
	@RequestMapping(method = RequestMethod.GET, value = "/pedidos")
    public ModelAndView inicio(@ModelAttribute("filtro")Filter filtro){
        ModelAndView modelAndView = new ModelAndView("lista/pedidos");
        Iterable<Pedido> pedidos = pr.findAll();
        modelAndView.addObject("pedido", pedidos);
		String usuariosLogado = ul.getUsuarioLogado().toString();
		modelAndView.addObject("usuarioLogado", usuariosLogado);

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
				pedido.setStatusPedido(StatusPedido.APROVACAO);
				pr.save(pedido);
				modelAndView.addObject("mensagem", "Pedido salvo com sucesso!");
				return modelAndView;
			}
		}
		pedido.setStatusPedido(StatusPedido.APROVACAO);
		pr.save(pedido);
		modelAndView.addObject("mensagem", "Pedido salvo com sucesso!");
		return modelAndView;
	}

	@GetMapping("pedidos/deletar/{idpedido}")
	public ModelAndView DeletarPedido(@PathVariable("idpedido") Long idpedido){
		ModelAndView modelAndView = new ModelAndView("redirect:/pedidos/aprovar");
		Optional<Pedido> pedidoDetalhado = pr.findById(idpedido);
		pr.delete(pedidoDetalhado.get());

		return modelAndView;
	}

    @GetMapping("pedidos/aprovar")
    public ModelAndView AprovarPedido(@ModelAttribute("filtro")Filter filtro){
        ModelAndView modelAndView = new ModelAndView("lista/pedidosaprovar");
		String pesquisa = filtro.getPesquisa() == null? "" : filtro.getPesquisa();
		List<Pedido> pedidos = pr.findByDestinoIgnoreCaseContainingOrderById(pesquisa);
		modelAndView.addObject("pedido", pedidos);
        return modelAndView;
    }

	@GetMapping("pedidos/detalhadoaprovar/{idpedido}")
	public ModelAndView DetalharPedidoAprovar(@PathVariable("idpedido") Long idpedido){
		ModelAndView modelAndView = new ModelAndView("MestreDetail/DetalhePedidosAprovar");
		Optional<Pedido> pedidoDetalhado = pr.findById(idpedido);
		modelAndView.addObject("pedido", pedidoDetalhado);

		return modelAndView;
	}

    @GetMapping("pedidos/aprovar/{idpedido}")
    public ModelAndView AprovarPedido(@PathVariable("idpedido") Long idpedido){
        ModelAndView modelAndView = new ModelAndView("redirect:/pedidos/aprovar");
        Optional<Pedido> pedidoAprovado = pr.findById(idpedido);
        pedidoAprovado.get().setStatusPedido(StatusPedido.MOTORISTA);
        pr.save(pedidoAprovado.get());

        return modelAndView;
    }

    @GetMapping("pedidos/rejeitar/{idpedido}")
    public ModelAndView RejeitarPedido(@PathVariable("idpedido") Long idpedido){
        ModelAndView modelAndView = new ModelAndView("redirect:/pedidos/aprovar");
        Optional<Pedido> pedidoRejeitado = pr.findById(idpedido);
        pedidoRejeitado.get().setStatusPedido(StatusPedido.REJEITADO);
        pr.save(pedidoRejeitado.get());

        return modelAndView;
    }

	@GetMapping("pedidos/incluirmotorista")
	public ModelAndView ListarInclusaoMotorista(@ModelAttribute("filtro")Filter filtro){
		ModelAndView modelAndView = new ModelAndView("lista/incluirmotorista");
		List<Pedido> pedidos = pr.findAllByStatusPedidoAprovados();
		modelAndView.addObject("pedido", pedidos);
		return modelAndView;
	}

	@GetMapping("pedidos/incluirmotorista/{idpedido}")
	public ModelAndView IncluirMotorista(@PathVariable("idpedido") Long idpedido){
		ModelAndView modelAndView = new ModelAndView("MestreDetail/IncluirMotorista");
		Optional<Pedido> pedidoDetalhado = pr.findById(idpedido);
		List<Motorista> motoristas = mr.findAllStatusMotoristaProntos();
		modelAndView.addObject("pedido", pedidoDetalhado);
		modelAndView.addObject("motoristas", motoristas);
		return modelAndView;
	}

	@PostMapping("pedidos/incluirmotorista")
	public ModelAndView SalvarIncluirMotorista(@ModelAttribute("filtro")Filter filtro, Pedido pedido){
		ModelAndView modelAndView = new ModelAndView("lista/incluirmotorista");
		Optional<Pedido> pedidoDetalhado = pr.findById(pedido.getId());
		List<Pedido> pedidos = (List<Pedido>) pr.findAll();
		modelAndView.addObject("pedido", pedidos);
		pedidoDetalhado.get().setStatusPedido(StatusPedido.VIATURA);
		pr.save(pedidoDetalhado.get());
		return modelAndView;
	}

	@GetMapping("pedidos/incluirviatura")
	public ModelAndView ListarInclusaoViatura(@ModelAttribute("filtro")Filter filtro){
		ModelAndView modelAndView = new ModelAndView("lista/incluirviatura");
		List<Pedido> pedidos = pr.findAllByStatusPedidoMotorista();
		modelAndView.addObject("pedido", pedidos);
		return modelAndView;
	}

	@GetMapping("pedidos/incluirviatura/{idpedido}")
	public ModelAndView IncluirViatura(@PathVariable("idpedido") Long idpedido){
		ModelAndView modelAndView = new ModelAndView("MestreDetail/IncluirViatura");
		Optional<Pedido> pedidoDetalhado = pr.findById(idpedido);
		List<Pedido> pedidos = (List<Pedido>) pr.findAll();
		List<Viatura> viaturas = vr.findViaturaProntas();
		modelAndView.addObject("pedido", pedidoDetalhado);
		modelAndView.addObject("viaturas", viaturas);

		return modelAndView;
	}

	@PostMapping("pedidos/incluirviatura")
	public ModelAndView SalvarIncluirViatura(@ModelAttribute("filtro")Filter filtro, Pedido pedido){
		ModelAndView modelAndView = new ModelAndView("lista/incluirviatura");
		Optional<Pedido> pedidoDetalhado = pr.findById(pedido.getId());
		List<Pedido> pedidos = (List<Pedido>) pr.findAll();
		modelAndView.addObject("pedido", pedidos);
		pedidoDetalhado.get().setStatusPedido(StatusPedido.PRONTO);
		pr.save(pedidoDetalhado.get());
		return modelAndView;
	}
	
	@ModelAttribute("tiposDeViatura")
	public List<TipoViatura> TiposDeViatura(){
    	return Arrays.asList(TipoViatura.values());
    }

	@ModelAttribute("finalidades")
	public List<Finalidade> Finalidades(){
		return Arrays.asList(Finalidade.values());
	}

	@ModelAttribute("statusPedidos")
	public List<StatusPedido> StatusPedido(){
		return Arrays.asList(StatusPedido.values());
	}
	
	
}
