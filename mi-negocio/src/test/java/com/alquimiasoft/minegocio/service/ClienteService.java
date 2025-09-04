package com.alquimiasoft.minegocio.service;

import com.alquimiasoft.minegocio.dto.request.ClienteRequest;
import com.alquimiasoft.minegocio.dto.request.DireccionRequest;
import com.alquimiasoft.minegocio.model.Cliente;
import com.alquimiasoft.minegocio.model.Direccion;

import java.util.List;

public interface ClienteService {
    Cliente crearCliente(ClienteRequest request);
    Cliente actualizarCliente(Long id, ClienteRequest request);
    void eliminarCliente(Long id);
    Cliente obtenerClientePorId(Long id);
    List<Cliente> buscarClientes(String query);
    void registrarDireccion(Long clienteId, DireccionRequest direccionRequest);
    List<Direccion> obtenerTodasLasDireccionesDelCliente(Long clienteId);
}
