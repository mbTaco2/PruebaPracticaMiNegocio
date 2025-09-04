package com.alquimiasoft.minegocio.repository;

import com.alquimiasoft.minegocio.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByClienteId(Long clienteId);
}