package com.example.trabajo_final.Repositorio;

import com.example.trabajo_final.entidades.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticuloRepositorio extends JpaRepository<Articulo, Integer> {

    Articulo findByArtId(Integer productId);

    @Query("select p from Articulo p where p.nombre = :nombre")
    List<Articulo> findByName(@Param("name") String productName);

    @Query("select p from Articulo p where p.supplier = :supplier")
    List<Articulo> findBySupplidor(@Param("supplier") String supplier);

    @Query("select p from Product p where p.productPrice between :low and :high")
    List<Articulo> findByPriceRange(@Param("low") Float minPrice, @Param("high") Float maxPrice);

}
