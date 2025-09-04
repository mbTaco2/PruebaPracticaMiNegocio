package com.alquimiasoft.minegocio.repository;

import com.alquimiasoft.minegocio.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombres) LIKE LOWER(CONCAT('%', :q, '%')) " +
           "OR c.numeroIdentificacion LIKE CONCAT('%', :q, '%')")
    List<Cliente> findByNombresOrNumeroIdentificacionContaining(@Param("q") String q);
}
