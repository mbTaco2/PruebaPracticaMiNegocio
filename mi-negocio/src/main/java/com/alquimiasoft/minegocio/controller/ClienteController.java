package com.alquimiasoft.minegocio.controller;

import com.alquimiasoft.minegocio.dto.request.ClienteRequest;
import com.alquimiasoft.minegocio.dto.request.DireccionRequest;
import com.alquimiasoft.minegocio.dto.response.ClienteResponse;
import com.alquimiasoft.minegocio.model.Cliente;
import com.alquimiasoft.minegocio.model.Direccion;
import com.alquimiasoft.minegocio.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) { this.clienteService = clienteService; }

    @GetMapping("/buscar")
    public List<ClienteResponse> buscar(@RequestParam("q") String q) {
        List<Cliente> lista = clienteService.buscarClientes(q);
        return lista.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@RequestBody ClienteRequest request) {
        Cliente c = clienteService.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(c));
    }

    @PutMapping("/{id}")
    public ClienteResponse actualizar(@PathVariable Long id, @RequestBody ClienteRequest request) {
        return toResponse(clienteService.actualizarCliente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{clienteId}/direcciones")
    public ResponseEntity<Void> registrarDireccion(@PathVariable Long clienteId, @RequestBody DireccionRequest req) {
        clienteService.registrarDireccion(clienteId, req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{clienteId}/direcciones")
    public List<ClienteResponse.DireccionResponse> listarDirecciones(@PathVariable Long clienteId) {
        List<Direccion> dirs = clienteService.obtenerTodasLasDireccionesDelCliente(clienteId);
        return dirs.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ---------- mapeos ----------
    private ClienteResponse toResponse(Cliente c) {
        ClienteResponse r = new ClienteResponse();
        r.setId(c.getId());
        r.setTipoIdentificacion(c.getTipoIdentificacion().name());
        r.setNumeroIdentificacion(c.getNumeroIdentificacion());
        r.setNombres(c.getNombres());
        r.setCorreo(c.getCorreo());
        r.setCelular(c.getCelular());

        ClienteResponse.DireccionResponse matriz = null;
        List<ClienteResponse.DireccionResponse> otras = new ArrayList<>();
        for (Direccion d : c.getDirecciones()) {
            ClienteResponse.DireccionResponse dr = toResponse(d);
            if (d.isMatriz()) matriz = dr;
            else otras.add(dr);
        }
        r.setMatriz(matriz);
        r.setOtras(otras);
        return r;
    }

    private ClienteResponse.DireccionResponse toResponse(Direccion d) {
        ClienteResponse.DireccionResponse r = new ClienteResponse.DireccionResponse();
        r.id = d.getId();
        r.provincia = d.getProvincia();
        r.ciudad = d.getCiudad();
        r.direccion = d.getDireccion();
        r.matriz = d.isMatriz();
        return r;
    }
}
