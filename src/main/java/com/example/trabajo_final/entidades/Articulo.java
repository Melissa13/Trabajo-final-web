package com.example.trabajo_final.entidades;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
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
    private Float precio;
    private Integer cantidad;
    private Byte[] foto_producto;

    public Articulo() {
    }

    public Articulo(String nombre, String descripcion, String supplidor, float precio, Integer cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.supplidor = supplidor;
        this.precio = precio;
        this.cantidad = cantidad;
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

    public Float getPrecio() { return precio; }

    public void setPrecio(Float precio) { this.precio = precio; }

    public Integer getCantidad() { return cantidad; }

    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Byte[] getFoto_producto() { return foto_producto; }

    public void setFoto_producto(Byte[] foto_producto) { this.foto_producto = foto_producto; }
}
