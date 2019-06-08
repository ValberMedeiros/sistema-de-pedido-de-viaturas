package br.com.sistema.pmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViaturaController {
    @RequestMapping(method = RequestMethod.GET, value = "/viaturas")
    public ModelAndView inicio(){
        ModelAndView modelAndView = new ModelAndView("lista/viaturas");
        return modelAndView;
    }
}
