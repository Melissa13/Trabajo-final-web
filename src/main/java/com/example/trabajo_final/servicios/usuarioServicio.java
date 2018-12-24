package com.example.trabajo_final.servicios;


import com.example.trabajo_final.repositorios.usuarioRepositorio;
import com.example.trabajo_final.repositorios.permisoRepository;
import com.example.trabajo_final.entidades.*;
import com.example.trabajo_final.Tools.Enum.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service("usuarioServicio")
public class usuarioServicio {

    private usuarioRepositorio uRepo;
    private permisoRepository pRepo;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public usuarioServicio(usuarioRepositorio uRepo, permisoRepository pRepo, BCryptPasswordEncoder passwordEncoder) {
        this.uRepo = uRepo;
        this.pRepo = pRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public usuario finUserByEmail(String email)
    {
        return uRepo.findByEmail(email);
    }

    public void SaveU(usuario u)
    {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        permisos rol = pRepo.findByRol("ADMIN");
        u.setRole(new HashSet<permisos>(Arrays.asList(rol)));
        uRepo.save(u);
    }


}
