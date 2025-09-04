package com.alquimiasoft.minegocio.service.impl;

import com.alquimiasoft.minegocio.dto.request.ClienteRequest;
import com.alquimiasoft.minegocio.dto.request.DireccionRequest;
import com.alquimiasoft.minegocio.exception.ClienteDuplicadoException;
import com.alquimiasoft.minegocio.exception.ClienteNotFoundException;
import com.alquimiasoft.minegocio.model.Cliente;
import com.alquimiasoft.minegocio.model.Direccion;
import com.alquimiasoft.minegocio.model.TipoIdentificacion;
import com.alquimiasoft.minegocio.repository.ClienteRepository;
import com.alquimiasoft.minegocio.repository.DireccionRepository;
import com.alquimiasoft.minegocio.service.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final DireccionRepository direccionRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository, DireccionRepository direccionRepository) {
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
    }

    @Override
    public Cliente crearCliente(ClienteRequest request) {
        if (clienteRepository.existsByNumeroIdentificacion(request.getNumeroIdentificacion())) {
            throw new ClienteDuplicadoException("Ya existe un cliente con identificación " + request.getNumeroIdentificacion());
        }
        boolean tieneMatriz = request.getDirecciones()!=null &&
                request.getDirecciones().stream().anyMatch(DireccionRequest::isMatriz);
        if (!tieneMatriz) throw new IllegalArgumentException("Debe tener al menos una dirección matriz");

        Cliente cliente = new Cliente();
        cliente.setTipoIdentificacion(TipoIdentificacion.valueOf(request.getTipoIdentificacion().toUpperCase()));
        cliente.setNumeroIdentificacion(request.getNumeroIdentificacion());
        cliente.setNombres(request.getNombres());
        cliente.setCorreo(request.getCorreo());
        cliente.setCelular(request.getCelular());

        List<Direccion> direcciones = request.getDirecciones().stream().map(dr -> {
            Direccion d = new Direccion();
            d.setProvincia(dr.getProvincia());
            d.setCiudad(dr.getCiudad());
            d.setDireccion(dr.getDireccion());
            d.setMatriz(dr.isMatriz());
            d.setCliente(cliente);
            return d;
        }).collect(Collectors.toList());
        cliente.getDirecciones().addAll(direcciones);

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(Long id, ClienteRequest request) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado " + id));
        if (!c.getNumeroIdentificacion().equals(request.getNumeroIdentificacion())
                && clienteRepository.existsByNumeroIdentificacion(request.getNumeroIdentificacion())) {
            throw new ClienteDuplicadoException("Número de identificación duplicado");
        }
        c.setTipoIdentificacion(TipoIdentificacion.valueOf(request.getTipoIdentificacion().toUpperCase()));
        c.setNumeroIdentificacion(request.getNumeroIdentificacion());
        c.setNombres(request.getNombres());
        c.setCorreo(request.getCorreo());
        c.setCelular(request.getCelular());
        return clienteRepository.save(c);
    }

    @Override
    public void eliminarCliente(Long id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado " + id));
        clienteRepository.delete(c);
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado " + id));
    }

    @Override
    public List<Cliente> buscarClientes(String query) {
        return clienteRepository.findByNombresOrNumeroIdentificacionContaining(query==null?"":query);
    }

    @Override
    public void registrarDireccion(Long clienteId, DireccionRequest dr) {
        Cliente c = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado " + clienteId));
        if (dr.isMatriz() && c.getDirecciones().stream().anyMatch(Direccion::isMatriz)) {
            throw new IllegalArgumentException("Ya existe una dirección matriz");
        }
        Direccion d = new Direccion();
        d.setProvincia(dr.getProvincia());
        d.setCiudad(dr.getCiudad());
        d.setDireccion(dr.getDireccion());
        d.setMatriz(dr.isMatriz());
        d.setCliente(c);
        c.getDirecciones().add(d);
        direccionRepository.save(d);
    }

    @Override
    public List<Direccion> obtenerTodasLasDireccionesDelCliente(Long clienteId) {
        Cliente c = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado " + clienteId));
        return c.getDirecciones();
    }
}
