package br.com.sistema.pmt.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.sistema.pmt.model.Motorista;
import br.com.sistema.pmt.model.PostoGraduacao;
import br.com.sistema.pmt.model.StatusMotorista;
import br.com.sistema.pmt.model.StatusViatura;
import br.com.sistema.pmt.model.Viatura;
import br.com.sistema.pmt.repository.MotoristaRepository;
import br.com.sistema.pmt.repository.PostoGraduacaoRepository;
import br.com.sistema.pmt.repository.filter.Filter;

@Controller
public class MotoristaController {

    @Autowired
    private MotoristaRepository mr;

    @Autowired
    private PostoGraduacaoRepository pr;

    @RequestMapping(method = RequestMethod.GET, value = "/motorista")
    public ModelAndView inicio(@ModelAttribute("filtro")Filter filtro){
        ModelAndView modelAndView = new ModelAndView("lista/motorista");
        String pesquisa = filtro.getPesquisa() == null? "" : filtro.getPesquisa();
        List<Motorista> motoristas = mr.findByNomeCompletoIgnoreCaseContainingOrderByNomeCompleto(pesquisa);
        Iterable<Motorista> motoristaIt = mr.findAll();
        modelAndView.addObject("motoristas", motoristas);
        modelAndView.addObject("motorista", motoristaIt);
        return modelAndView;
    }

    @GetMapping("motorista/cadastro")
    public ModelAndView cadastroMotorista(){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastromotorista");
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        modelAndView.addObject("motorista", new Motorista());
        modelAndView.addObject("postoGrads", listPostoGrad);

        return modelAndView;
    }

    @PostMapping("**/salvarMotorista")
    public ModelAndView salvarMotorista(@ModelAttribute("filtro")Filter filtro, @Validated Motorista motorista, Errors erros){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastromotorista");
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("motorista", motorista);
        if(erros.hasErrors()) {
        	return modelAndView;
        }
        mr.save(motorista);
        modelAndView.addObject("motorista", new Motorista());
        modelAndView.addObject("mensagem", "Motorista salvo com sucesso!");
        return modelAndView;
    }

    @GetMapping("motorista/editar/{idmotorista}")
    public ModelAndView editarMotorista(@PathVariable("idmotorista") Long idmotorista){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastromotorista");
        Optional<Motorista> motoristaEditar = mr.findById(idmotorista);
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        Optional<PostoGraduacao> postoGraduacaoSelect = pr.findById(motoristaEditar.get().getPostoGraduacaoMotorista().getPostoGraduacao_id());
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("postoSelecionado", postoGraduacaoSelect.get());
        modelAndView.addObject("motorista", motoristaEditar);
        return modelAndView;
    }
    
    @GetMapping("motorista/deletar/{idmotorista}")
    public ModelAndView deletarMotorista(@ModelAttribute("filtro")Filter filtro, @PathVariable("idmotorista") Long idMotorista){
        ModelAndView modelAndView = new ModelAndView("redirect:/motorista");
        Optional<Motorista> motorista = mr.findById(idMotorista);
        mr.delete(motorista.get());
        Iterable<Motorista> listMotoristas = mr.findAll();
        Iterable<Motorista> motoristaobj = mr.findAll();
        modelAndView.addObject("motoristaobj", motoristaobj);
        modelAndView.addObject("motoristas", listMotoristas);
        return modelAndView;
    }
    
    @ModelAttribute("todosStatusMotorista")
    public List<StatusMotorista> todosStatusMotorista(){
    	return Arrays.asList(StatusMotorista.values());
    }
}
