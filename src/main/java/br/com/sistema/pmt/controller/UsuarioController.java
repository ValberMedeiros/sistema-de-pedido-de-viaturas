package br.com.sistema.pmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsuarioController {

    @RequestMapping(method = RequestMethod.GET, value = "/usuarios")
    public String inicio(){
        return "cadastro/usuario";
    }

}
