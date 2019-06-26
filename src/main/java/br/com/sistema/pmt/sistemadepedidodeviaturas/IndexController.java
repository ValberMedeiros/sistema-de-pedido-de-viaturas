package br.com.sistema.pmt.sistemadepedidodeviaturas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
   
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
