package br.com.sistema.pmt.controller;

import br.com.sistema.pmt.model.Motorista;
import br.com.sistema.pmt.model.PostoGraduacao;
import br.com.sistema.pmt.model.Roles;
import br.com.sistema.pmt.repository.MotoristaRepository;
import br.com.sistema.pmt.repository.PostoGraduacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class MotoristaController {

    @Autowired
    private MotoristaRepository mr;

    @Autowired
    private PostoGraduacaoRepository pr;

    @RequestMapping(method = RequestMethod.GET, value = "/motorista")
    public ModelAndView inicio(){
        ModelAndView modelAndView = new ModelAndView("lista/motorista");
        Iterable<Motorista> motoristaIt = mr.findAll();
        Iterable<Motorista> listMotoristas = mr.findAll();
        modelAndView.addObject("motoristas", listMotoristas);
        modelAndView.addObject("motoristaobj", motoristaIt);
        return modelAndView;
    }

    @GetMapping("/motorista/cadastro")
    public ModelAndView cadastroMotorista(){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastromotorista");
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        modelAndView.addObject("motoristaobj", new Motorista());
        modelAndView.addObject("postoGrads", listPostoGrad);

        return modelAndView;
    }

    @PostMapping("**/salvarMotorista")
    public ModelAndView salvarMotorista(Motorista motorista){
        ModelAndView modelAndView = new ModelAndView("lista/motorista");
        mr.save(motorista);
        Iterable<Motorista> listMotoristas = mr.findAll();
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        Iterable<Motorista> motoristaobj = mr.findAll();
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("motoristaobj", motoristaobj);
        modelAndView.addObject("motoristas", listMotoristas);
        return modelAndView;
    }

    @GetMapping("/motorista/editar/{idmotorista}")
    public ModelAndView editarUsuario(@PathVariable("idmotorista") Long idmotorista){
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastromotorista");
        Optional<Motorista> motoristaEditar = mr.findById(idmotorista);
        Iterable<PostoGraduacao> listPostoGrad = pr.findAll();
        Optional<PostoGraduacao> postoGraduacaoSelect = pr.findById(motoristaEditar.get().getPostoGraduacaoMotorista().getPostoGraduacao_id());
        modelAndView.addObject("postoGrads", listPostoGrad);
        modelAndView.addObject("postoSelecionado", postoGraduacaoSelect.get());
        modelAndView.addObject("motoristaobj", motoristaEditar);
        return modelAndView;
    }
}
