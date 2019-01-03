package com.example.trabajo_final.Controlador;

import com.example.trabajo_final.Servicios.Basicos.ServicioLectura;
import com.example.trabajo_final.entidades.Factura;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AplicacionControlador {
    ServicioLectura RDS;

    @RequestMapping(value ="/greeting", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Factura> greeting(@RequestParam(value="name", defaultValue="World") String name) {

        List<Factura> test= RDS.findAllRegisteredTransactions();
        if (RDS.findAllRegisteredTransactions() == null)
        {
            test= new ArrayList<>();
            return test;
        }



        return test;

    }

}
