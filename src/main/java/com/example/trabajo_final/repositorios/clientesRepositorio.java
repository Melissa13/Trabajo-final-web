package com.example.trabajo_final.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trabajo_final.entidades.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface clientesRepositorio extends JpaRepository<clientes,Long>{
    @Query("select u from clientes u where u.id = :id")
    clientes buscar(@Param("id") Long id);
}
