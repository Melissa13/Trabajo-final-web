package com.example.trabajo_final.Tools.Enum;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class permisos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;
    private String rol;

    public permisos(String rol) {
        this.rol = rol;
    }

    public permisos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
