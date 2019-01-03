package com.example.trabajo_final.Servicios.Basicos;

import com.example.trabajo_final.Repositorio.ArticuloRepositorio;
import com.example.trabajo_final.Repositorio.FacturaRepositorio;
import com.example.trabajo_final.Repositorio.HistorialRepositorio;
import com.example.trabajo_final.Repositorio.UsuarioRepositorio;
import com.example.trabajo_final.Servicios.Complementarias.ServicioEncryt;
import com.example.trabajo_final.Tools.Enum.EstadoEnvio;
import com.example.trabajo_final.Tools.Enum.estadoCuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.trabajo_final.entidades.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ServicioLectura {
    // Repositories
    @Autowired
    private HistorialRepositorio historialRepositorio;
    @Autowired
    private ArticuloRepositorio articuloRepositorio;
    @Autowired
    private FacturaRepositorio facturaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private HttpSession session;
    @Autowired
    private ServicioEncryt servicioEncryt;


    public Object getSessionAttr(String name)
    {
        return session.getAttribute(name);
    }

    public void setSessionAttr(String name,Object obj)
    {
        session.setAttribute(name,obj);
    }

    // Single Search
    public Historial findRegisteredUserHistory(String email) { return historialRepositorio.findByUser(email); }

    public Articulo findRegisteredProduct(Integer productId) { return articuloRepositorio.findByArtId(productId); }

    public Factura findRegisteredTransaction(String fiscalCode) { return facturaRepositorio.findByFiscalCode(fiscalCode); }

    public Usuario findRegisteredUserAccount(String email) { return usuarioRepositorio.findByEmail(email); } // Used for profiles

    public boolean findRegisteredUserAccount(String email, String password) {
        Usuario usuario = usuarioRepositorio.findUserAccountWithUsernameAndPassword(email, ServicioEncryt.encryptPassword(password));
        return (usuario != null);
    }

    // Complete Search
    public List<Articulo> findAllRegisteredProducts() { return articuloRepositorio.findAll(); }

    public List<Factura> findAllRegisteredTransactions() { return facturaRepositorio.findAll(); }

    public List<Usuario> findAllRegisteredAccounts() { return usuarioRepositorio.findAll(); }

    // Specific Search
    public List<Articulo> findRegisteredProductsWithName(String name) { return articuloRepositorio.findByName(name); }

    public List<Articulo> findRegisteredProductsFromSupplier(String supplier) { return articuloRepositorio.findBySupplidor(supplier); }

    public List<Articulo> findRegisteredProductsByPriceRange(Float minPrice, Float maxPrice){

        if (minPrice < 0.00f || maxPrice < 0.00f)
            throw new IllegalArgumentException("Price range must be in the positive");

        if (minPrice < maxPrice)
            return articuloRepositorio.findByPriceRange(minPrice, maxPrice);
        else
            return articuloRepositorio.findByPriceRange(maxPrice, minPrice);
    }

    public List<Factura> findRegisteredUserTransactions(String email) {

        if (!isEmailAddressTaken(email))
            throw new IllegalArgumentException("This user account does not exist");

        return facturaRepositorio.findByUser(email);
    }

    public List<Factura> findRegisteredTransactionByStatus(EstadoEnvio status) { return facturaRepositorio.findByOrderStatus(status); }

    public List<Usuario> findRegisteredAccountsByStatus(estadoCuenta status) { return usuarioRepositorio.findByAccountStatus(status); }
    // TODO: Add specific searches as the need comes

    // Auxiliary Functions
    private boolean isEmailAddressTaken(String email){
        Usuario user = usuarioRepositorio.findByEmail(email);
        return (user != null);
    }

    public boolean isUserLoggedIn() {
        return null != session.getAttribute("user");
    }

    public void logOut()
    {
        session.invalidate();
    }

    public Usuario getCurrentLoggedUser()
    {
        return (Usuario) session.getAttribute("user");
    }

    // User Queries
    public Usuario findUserInformation(String email) { return usuarioRepositorio.findByEmail(email); }


}
