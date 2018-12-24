package com.example.trabajo_final.repositorios;

import com.example.trabajo_final.entidades.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface articulosRepositorio extends JpaRepository<articulos,Long> {
    @Query("select u from articulos u where u.id = :id")
    articulos buscar(@Param("id") Long id);
}
