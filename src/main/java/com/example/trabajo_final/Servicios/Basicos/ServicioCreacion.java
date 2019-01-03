package com.example.trabajo_final.Servicios.Basicos;

import com.example.trabajo_final.Repositorio.ArticuloRepositorio;
import com.example.trabajo_final.Repositorio.FacturaRepositorio;
import com.example.trabajo_final.Repositorio.HistorialRepositorio;
import com.example.trabajo_final.Repositorio.UsuarioRepositorio;
import com.example.trabajo_final.Servicios.Complementarias.ServicioEncryt;
import com.example.trabajo_final.entidades.Articulo;
import freemarker.template.utility.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

@Service
public class ServicioCreacion {

    @Autowired
    private HistorialRepositorio historyRepository;
    @Autowired
    private ArticuloRepositorio productRepository;
    @Autowired
    private FacturaRepositorio receiptRepository;
    @Autowired
    private UsuarioRepositorio userRepository;
    @Autowired
    private ServicioEncryt encryptionService;

    //Productos
    public Articulo registerNewProduct(String nombre, String descripcion, String supplidor, float precio, Integer cantidad) throws Exception{

        if (precio <= 0.00f)
            throw new IllegalArgumentException("All price must be positive decimal numbers");

        if (precio < 0)
            throw new IllegalArgumentException("There must be at least one unit registered");

        try {
            return productRepository.save(new Articulo(nombre, descripcion, supplidor, precio, cantidad));
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (NullArgumentException exp){
            throw new NullArgumentException("Null Argument Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

    public Articulo registerNewProduct(Articulo p) throws Exception{

        if (p.getPrecio() <= 0.00f)
            throw new IllegalArgumentException("All price must be positive decimal numbers");

        if (p.getCantidad() < 0)
            throw new IllegalArgumentException("There must be at least one unit registered");

        try {
            return productRepository.save(p);
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (NullArgumentException exp){
            throw new NullArgumentException("Null Argument Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

}
