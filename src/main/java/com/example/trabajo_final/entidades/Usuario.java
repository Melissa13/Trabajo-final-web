package com.example.trabajo_final.entidades;

import com.example.trabajo_final.Tools.Enum.Rol;
import com.example.trabajo_final.Tools.Enum.estadoCuenta;
//import com.example.trabajo_final.Tools.Enum.permisos;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String email;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    @Column(length = 1000)
    private String direccion_envio;
    @NotNull
    private String pais;
    @NotNull
    private String ciudad;    
    @NotNull
    private String password;
    @NotNull 
    Rol role;
    @NotNull
    private estadoCuenta estado;
    private Byte[] foto;

    public Usuario(String email, String nombre, String apellido, String direccion_envio, String pais, String ciudad, String password, Rol role) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion_envio = direccion_envio;
        this.pais = pais;
        this.ciudad = ciudad;
        this.password = password;
        this.role = role;
        this.setEstado(estadoCuenta.SUSPENDIDO);
    }

    public Usuario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion_envio() {
        return direccion_envio;
    }

    public void setDireccion_envio(String direccion_envio) {
        this.direccion_envio = direccion_envio;
    }

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }

    public String mostrar(){
        if(this.foto == null)
            return null;

        byte[] BytesAsBase64 = org.apache.tomcat.util.codec.binary.Base64.encodeBase64(auxiliar(this.foto));
        return new String(BytesAsBase64);
    }

    // Auxiliary Function
    private byte[] auxiliar(Byte[] buffer) {

        byte[] bytes = new byte[buffer.length];
        for(int i = 0; i < buffer.length; i++){
            bytes[i] = buffer[i];
        }
        return bytes;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public estadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(estadoCuenta estado) {
        this.estado = estado;
    }

    public Rol getRole() {
        return role;
    }

    public void setRole(Rol role) {
        this.role = role;
    }
}
