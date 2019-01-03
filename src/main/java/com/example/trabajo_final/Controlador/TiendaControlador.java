package com.example.trabajo_final.Controlador;

import com.example.trabajo_final.Servicios.Basicos.ServicioActualizacion;
import com.example.trabajo_final.Servicios.Basicos.ServicioCreacion;
import com.example.trabajo_final.Servicios.Basicos.ServicioLectura;
import com.example.trabajo_final.entidades.Articulo;
import com.example.trabajo_final.entidades.Factura;
import com.example.trabajo_final.entidades.Historial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class TiendaControlador {

    @Autowired
    private ServicioCreacion SC;
    @Autowired
    private ServicioLectura SL;
    @Autowired
    private ServicioActualizacion SA;

    @GetMapping("/")
    public ModelAndView storeFront(Model model) {

        model.addAttribute("user", SL.getCurrentLoggedUser());

        if(SL.getCurrentLoggedUser() != null)
            model.addAttribute("shoppingCart", SL.findRegisteredUserHistory(SL.getCurrentLoggedUser().getEmail()).getShoppingCart());
        else
            model.addAttribute("shoppingCart", new HashSet<Articulo>()); // empty cart

        model.addAttribute("selection", SL.findAllRegisteredProducts());


        return new ModelAndView("StoreFront/homepage/index");
    }

    @GetMapping("/account")
    public ModelAndView account(Model model){

        if(!SL.isUserLoggedIn())
            return new ModelAndView("redirect:/login");

        return new ModelAndView("StoreFront/account");
    }

    @GetMapping("/cart")
    public ModelAndView cart(Model model){
        if(SL.getCurrentLoggedUser() != null)
            model.addAttribute("shoppingCart", SL.findRegisteredUserHistory(SL.getCurrentLoggedUser().getEmail()).getShoppingCart());
        else
            model.addAttribute("shoppingCart", new HashSet<Articulo>()); // empty cart


        return new ModelAndView("StoreFront/cart");
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(Model model){

        if(SL.getCurrentLoggedUser() != null)
            model.addAttribute("shoppingCart", SL.findRegisteredUserHistory(SL.getCurrentLoggedUser().getEmail()).getShoppingCart());
        else
            model.addAttribute("shoppingCart", new HashSet<Articulo>()); // empty cart
        float total=0;
        for(Articulo i : SL.findRegisteredUserHistory(SL.getCurrentLoggedUser().getEmail()).getShoppingCart())
        {
            total+=i.getPrecio();
        }
        model.addAttribute("total",total);


        return new ModelAndView("StoreFront/checkout");
    }

    @GetMapping("/products")
    public ModelAndView productList(Model model){

        if(SL.getCurrentLoggedUser() != null)
            model.addAttribute("shoppingCart", SL.findRegisteredUserHistory(SL.getCurrentLoggedUser().getEmail()).getShoppingCart());
        else
            model.addAttribute("shoppingCart", new HashSet<Articulo>()); // empty cart

        model.addAttribute("selection", SL.findAllRegisteredProducts());

        return new ModelAndView("StoreFront/product_list/product");
    }

    @GetMapping("/product-detail/{id}")
    public ModelAndView product(Model model, @PathVariable("id") String productId) {
        if(SL.getCurrentLoggedUser() != null)
            model.addAttribute("shoppingCart", SL.findRegisteredUserHistory(SL.getCurrentLoggedUser().getEmail()).getShoppingCart());
        else
            model.addAttribute("shoppingCart", new HashSet<Articulo>()); // empty cart


        Articulo product = SL.findRegisteredProduct(Integer.parseInt(productId));

        model.addAttribute("item", product);

        try {
            Historial history = SL.findRegisteredUserHistory(SL.getCurrentLoggedUser().getEmail());
            Set<Articulo> browsingHistory = history.getBrowsingHistory();

            // Updating the browsing history
            browsingHistory.add(product);
            history.setBrowsingHistory(browsingHistory);

            SA.updateRegisteredUserHistory(history);
        } catch (Exception exp){
            //
        }

        return new ModelAndView("StoreFront/product_details/product-detail");
    }

    // Posts
    @PostMapping("/add_to_cart")
    public String addToCart(@RequestParam("productId") String productId){

        if(!SL.isUserLoggedIn())
            return "redirect:/login";

        try {
            Articulo product = SL.findRegisteredProduct(Integer.parseInt(productId));

            if (product.getCantidad() > 0) {
                Historial history = SL.findRegisteredUserHistory(SL.getCurrentLoggedUser().getEmail());
                Set<Articulo> shoppingCart = history.getShoppingCart();
                ArrayList<Integer> amount = history.getAmount();
                Set<Articulo> browsingHistory = history.getBrowsingHistory();

                if (amount == null)
                    amount = new ArrayList<>(); // fail safe

                // Adding to cart
                shoppingCart.add(product);
                amount.add(1);
                history.setShoppingCart(shoppingCart);
                history.setAmount(amount);

                // Updating the browsing history
                browsingHistory.add(product);
                history.setBrowsingHistory(browsingHistory);

                SA.updateRegisteredUserHistory(history);

                return "redirect:/products"; // TODO: this should go back to the origin - store page, or product detail
            } else
                return "redirect:/products"; // TODO: this should go back to the origin - store page, or product detail with error message
        } catch (Exception exp){
            //
        }

        return "redirect:/"; // TODO: Add error handling
    }

    @PostMapping("/one_click/quick_buy")
    public String oneClickBuy(@RequestParam("productId") Integer productId,@RequestParam("stripeToken") String stripeToken,@RequestParam("stripeEmail") String stripeEmail){
        if(!SL.isUserLoggedIn())
            return "redirect:/login";

        try {
            Articulo product = SL.findRegisteredProduct(productId);
            ArrayList<Integer> list = new ArrayList<>();
            ArrayList<Integer> amount = new ArrayList<>();

            if (product.getCantidad() > 0) {

                list.add(product.getId());
                amount.add(1);

                // Updating Inventory
                product.setCantidad(product.getCantidad() - 1);
            }

            Factura receipt = SC.registerTransaction(SL.getCurrentLoggedUser().getEmail(), list, amount, product.getPrecio());

            // TODO: send email to admin to confirm transaction
            return "redirect:/download_pdf/transaction?fiscalCode=" + receipt.getCodigoF();

            //return "redirect:/"; // TODO: this should go back to the origin - store page, or product detail
        } catch (Exception exp){
            //
        }

        return "redirect:/"; // TODO: Add error handling
    }
}
