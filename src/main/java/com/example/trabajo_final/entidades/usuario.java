package com.example.trabajo_final.entidades;

import com.example.trabajo_final.Tools.Enum.estadoCuenta;
import com.example.trabajo_final.Tools.Enum.permisos;
import org.apache.commons.codec.binary.Base64;

public class usuario {

    private String email;
    private String nombre;
    private String apellido;
    private String direccion;
    private String pais;
    private String ciudad;
    private String password;
    private permisos role;
    private estadoCuenta estado;
    private Byte[] foto;

    public usuario(String email, String nombre, String apellido, String direccion, String pais, String ciudad, String password, permisos role) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.pais = pais;
        this.ciudad = ciudad;
        this.password = password;
        this.role = role;
        this.setEstado(estadoCuenta.SUSPENDIDO);
    }

    public usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public permisos getRole() {
        return role;
    }

    public void setRole(permisos role) {
        this.role = role;
    }

    public estadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(estadoCuenta estado) {
        this.estado = estado;
    }

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }

    public String mostrarFoto(){
        if(this.foto == null)
            return null;

        byte[] imgBytesAsBase64 = Base64.encodeBase64(toPrimitives(this.foto));
        return new String(imgBytesAsBase64);
    }

    private byte[] toPrimitives(Byte[] buffer) {

        byte[] bytes = new byte[buffer.length];
        for(int i = 0; i < buffer.length; i++){
            bytes[i] = buffer[i];
        }
        return bytes;
    }
}
