// src/main/java/com/alquimiasoft/minnegocio/repository/ClienteRepository.java
package com.alquimiasoft.minegocio.repository;

import com.alquimiasoft.minegocio.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNumeroIdentificacion(String numeroIdentificacion);

    @Query("SELECT c FROM Cliente c JOIN c.direcciones d WHERE c.nombres LIKE %:query% OR d.direccion LIKE %:query% OR c.numeroIdentificacion LIKE %:query%")
    List<Cliente> searchByQuery(@Param("query") String query);
}