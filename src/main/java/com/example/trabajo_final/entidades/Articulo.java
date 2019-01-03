package com.example.trabajo_final.entidades;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "articulo")
public class Articulo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String descripcion;
    private String supplidor;
    private double precio;
    private long cantidad;
    private Byte[] foto_producto;

    public Articulo() {
    }

    public Articulo(int id, String nombre, String descripcion, String supplidor, double precio, long cantidad, Byte[] foto_producto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.supplidor = supplidor;
        this.precio = precio;
        this.cantidad = cantidad;
        this.foto_producto = foto_producto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSupplidor() { return supplidor; }

    public void setSupplidor(String supplidor) { this.supplidor = supplidor; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public long getCantidad() { return cantidad; }

    public void setCantidad(long cantidad) { this.cantidad = cantidad; }

    public Byte[] getFoto_producto() { return foto_producto; }

    public void setFoto_producto(Byte[] foto_producto) { this.foto_producto = foto_producto; }
}
