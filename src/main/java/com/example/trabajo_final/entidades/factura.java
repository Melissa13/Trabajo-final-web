package com.example.trabajo_final.entidades;

import java.util.Set;

public class factura {

    public long id;
    public clientes cliente;
    public Set<articulos> items;

    public factura(long id, clientes cliente, Set<articulos> items) {
        this.id = id;
        this.cliente = cliente;
        this.items = items;
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
}
