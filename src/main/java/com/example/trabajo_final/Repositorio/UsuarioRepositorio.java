package com.example.trabajo_final.Repositorio;

import com.example.trabajo_final.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.trabajo_final.Tools.Enum.*;

import java.util.List;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    @Query("select u from Usuario u where u.email = :email and u.password = :password")
    Usuario findUserAccountWithUsernameAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("select u from Usuario u where u.status = :status")
    List<Usuario> findByAccountStatus(@Param("status") estadoCuenta status);

    @Query("select u from Usuario u where u.country = :country")
    List<Usuario> findByCountry(@Param("country") String country);

    @Query("select u from Usuario u where u.city = :city and u.country = :country")
    List<Usuario> findByCity(@Param("city") String city, @Param("country") String coutry);
}
