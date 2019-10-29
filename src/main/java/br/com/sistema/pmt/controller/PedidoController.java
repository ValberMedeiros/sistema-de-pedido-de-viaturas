package br.com.sistema.pmt.controller;

import br.com.sistema.pmt.Service.UsuarioLogado;
import br.com.sistema.pmt.model.*;
import br.com.sistema.pmt.repository.MotoristaRepository;
import br.com.sistema.pmt.repository.PedidoRepository;
import br.com.sistema.pmt.repository.UsuarioRepository;
import br.com.sistema.pmt.repository.ViaturaRepository;
import br.com.sistema.pmt.repository.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Controllador dos pedidos do sistema
 */
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

	/***
	 *
	 * @param filtro filtro contendo a string de pesquisa
	 * @return ModelAndView contendo os pedidos feitos pelo militar
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/pedidos")
    public ModelAndView inicio(@ModelAttribute("filtro")Filter filtro){
        ModelAndView modelAndView = new ModelAndView("lista/pedidos");
        Iterable<Pedido> pedidos = pr.findAll();
        modelAndView.addObject("pedido", pedidos);
		String usuariosLogado = ul.getUsuarioLogado().toString();
		modelAndView.addObject("usuarioLogado", usuariosLogado);

        return modelAndView;
    }

	/**
	 * Metodo que redireciona para tela de cadastro de pedidos
	 * @return ModelAndView contendo um novo pedido
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/pedidos/cadastro")
	public ModelAndView cadastrarPedido() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropedido");
		modelAndView.addObject(new Pedido());
		return modelAndView;
	}


	/**
	 * Metodo que salva um pedido no banco de dados
	 * @param pedido pedido a ser salvo no banco
	 * @param errors lista de erros na validação do pedido a ser salvo
	 * @return ModelAndView com as mensagens de erro ou sucesso no cadastro do pedido
	 */
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

	/**
	 * Método que deleta um pedido pelo ID
	 * @param idpedido id do pedido a ser deletado
	 * @return ModelAndView redirecionando para pagina da listagens dos pedidos a serem aprovados
	 */
	@GetMapping("pedidos/deletar/{idpedido}")
	public ModelAndView DeletarPedido(@PathVariable("idpedido") Long idpedido){
		ModelAndView modelAndView = new ModelAndView("redirect:/pedidos/aprovar");
		Optional<Pedido> pedidoDetalhado = pr.findById(idpedido);
		pr.delete(pedidoDetalhado.get());

		return modelAndView;
	}

	/**
	 * Metodo que redireciona para pagina de pedidos a serem aprovados
	 * @param filtro filtro com os dados de pesquisa de pedidos
	 * @return ModelAndView redirecionando para pagina de pedidos a serem aprovados
	 */
    @GetMapping("pedidos/aprovar")
    public ModelAndView AprovarPedido(@ModelAttribute("filtro")Filter filtro){
        ModelAndView modelAndView = new ModelAndView("lista/pedidosaprovar");
		String pesquisa = filtro.getPesquisa() == null? "" : filtro.getPesquisa();
		List<Pedido> pedidos = pr.findByDestinoIgnoreCaseContainingOrderByDataDeEmbarqueDesc(pesquisa);
		modelAndView.addObject("pedido", pedidos);
        return modelAndView;
    }

	/**
	 * Método que mostra o pedido detalhado com todos os dados pelo ID
	 * @param idpedido Id do pedido a ser detalhado
	 * @return ModelAndView redirecionando para pagina contendo o pedido detalhado
	 */
	@GetMapping("pedidos/detalhado/{idpedido}")
	public ModelAndView DetalharPedido(@PathVariable("idpedido") Long idpedido){
		ModelAndView modelAndView = new ModelAndView("MestreDetail/DetalhePedidos");
		Optional<Pedido> pedidoDetalhado = pr.findById(idpedido);
		Motorista motoristaDoPedido = pedidoDetalhado.get().getMotoristaDoPedido();
		Viatura viaturaDoPedido = pedidoDetalhado.get().getViaturaDoPedido();
		modelAndView.addObject("pedido", pedidoDetalhado.get());
		modelAndView.addObject("motoristaDoPedido", motoristaDoPedido);
		modelAndView.addObject("viatura", viaturaDoPedido);

		return modelAndView;
	}

	/**
	 * Método que mostra o pedido detalhado a ser aprovado com todos os dados pelo ID
	 * @param idpedido Id do pedido detalhado a ser detalhado
	 * @return ModelAndView redirecionando para pagina contendo o pedido detalhado a ser aprovado
	 */
	@GetMapping("pedidos/detalhadoaprovar/{idpedido}")
	public ModelAndView DetalharPedidoAprovar(@PathVariable("idpedido") Long idpedido){
		ModelAndView modelAndView = new ModelAndView("MestreDetail/DetalhePedidosAprovar");
		Optional<Pedido> pedidoDetalhado = pr.findById(idpedido);
		modelAndView.addObject("pedido", pedidoDetalhado);

		return modelAndView;
	}

	/**
	 * Método que aprova o pedido pelo ID
	 * @param idpedido Id do pedido a ser aprovado
	 * @return ModelAndView redirecionando para pagina de pedidos a serem aprovados
	 */
    @GetMapping("pedidos/aprovar/{idpedido}")
    public ModelAndView AprovarPedido(@PathVariable("idpedido") Long idpedido){
        ModelAndView modelAndView = new ModelAndView("redirect:/pedidos/aprovar");
        Optional<Pedido> pedidoAprovado = pr.findById(idpedido);
        pedidoAprovado.get().setStatusPedido(StatusPedido.MOTORISTA);
        pr.save(pedidoAprovado.get());

        return modelAndView;
    }

	/**
	 * Método que mostra o pedido detalhado a ser rejeitado com todos os dados pelo ID
	 * @param idpedido id do pedido a ser rejeitado
	 * @return ModelAndView redirecionando para pagina contendo o pedido detalhado a ser rejeitado
	 */
	@GetMapping("pedidos/rejeitar/{idpedido}")
    public ModelAndView RejeitarPedido(@PathVariable("idpedido") Long idpedido){
        ModelAndView modelAndView = new ModelAndView("MestreDetail/DetalhePedidosRejeitar");
        Optional<Pedido> pedidoRejeitado = pr.findById(idpedido);
        modelAndView.addObject("pedido", pedidoRejeitado.get());
        return modelAndView;
    }

	/**
	 * Método que inclui justificativa da rejeição do pedido
	 * @param filtro
	 * @param pedido pedido a ter sua rejeição justificada
	 * @return ModelAndView redirecionando para pagina de pedidos
	 */
	@PostMapping("pedidos/justificarejeitar")
    public ModelAndView JustificarRejeicao(@ModelAttribute("filtro")Filter filtro, Pedido pedido){
	    ModelAndView modelAndView = new ModelAndView("lista/pedidos");
        pedido.setStatusPedido(StatusPedido.REJEITADO);
        pr.save(pedido);
        String pesquisa = filtro.getPesquisa() == null? "" : filtro.getPesquisa();
        Iterable<Pedido> pedidos = pr.findAll();
        modelAndView.addObject("pedido", pedidos);
        String usuariosLogado = ul.getUsuarioLogado().toString();
        modelAndView.addObject("usuarioLogado", usuariosLogado);
        return modelAndView;
    }

	@GetMapping("pedidos/incluirmotorista")
	public ModelAndView ListarInclusaoMotorista(@ModelAttribute("filtro")Filter filtro){
		ModelAndView modelAndView = new ModelAndView("lista/incluirmotorista");
		List<Pedido> pedidos = pr.findAllByStatusPedidoAprovadosOrderByDataDeEmbarqueDesc();
		modelAndView.addObject("pedido", pedidos);
		return modelAndView;
	}

	@GetMapping("pedidos/incluirmotorista/{idpedido}")
	public ModelAndView IncluirMotorista(@PathVariable("idpedido") Long idpedido){
		ModelAndView modelAndView = new ModelAndView("MestreDetail/IncluirMotorista");
		Optional<Pedido> pedidoDetalhado = pr.findById(idpedido);
		List<Motorista> motoristas = mr.findAllStatusMotoristaProntos();
		modelAndView.addObject("pedido", pedidoDetalhado.get());
		modelAndView.addObject("motoristas", motoristas);
		return modelAndView;
	}

	@PostMapping("pedidos/incluirmotorista")
	public ModelAndView SalvarIncluirMotorista(@ModelAttribute("filtro")Filter filtro, Pedido pedido){
		ModelAndView modelAndView = new ModelAndView("lista/incluirmotorista");
		List<Pedido> pedidos = (List<Pedido>) pr.findAll();
		modelAndView.addObject("pedido", pedidos);
		pedido.setStatusPedido(StatusPedido.VIATURA);
		pr.save(pedido);
		return modelAndView;
	}

	@GetMapping("pedidos/incluirviatura")
	public ModelAndView ListarInclusaoViatura(@ModelAttribute("filtro")Filter filtro){
		ModelAndView modelAndView = new ModelAndView("lista/incluirviatura");
		List<Pedido> pedidos = pr.findAllByStatusPedidoMotoristaOrderByDataDeEmbarqueDesc();
		modelAndView.addObject("pedido", pedidos);
		return modelAndView;
	}

	@GetMapping("pedidos/incluirviatura/{idpedido}")
	public ModelAndView IncluirViatura(@PathVariable("idpedido") Long idpedido){
		ModelAndView modelAndView = new ModelAndView("MestreDetail/IncluirViatura");
		Optional<Pedido> pedidoDetalhado = pr.findById(idpedido);
		List<Pedido> pedidos = (List<Pedido>) pr.findAll();
		List<Viatura> viaturas = vr.findViaturaProntas();
		List<Motorista> motoristas = mr.findAllStatusMotoristaProntos();
		modelAndView.addObject("pedido", pedidoDetalhado.get());
		modelAndView.addObject("viaturas", viaturas);
		modelAndView.addObject("motoristas", motoristas);

		return modelAndView;
	}

	@PostMapping("pedidos/incluirviatura")
	public ModelAndView SalvarIncluirViatura(@ModelAttribute("filtro")Filter filtro, Pedido pedido){
		ModelAndView modelAndView = new ModelAndView("lista/incluirviatura");
		List<Pedido> pedidos = (List<Pedido>) pr.findAll();
		modelAndView.addObject("pedido", pedidos);
		pedido.setStatusPedido(StatusPedido.PRONTO);
		pr.save(pedido);
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
