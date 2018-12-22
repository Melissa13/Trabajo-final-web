package com.example.trabajo_final.repositorios;
import com.example.trabajo_final.Tools.Enum.permisos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface permisoRepository extends JpaRepository<permisos, Integer>{
    permisos findByRol(String rol);
}
