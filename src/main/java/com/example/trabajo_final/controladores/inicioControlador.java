package com.example.trabajo_final.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class inicioControlador {

    @RequestMapping(value = "/", method=RequestMethod.GET)
    public String index(Model model, HttpSession session){

        model.addAttribute("title","Web Final- Inicio");
        return "inicio"; //TODO: uso de los cambios
    }

    @RequestMapping(value = "/prueba", method=RequestMethod.GET)
    public String prueba(Model model, HttpSession session){

        model.addAttribute("title","clientes");
        return "clientes"; //TODO: uso de los cambios
    }
}
