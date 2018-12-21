package com.example.trabajo_final.entidades;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "articulo_solo")
public class articuloSolo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne()
    private articulos asociado;
    private long cantidad;
    @ManyToOne()
    private factura orden_factura;

    public articuloSolo() {
    }

    public articuloSolo(articulos asociado, long cantidad, factura orden_factura) {
        this.asociado = asociado;
        this.cantidad = cantidad;
        this.orden_factura = orden_factura;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public articulos getAsociado() {
        return asociado;
    }

    public void setAsociado(articulos asociado) {
        this.asociado = asociado;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public factura getOrden_factura() {
        return orden_factura;
    }

    public void setOrden_factura(factura orden_factura) {
        this.orden_factura = orden_factura;
    }
}
