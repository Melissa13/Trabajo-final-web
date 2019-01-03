package com.example.trabajo_final.Servicios.Basicos;

import com.example.trabajo_final.Repositorio.ArticuloRepositorio;
import com.example.trabajo_final.Repositorio.FacturaRepositorio;
import com.example.trabajo_final.Repositorio.HistorialRepositorio;
import com.example.trabajo_final.Tools.Enum.EstadoEnvio;
import com.example.trabajo_final.entidades.Articulo;
import com.example.trabajo_final.entidades.Factura;
import com.example.trabajo_final.entidades.Historial;
import freemarker.template.utility.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Set;

@Service
public class ServicioEliminar {

    @Autowired
    private HistorialRepositorio historyRepository;
    @Autowired
    private ArticuloRepositorio productRepository;
    @Autowired
    private FacturaRepositorio receiptRepository;

    public void deleteRegisteredProduct(Integer productId) throws Exception{

        if (!doesProductIdExist(productId))
            throw new IllegalArgumentException("This product does not exists");

        try {
            Articulo product = productRepository.findByArtId(productId);

            for (Historial history:
                    historyRepository.findAll()) {
                Set<Articulo> browsingHistory = history.getBrowsingHistory();
                Set<Articulo> shoppingCart = history.getShoppingCart();

                browsingHistory.remove(product);
                shoppingCart.remove(product);

                history.setBrowsingHistory(browsingHistory);
                history.setShoppingCart(shoppingCart);
                historyRepository.save(history);
            }

            productRepository.delete(product);
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (NullArgumentException exp){
            throw new NullArgumentException("Null Argument Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

    public void deleteRegisteredPendingTransaction(String fiscalCode) throws Exception{

        if (receiptRepository.findByFiscalCode(fiscalCode).getEstado() == EstadoEnvio.PENDIENTE)
            throw new IllegalArgumentException("This is an illegal action! You cannot delete a pending transaction");

        try {
            receiptRepository.delete(fiscalCode);
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error --> " + exp.getMessage());
        } catch (NullArgumentException exp){
            throw new NullArgumentException("Null Argument Error --> " + exp.getMessage());
        } catch (Exception exp){
            throw new Exception("General Error --> " + exp.getMessage());
        }
    }

    private boolean doesProductIdExist(Integer productId){
        Articulo product = productRepository.findByArtId(productId);
        return (product != null);
    }

}
