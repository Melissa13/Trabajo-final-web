package com.example.trabajo_final.Servicios.Basicos;

import com.example.trabajo_final.Repositorio.ArticuloRepositorio;
import com.example.trabajo_final.Repositorio.FacturaRepositorio;
import com.example.trabajo_final.Repositorio.HistorialRepositorio;
import com.example.trabajo_final.Repositorio.UsuarioRepositorio;
import com.example.trabajo_final.entidades.Articulo;
import com.example.trabajo_final.entidades.Factura;
import com.example.trabajo_final.entidades.Historial;
import com.example.trabajo_final.entidades.Usuario;
import freemarker.template.utility.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

@Service
public class ServicioActualizacion {
    @Autowired
    private HistorialRepositorio historialRepositorio;
    @Autowired
    private ArticuloRepositorio articuloRepositorio;
    @Autowired
    private FacturaRepositorio facturaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public void updateRegisteredUserHistory(Historial history) throws Exception{

        if (history == null)
            throw new NullArgumentException("This history is void");

        try {
            historialRepositorio.save(history);
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

    public void updateRegisteredProduct(Articulo articulo) throws Exception {

        if (articulo == null)
            throw new NullArgumentException("This product is void");

        try {
            articuloRepositorio.save(articulo);
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

    public void updateRegisteredUserTransaction(Factura factura) throws Exception{

        if (factura == null)
            throw new NullArgumentException("This transaction is void");

        try {
            facturaRepositorio.save(factura);
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

    // User and History Updates
    public void updateRegisteredUserAccount(Usuario usuario) throws Exception {

        if (usuario == null)
            throw new NullArgumentException("This user has a null value");

        if (!isEmailAddressTaken(usuario.getEmail()))
            throw new IllegalArgumentException("This user account does not exist");

        try {
            // Updating user
            usuarioRepositorio.save(usuario);

            // Updating History
            Historial historial = historialRepositorio.findByUser(usuario.getEmail());

            historial.setUser(usuario);

            historialRepositorio.save(historial);
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

    // Auxiliary Functions
    private boolean isEmailAddressTaken(String email){
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        return (usuario != null);
    }
}
