package com.example.trabajo_final;

import com.example.trabajo_final.entidades.clientes;
import com.example.trabajo_final.repositorios.clientesRepositorio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class TrabajoFinalApplication {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(TrabajoFinalApplication.class, args);

        clientesRepositorio clientRep = (clientesRepositorio) applicationContext.getBean("clientesRepositorio");

        /*Long l=123L;
        Date today = Calendar.getInstance().getTime();
        clientes c=new clientes();
        c.setBirth_date(today);
        c.setCedula(l);
        c.setGenero("Masculino");
        c.setNombre("Pedro");
        c.setDireccion_envio("Santiago");
        clientRep.save(c);*/

        List<clientes> client= clientRep.findAll();
        System.out.println(" ----CLIENTES---");
        for (clientes cc:client){
            System.out.println("NOMBRE:"+cc.getNombre()+" ID:"+cc.getId()+" NACIMIENTO: "+ cc.getBirth_date()+" CEDULA:"+ cc.getCedula()+" LUGAR: "+cc.getDireccion_envio());
        }
    }
}
