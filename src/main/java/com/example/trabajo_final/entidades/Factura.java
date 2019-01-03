package com.example.trabajo_final.entidades;

import com.example.trabajo_final.Tools.Enum.EstadoEnvio;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne()
    private Usuario usuario;
    @OneToMany(mappedBy = "orden_factura", fetch = FetchType.EAGER)
    private Set<Historial> items;
    private ArrayList<Integer> monto;
    private ArrayList<Integer> articulosL;
    private Timestamp fecha_pedido;
    private Float precio_total;
    private EstadoEnvio estado;
    private String codigoF;

    public Factura(Usuario usuario, ArrayList<Integer> monto, ArrayList<Integer> articulosL, Timestamp fecha_pedido, Float precio_total) {
        this.usuario = usuario;
        this.monto = monto;
        this.articulosL = articulosL;
        this.fecha_pedido = fecha_pedido;
        this.precio_total = precio_total;
        this.estado = EstadoEnvio.PENDIENTE;
        this.codigoF= UUID.randomUUID().toString().split("-")[0].toUpperCase();
    }

    public Factura() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Integer> getMonto() {
        return monto;
    }

    public void setMonto(ArrayList<Integer> monto) {
        this.monto = monto;
    }

    public ArrayList<Integer> getArticulosL() {
        return articulosL;
    }

    public void setArticulosL(ArrayList<Integer> articulosL) {
        this.articulosL = articulosL;
    }

    public Timestamp getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Timestamp fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public Float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(Float precio_total) {
        this.precio_total = precio_total;
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
    }

    public String getCodigoF() {
        return codigoF;
    }

    public void setCodigoF(String codigoF) {
        this.codigoF = codigoF;
    }
}
