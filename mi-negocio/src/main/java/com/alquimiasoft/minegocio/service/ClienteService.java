// src/main/java/com/alquimiasoft/minnegocio/service/ClienteService.java
package com.alquimiasoft.minegocio.service;

import com.alquimiasoft.minegocio.dto.ClienteDTO;
import com.alquimiasoft.minegocio.dto.DireccionDTO;
import com.alquimiasoft.minegocio.model.Cliente;
import com.alquimiasoft.minegocio.model.Direccion;
import com.alquimiasoft.minegocio.model.TipoIdentificacion;
import com.alquimiasoft.minegocio.repository.ClienteRepository;
import com.alquimiasoft.minegocio.service.exception.ClienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        if (clienteRepository.findByNumeroIdentificacion(clienteDTO.getNumeroIdentificacion()).isPresent()) {
            throw new ClienteException("Ya existe un cliente con ese número de identificación");
        }

        boolean tieneMatriz = clienteDTO.getDirecciones().stream().anyMatch(DireccionDTO::isEsMatriz);
        if (!tieneMatriz) {
            throw new ClienteException("Debe registrar al menos una dirección matriz");
        }

        Cliente cliente = new Cliente();
        cliente.setTipoIdentificacion(clienteDTO.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(clienteDTO.getNumeroIdentificacion());
        cliente.setNombres(clienteDTO.getNombres());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setCelular(clienteDTO.getCelular());

        clienteDTO.getDirecciones().forEach(d -> {
            Direccion direccion = Direccion.builder()
                    .provincia(d.getProvincia())
                    .ciudad(d.getCiudad())
                    .direccion(d.getDireccion())
                    .esMatriz(d.isEsMatriz())
                    .build();
            cliente.addDireccion(direccion);
        });

        Cliente saved = clienteRepository.save(cliente);
        return toDTO(saved);
    }

    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente no encontrado"));

        if (!cliente.getNumeroIdentificacion().equals(clienteDTO.getNumeroIdentificacion())) {
            if (clienteRepository.findByNumeroIdentificacion(clienteDTO.getNumeroIdentificacion()).isPresent()) {
                throw new ClienteException("Ya existe un cliente con ese número de identificación");
            }
        }

        cliente.setNombres(clienteDTO.getNombres());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setCelular(clienteDTO.getCelular());

        return toDTO(clienteRepository.save(cliente));
    }

    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }

    public ClienteDTO obtenerCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente no encontrado"));
        return toDTO(cliente);
    }

    public List<ClienteDTO> buscarClientes(String query) {
        return clienteRepository.searchByQuery(query).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO agregarDireccion(Long id, DireccionDTO direccionDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente no encontrado"));

        Direccion direccion = Direccion.builder()
                .provincia(direccionDTO.getProvincia())
                .ciudad(direccionDTO.getCiudad())
                .direccion(direccionDTO.getDireccion())
                .esMatriz(direccionDTO.isEsMatriz())
                .cliente(cliente)
                .build();

        if (direccion.isEsMatriz()) {
            cliente.getDirecciones().forEach(d -> d.setEsMatriz(false));
        }

        cliente.addDireccion(direccion);
        return toDTO(clienteRepository.save(cliente));
    }

    public List<DireccionDTO> listarDirecciones(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente no encontrado"));

        return cliente.getDirecciones().stream()
                .map(d -> new DireccionDTO(d.getId(), d.getProvincia(), d.getCiudad(), d.getDireccion(), d.isEsMatriz()))
                .collect(Collectors.toList());
    }

    private ClienteDTO toDTO(Cliente cliente) {
        List<DireccionDTO> direcciones = cliente.getDirecciones().stream()
                .map(d -> new DireccionDTO(d.getId(), d.getProvincia(), d.getCiudad(), d.getDireccion(), d.isEsMatriz()))
                .collect(Collectors.toList());

        return new ClienteDTO(
                cliente.getId(),
                cliente.getTipoIdentificacion(),
                cliente.getNumeroIdentificacion(),
                cliente.getNombres(),
                cliente.getCorreo(),
                cliente.getCelular(),
                direcciones
        );
    }
}