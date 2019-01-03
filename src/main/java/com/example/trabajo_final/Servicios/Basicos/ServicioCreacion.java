package com.example.trabajo_final.Servicios.Basicos;

import com.example.trabajo_final.Repositorio.ArticuloRepositorio;
import com.example.trabajo_final.Repositorio.FacturaRepositorio;
import com.example.trabajo_final.Repositorio.HistorialRepositorio;
import com.example.trabajo_final.Repositorio.UsuarioRepositorio;
import com.example.trabajo_final.Servicios.Complementarias.ServicioEncryt;
import com.example.trabajo_final.Tools.Enum.Rol;
import com.example.trabajo_final.entidades.Articulo;
import com.example.trabajo_final.entidades.Factura;
import com.example.trabajo_final.entidades.Historial;
import com.example.trabajo_final.entidades.Usuario;
import freemarker.template.utility.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.ArrayList;

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

    //Facturas
    public Factura registerTransaction(String email, ArrayList<Integer> monto, ArrayList<Integer> articulosL, Float precio_total) throws Exception {

        if (!isEmailAddressTaken(email))
            throw new IllegalArgumentException("This user account does not exist");

        if (articulosL.isEmpty())
            throw new IllegalArgumentException("There needs to be purchased items to realize a transaction");

        if (precio_total < 0.00f)
            throw new IllegalArgumentException("Nothing is free in life");

        if (articulosL.size() != monto.size())
            throw new IllegalStateException("An error occurred while registering items; productList size is no equal to amount size");

        try {
            return receiptRepository.save(new Factura(userRepository.findByEmail(email), monto, articulosL, precio_total));
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (NullArgumentException exp){
            throw new NullArgumentException("Null Argument Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

    //Uusuarios

    public Usuario registerNewUser(String email, String nombre, String apellido, String direccion_envio, String pais, String ciudad, String password, Rol role) throws Exception{

        if (isEmailAddressTaken(email))
            throw new IllegalArgumentException("This user Account already exist");

        try {
            Usuario user = userRepository.save(new Usuario(email, nombre, apellido,  direccion_envio, pais, ciudad,encryptionService.encryptPassword(password), role));
            historyRepository.save(new Historial(user)); // Creating the users history
            return user;
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (NullArgumentException exp){
            throw new NullArgumentException("Null Argument Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

    private boolean isEmailAddressTaken(String email){
        Usuario user = userRepository.findByEmail(email);
        return (user != null);
    }

}
