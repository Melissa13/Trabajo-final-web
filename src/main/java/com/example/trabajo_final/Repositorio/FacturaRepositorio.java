package com.example.trabajo_final.Repositorio;

import com.example.trabajo_final.Tools.Enum.EstadoEnvio;
import com.example.trabajo_final.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface FacturaRepositorio extends JpaRepository<Factura, Long> {
    Factura findByFiscalCode(String fiscalCode);

    @Query("select r from Factura r where r.user.email = :email")
    List<Factura> findByUser(@Param("email") String email);

    @Query("select r from Factura r where r.status = :status")
    List<Factura> findByOrderStatus(@Param("status") EstadoEnvio status);

    @Query("select r from Factura r where r.transactionDate between :beginning and :ending")
    List<Factura> findByTimestamp(@Param("beginning") Timestamp start, @Param("ending") Timestamp end);

    void delete(String fiscalCode);
}
