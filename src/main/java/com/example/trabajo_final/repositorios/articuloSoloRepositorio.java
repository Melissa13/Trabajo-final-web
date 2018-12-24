package com.example.trabajo_final.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trabajo_final.entidades.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface articuloSoloRepositorio extends JpaRepository<articuloSolo,Long> {
    @Query("select u from articuloSolo u where u.id = :id")
    articuloSolo buscar(@Param("id") Long id);
}
