package com.example.trabajo_final.entidades;

import java.util.Date;
import java.util.Set;

public class factura {

    private long id;
    private clientes cliente;
    private Set<articulos> items;
    private Date fecha_pedido;
    private double precio_total;
    private String estado;

    public factura(long id, clientes cliente, Set<articulos> items, Date fecha_pedido, double precio_total, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.items = items;
        this.fecha_pedido = fecha_pedido;
        this.precio_total = precio_total;
        this.estado = estado;
    }

    public factura() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public clientes getCliente() {
        return cliente;
    }

    public void setCliente(clientes cliente) {
        this.cliente = cliente;
    }

    public Set<articulos> getItems() {
        return items;
    }

    public void setItems(Set<articulos> items) {
        this.items = items;
    }

    public Date getFecha_pedido() {  return fecha_pedido; }

    public void setFecha_pedido(Date fecha_pedido) {  this.fecha_pedido = fecha_pedido;  }

    public double getPrecio_total() {  return precio_total;  }

    public void setPrecio_total(double precio_total) {  this.precio_total = precio_total;  }

    public String getEstado() {  return estado;  }

    public void setEstado(String estado) {  this.estado = estado;  }
}
