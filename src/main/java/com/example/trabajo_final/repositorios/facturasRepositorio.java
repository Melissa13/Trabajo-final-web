package com.example.trabajo_final.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trabajo_final.entidades.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface facturasRepositorio extends JpaRepository<factura,Long>{
    @Query("select u from factura u where u.id = :id")
    factura buscar(@Param("id") Long id);
}
