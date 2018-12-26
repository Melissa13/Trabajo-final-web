package com.example.trabajo_final.repositorios;

import com.example.trabajo_final.entidades.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface usuarioRepositorio extends JpaRepository<usuario, Long> {
    usuario findByEmail(String Email);

    usuario findByUsername(String Username);
}
