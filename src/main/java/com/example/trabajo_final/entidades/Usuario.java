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
    private long cedula;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private Date birth_date;
    private String genero;
    @NotNull
    @Column(length = 1000)
    private String direccion_envio;
    private Long cuenta_cancaria;
    private Byte[] foto;
    private String ciudad;
    private String pais;
    private String email;
    private estadoCuenta estado;
    @NotNull
    private Rol rol;

    public Usuario(long id, long cedula, String nombre, String apellido, String username, String password, Date birth_date, String genero, String direccion_envio, Long cuenta_cancaria, Byte[] foto, String ciudad, String pais, String email, Rol rol){
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.birth_date = birth_date;
        this.genero = genero;
        this.direccion_envio = direccion_envio;
        this.cuenta_cancaria = cuenta_cancaria;
        this.foto = foto;
        this.ciudad = ciudad;
        this.pais = pais;
        this.email = email;
        this.estado=estadoCuenta.SUSPENDIDO;
        this.rol=rol;
    }

    public Usuario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion_envio() {
        return direccion_envio;
    }

    public void setDireccion_envio(String direccion_envio) {
        this.direccion_envio = direccion_envio;
    }

    public Long getCuenta_cancaria() {
        return cuenta_cancaria;
    }

    public void setCuenta_cancaria(Long cuenta_cancaria) {
        this.cuenta_cancaria = cuenta_cancaria;
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
}
