package com.example.trabajo_final.Repositorio;

import com.example.trabajo_final.entidades.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HistorialRepositorio extends JpaRepository<Historial, Integer> {

    Historial findByHistoryId(Integer historyId);

    @Query("select h from History h where h.user.email = :email")
    Historial findByUser(@Param("email") String email);

}
