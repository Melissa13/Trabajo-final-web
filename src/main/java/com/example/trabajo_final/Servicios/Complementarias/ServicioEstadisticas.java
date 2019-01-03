package com.example.trabajo_final.Servicios.Complementarias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.trabajo_final.entidades.*;
import com.example.trabajo_final.Repositorio.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServicioEstadisticas {
    // Repositories
    @Autowired
    private HistorialRepositorio historialRepositorio;
    @Autowired
    private ArticuloRepositorio articuloRepositorio;
    @Autowired
    private FacturaRepositorio facturaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // Functions
    // Product related Statistics
    public ArrayList<String> productViewStatistics(){

        Map<Integer, Integer> statistic = fetchProductLegend();
        ArrayList<String> buffer = new ArrayList<>();

        try {
            for (Historial history: historialRepositorio.findAll())
                for (Articulo articulo: history.getBrowsingHistory())
                    statistic.replace(articulo.getId(), statistic.get(articulo.getId()) + 1);

            for (Integer i:
                    statistic.keySet())
                buffer.add("'" + i.toString() + "--" + articuloRepositorio.findByArtId(i).getNombre() + "', " + statistic.get(i).toString() + ", 'color: #b87333'");

            return buffer;
        } catch (Exception exp) {
            System.out.println();
        }

        return null;
    }

    public ArrayList<String> productPurchaseStatistics(){

        Map<Integer, Integer> statistic = fetchProductLegend();
        ArrayList<String> buffer = new ArrayList<>();

        try {
            for (Factura factura: facturaRepositorio.findAll())
                for (Integer p: factura.getArticulosL())
                    statistic.replace(p, statistic.get(p) + 1);

            for (Integer i:
                    statistic.keySet())
                buffer.add("'" + i.toString() + "--" + articuloRepositorio.findByArtId(i).getNombre() + "', " + statistic.get(i).toString() + "', 'color: gold'");

            return buffer;
        } catch (Exception exp) {
            //
        }

        return null;
    }

    public ArrayList<String> productSupplierStatistics(){

        try {
            Map<String, Integer> statistic = fetchSupplierLegend();
            ArrayList<String> buffer = new ArrayList<>();

            for (Articulo articulo: articuloRepositorio.findAll())
                statistic.replace(articulo.getSupplidor(), statistic.get(articulo.getSupplidor()) + 1);

            for (String supplier:
                    statistic.keySet())
                buffer.add("'" + supplier + "', " + statistic.get(supplier).toString());

            return buffer;

        } catch (Exception exp) {
            //
        }

        return null;
    }

    // Transaction Related Functions
    public ArrayList<String> userAveragePurchaseByDollar(){

        try {
            Map<String, Float> statistics = fetchUserLegend();
            ArrayList<String> buffer = new ArrayList<>();

            for (String email: statistics.keySet()) {
                int count = 0;
                for (Factura factura : facturaRepositorio.findByUser(email)) {
                    statistics.replace(email, statistics.get(email) + factura.getPrecio_total());
                    count++;
                }

                statistics.replace(email, statistics.get(email)/count);
            }

            for (String email: statistics.keySet())
                buffer.add("'" + email + "', " + Float.toString(statistics.get(email)) + ", 'color: silver'");

            return buffer;
        } catch (Exception exp) {
            //
        }

        return null;
    }

    public ArrayList<String> userAverageNumberOfItemPurchase(){

        try {
            Map<String, Float> statistics = fetchUserLegend();
            ArrayList<String> buffer = new ArrayList<>();

            for (String email: statistics.keySet()) {
                int count = 0;
                for (Factura factura : facturaRepositorio.findByUser(email)) {
                    statistics.replace(email, statistics.get(email) + factura.getArticulosL().size());
                    count++;
                }

                statistics.replace(email, statistics.get(email)/count);
            }

            for (String email: statistics.keySet())
                buffer.add("'" + email + "', " + Float.toString(statistics.get(email)) + ", 'color: silver'");

            return buffer;
        } catch (Exception exp) {
            //
        }

        return null;
    }


    // Auxiliary Functions
    private Map<Integer, Integer> fetchProductLegend(){
        Map<Integer, Integer> legend = new HashMap<>();

        for (Articulo a:
                articuloRepositorio.findAll()) {
            legend.put(a.getId(), 0);
        }

        return legend;
    }

    private Map<String, Integer> fetchSupplierLegend(){
        Map<String, Integer> legend = new HashMap<>();

        for (Articulo a: articuloRepositorio.findAll())
            legend.putIfAbsent(a.getSupplidor(), 0);

        return legend;
    }

    private Map<String, Float> fetchUserLegend(){
        Map<String, Float> legend = new HashMap<>();

        for (Usuario usuario: usuarioRepositorio.findAll())
            legend.putIfAbsent(usuario.getEmail(), 0.00f);

        return legend;
    }
}
