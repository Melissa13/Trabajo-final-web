package com.example.trabajo_final.Controlador;

import com.example.trabajo_final.Servicios.Basicos.ServicioActualizacion;
import com.example.trabajo_final.Servicios.Basicos.ServicioCreacion;
import com.example.trabajo_final.Servicios.Basicos.ServicioEliminar;
import com.example.trabajo_final.Servicios.Basicos.ServicioLectura;
import com.example.trabajo_final.Tools.Enum.estadoCuenta;
import com.example.trabajo_final.entidades.Articulo;
import com.example.trabajo_final.entidades.Factura;
import com.example.trabajo_final.entidades.Historial;
import com.example.trabajo_final.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;
import java.util.*;

@Controller
public class AccesoControlador {

    @Autowired
    private ServicioCreacion SC;

    @Autowired
    private ServicioLectura SL;

    @Autowired
    private ServicioActualizacion SA;

    @Autowired
    private ServicioEliminar SE;

    // Gets
    @GetMapping("/login")
    public ModelAndView fetchLoginView(){

        if(SL.isUserLoggedIn()) // There is no need to log in if already logged in
            return new ModelAndView("redirect:/");

        return new ModelAndView("/Backend/users/login_register");
    }

    @GetMapping("/profile")
    public ModelAndView viewProfile(Model model){

        if(!SL.isUserLoggedIn())
            return new ModelAndView("redirect:/login");

        model.addAttribute("user", SL.findRegisteredUserAccount(SL.getCurrentLoggedUser().getEmail()));

        return new ModelAndView("");
    }

    @GetMapping("/myHistorial")
    public ModelAndView viewHistorial(Model model){

        if(!SL.isUserLoggedIn())
            return new ModelAndView("redirect:/login");

        model.addAttribute("browsingHistorial", SL.findRegisteredUserHistorial(SL.getCurrentLoggedUser().getEmail()).getBrowsingHistorial());
        model.addAttribute("shoppingCart", SL.findRegisteredUserHistorial(SL.getCurrentLoggedUser().getEmail()).getShoppingCart());
        model.addAttribute("transactions", SL.findRegisteredUserTransactions(SL.getCurrentLoggedUser().getEmail()));

        return new ModelAndView("");
    }

    @GetMapping("/transaction/{fiscalCode}")
    public ModelAndView viewTransaction(Model model, @PathVariable("fiscalCode") String fiscalCode){

        if(!SL.isUserLoggedIn())
            return new ModelAndView("redirect:/login");

        model.addAttribute("transaction", SL.findRegisteredTransaction(fiscalCode));

        return new ModelAndView("");
    }


    @GetMapping("/download_pdf/transaction")
    @ResponseBody
    public void downloadTransaction(@RequestParam("fiscalCode") String fiscalCode, HttpServletResponse response) throws JRException, IOException {

        InputStream jasperStream;

        try {
            jasperStream = new FileInputStream(new File("").getAbsolutePath().concat("\\src\\main\\resources\\templates\\jasperreports\\transaction.jasper"));

            if (jasperStream == null){
                JasperCompileManager.compileReportToFile(new File("").getAbsolutePath().concat("\\src\\main\\resources\\templates\\jasperreports\\transaction.jrxml"), new File("").getAbsolutePath().concat("\\src\\main\\resources\\templates\\jasperreports\\transaction.jasper"));
                jasperStream = this.getClass().getResourceAsStream(new File("").getAbsolutePath().concat("\\src\\main\\resources\\templates\\jasperreports\\transaction.jasper"));
            }

            Map<String, Object> params = new HashMap<>();
            params.put("FiscalCode", fiscalCode);

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

            JRDataSource data = new JRMapArrayDataSource(fetchTransactionDataSource());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, data/*new JREmptyDataSource()*/);

            response.setContentType("application/x-pdf");
            response.setHeader("Content-disposition", "inline; filename=Transaction_Report_" + fiscalCode + ".pdf");

            final OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @PostMapping("/userLogin")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("origin") String origin){

        if(SL.isUserLoggedIn()) // There is no need to log in if already logged in
            return "redirect:/";

        if (SL.findRegisteredUserAccount(email.toLowerCase(), password))
        {
            Usuario u = SL.findRegisteredUserAccount(email.toLowerCase());

            if (u.getEstado() == estadoCuenta.SUSPENDIDO)
                return "redirect:/login"; // TODO: Implement "You have been blocked" message

            SL.setSessionAttr("user", u);
            return "redirect:" + origin;
        }
        else
            return "redirect:/login"; // TODO: Implement error exception or message to login
    }

    @PostMapping("/user/change_password")
    public String changePassword(@RequestParam("old") String oldPassword, @RequestParam("new") String newPassword, @RequestParam("confirm") String confirmPassword){

        if(!SL.isUserLoggedIn())
            return "redirect:/login";

        if (!SL.findRegisteredUserAccount(SL.getCurrentLoggedUser().getEmail(), oldPassword))
            return "redirect:/profile"; // TODO: Add error message

        if (oldPassword.equals(newPassword))
            return "redirect:/profile"; // TODO: Add error message

        if (!newPassword.equals(confirmPassword))
            return "redirect:/profile"; // TODO: Add error message

        try {
            Usuario user = SL.findRegisteredUserAccount(SL.getCurrentLoggedUser().getEmail());
            user.setPassword(newPassword);
            SA.updateRegisteredUserAccount(user);

            return "redirect:/profile";
        } catch (Exception exp){
            //
        }

        return "redirect:/profile"; // TODO: Add error message
    }

    @RequestMapping("/logout")
    public ModelAndView logOut(){
        if (!SL.isUserLoggedIn())
            return new ModelAndView("redirect:/login");

        SL.logOut();
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/logout")
    public ModelAndView logOut2(@RequestParam("origin") String origin){

        if (!SL.isUserLoggedIn())
            return new ModelAndView("redirect:/login");

        SL.logOut();
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/edit/first_name")
    public String editFirstName(@RequestParam("email") String email, @RequestParam("new") String newName){

        if (!SL.isUserLoggedIn())
            return "redirect:/login";

        try {
            Usuario user = SL.findRegisteredUserAccount(email);
            user.setNombre(newName);
            SA.updateRegisteredUserAccount(user);

            return "redirect:/profile";
        } catch (Exception exp){
            //
        }

        return "redirect:/profile"; // TODO: Add error message
    }

    @PostMapping("/edit/last_name")
    public String editLastName(@RequestParam("email") String email, @RequestParam("new") String newName){

        if (!SL.isUserLoggedIn())
            return "redirect:/login";

        try {
            Usuario user = SL.findRegisteredUserAccount(email);
            user.setApellido(newName);
            SA.updateRegisteredUserAccount(user);

            return "redirect:/profile";
        } catch (Exception exp){
            //
        }

        return "redirect:/profile"; // TODO: Add error message
    }

    @PostMapping("/edit/full_address")
    public String editCompleteAddress(@RequestParam("email") String email, @RequestParam("newAdress") String newAddress, @RequestParam("newCountry") String newCountry, @RequestParam("newCity") String newCity){

        if (!SL.isUserLoggedIn())
            return "redirect:/login";

        try {
            Usuario user = SL.findRegisteredUserAccount(email);
            user.setDireccion_envio(newAddress);
            user.setPais(newCountry);
            user.setCiudad(newCity);
            SA.updateRegisteredUserAccount(user);

            return "redirect:/profile";
        } catch (Exception exp){
            //
        }

        return "redirect:/profile"; // TODO: Add error message
    }

    @PostMapping("/upload/user_picture")
    public String uploadUserProfilePicture(@RequestParam("email") String email, @RequestParam("file") MultipartFile picture){

        if (!SL.isUserLoggedIn())
            return "redirect:/login";

        try {
            Usuario user = SL.findRegisteredUserAccount(email);
            user.setFoto(processImageFile(picture.getBytes()));
            SA.updateRegisteredUserAccount(user);

            return "redirect:/profile";
        } catch (Exception exp){
            //
        }

        return "redirect:/profile"; // TODO: Add error message
    }


    @PostMapping("/confirm_transaction")
    public String buyItemsInCart(){

        if (!SL.isUserLoggedIn())
            return "redirect:/login";

        try {
            Historial history = SL.findRegisteredUserHistorial(SL.getCurrentLoggedUser().getEmail());
            Set<Articulo> shoppingCart = history.getShoppingCart();
            ArrayList<Integer> amount = history.getAmount();

            ArrayList<Integer> productList = new ArrayList<>();
            Float total = 0.00f;
            int count = 0;

            for (Articulo product:
                    shoppingCart) {
                if (product.getCantidad() > 0){
                    productList.add(product.getId());
                    total += product.getPrecio() * amount.get(count);

                    // Updating inventory
                    product.setCantidad(product.getCantidad() - amount.get(count++));
                    SA.updateRegisteredArticulo(product);
                }
            }

            history.setShoppingCart(new HashSet<>()); // Clearing Shopping cart

            //Completing transaction
            Factura receipt = SC.registerTransaction(SL.getCurrentLoggedUser().getEmail(), productList, amount, total);

            // TODO: Send email to admin for order confirmation
            return "redirect:/download_pdf/transaction?fiscalCode=" + receipt.getCodigoF();

            //return "redirect:/myHistorial";
        } catch (Exception exp){
            //
        }

        return "redirect:/myHistorial"; // TODO: Add error message
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathParam("productId") Integer productId){

        if (!SL.isUserLoggedIn())
            return "redirect:/login";

        try {
            Historial history = SL.findRegisteredUserHistorial(SL.getCurrentLoggedUser().getEmail());
            Set<Articulo> shoppingCart = history.getShoppingCart();
            ArrayList<Integer> amount = history.getAmount();
            Articulo product = SL.findRegisteredArticulo(productId);

            int count = 0;
            for (Articulo p: shoppingCart)
                if (p.getArticuloId().equals(productId))
                    break;
                else
                    count++;

            amount.remove(count);
            shoppingCart.remove(product);
            history.setShoppingCart(shoppingCart);

            UDS.updateRegisteredUserHistorial(history);

            return "redirect:/myHistorial";
        } catch (Exception exp){
            //
        }

        return "redirect:/myHistorial"; // TODO: Add error message
    }

    @PostMapping("/clear")
    public String clearCart(){

        if (!SL.isUserLoggedIn())
            return "redirect:/login";

        try {
            Historial history = SL.findRegisteredUserHistorial(SL.getCurrentLoggedUser().getEmail());
            history.setShoppingCart(new HashSet<>());
            UDS.updateRegisteredUserHistorial(history);

            return "redirect:/myHistorial";
        } catch (Exception exp){
            //
        }

        return "redirect:/myHistorial"; // TODO: Add error message
    }

    @PostMapping("/cancel/{fiscalCode}")
    public String cancelTransaction(@PathParam("fiscalCode") String fiscalCode){

        if (!SL.isUserLoggedIn())
            return "redirect:/login";

        // Only pending orders can be deleted, once shipped or received it can no longer be canceled
        if (SL.findRegisteredTransaction(fiscalCode).getStatus() != OrderStatus.PENDING)
            return "redirect:/myHistorial"; // TODO: Add error message

        try {
            // Updating Inventory
            Factura receipt = SL.findRegisteredTransaction(fiscalCode);
            int count = 0;
            for (Integer productId:
                    receipt.getArticuloList()) {
                Articulo product = SL.findRegisteredArticulo(productId);
                product.setArticuloInStock(product.getArticuloInStock() + receipt.getAmount().get(count));
                UDS.updateRegisteredArticulo(product);
            }

            DDS.deleteRegisteredPendingTransaction(fiscalCode);

            // TODO: email admin of order cancelation

            return "redirect:/myHistorial";
        } catch (Exception exp){
            //
        }

        return "redirect:/myHistorial"; // TODO: Add error message
    }

    @PostMapping("/received/{fiscalCode}")
    public String markTransactionAsReceived(@PathParam("fiscalCode") String fiscalCode){

        if (!SL.isUserLoggedIn())
            return "redirect:/login";

        // Only shipped items can be received
        if (SL.findRegisteredTransaction(fiscalCode).getStatus() != OrderStatus.SHIPPING)
            return "redirect:/myHistorial"; // TODO: Add error message

        try {
            Factura receipt = SL.findRegisteredTransaction(fiscalCode);
            receipt.setStatus(OrderStatus.DELIVERED);
            UDS.updateRegisteredUserTransaction(receipt);

            return "redirect:/myHistorial";
        } catch (Exception exp){
            //
        }

        return "redirect:/myHistorial"; // TODO: Add error message
    }

    // Auxiliary Functions
    private Byte[] processImageFile(byte[] buffer) {
        Byte[] bytes = new Byte[buffer.length];
        int i = 0;

        for (byte b :
                buffer)
            bytes[i++] = b; // Autoboxing

        return bytes;
    }

    private Map[] fetchTransactionDataSource(){
        HashMap[] rows = new HashMap[SL.findAllRegisteredTransactions().size()];
        int count = 0;

        for (Factura r:
                SL.findAllRegisteredTransactions()) {
            HashMap data = new HashMap();
            data.put("fiscal", r.getFiscalCode());
            data.put("user_email", r.getUser().getEmail());
            data.put("user_name", r.getUser().getFullName());
            data.put("time", r.getTransactionDate().toString().substring(0, r.getTransactionDate().toString().length() - 2));
            data.put("total", "$" + r.getTotal().toString());
            data.put("content", formatReceiptBody(r.getArticuloList(), r.getAmount()));

            rows[count++] = data;
        }

        return rows;
    }

    private String formatReceiptBody(ArrayList<Integer> products, ArrayList<Integer> amount){
        String buffer = "";
        int count = 0;

        for (Integer i:
                products) {
            Articulo product = SL.findRegisteredArticulo(i);
            buffer += amount.get(count++).toString() + "x " + product.getArticuloName() + " ------ $" + product.getArticuloPrice().toString() + "\n";
        }

        return buffer;
    }
}
