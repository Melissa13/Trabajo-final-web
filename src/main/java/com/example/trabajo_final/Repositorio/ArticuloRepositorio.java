package com.example.trabajo_final.Repositorio;

import com.example.trabajo_final.entidades.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticuloRepositorio extends JpaRepository<Articulo, Integer> {

    Articulo findByArtId(Integer productId);

    @Query("select p from Articulo p where p.nombre = :nombre")
    List<Articulo> findByName(@Param("suplidor") String productName);

    @Query("select p from Articulo p where p.suplidor = :suplidor")
    List<Articulo> findBySupplidor(@Param("suplidor") String supplier);

    @Query("select p from Articulo p where p.precio between :low and :high")
    List<Articulo> findByPriceRange(@Param("low") Float minPrice, @Param("high") Float maxPrice);

}
