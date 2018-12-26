package com.example.trabajo_final.controladores;

import com.example.trabajo_final.servicios.usuarioServicio;
import com.example.trabajo_final.entidades.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@RestController
@RequestMapping("/registro")
public class registroController {

    @Autowired
    private usuarioServicio uService;

    @RequestMapping(value="/registro", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        usuario user = new usuario();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registro");
        return modelAndView;
    }

    @RequestMapping(value = "/registro", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid usuario user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        usuario userExists = uService.finUserByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "Ya existe un usuario con ese nick");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registro");
        } else {
            uService.SaveU(user);
            modelAndView.addObject("successMessage", "Se ha registrado correctamente");
            modelAndView.addObject("user", new usuario());
            modelAndView.setViewName("registro");

        }
        return modelAndView;
    }

}
