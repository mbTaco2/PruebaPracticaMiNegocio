// src/main/java/com/alquimiasoft/minnegocio/controller/ClienteController.java
package com.alquimiasoft.minegocio.controller;

import com.alquimiasoft.minegocio.dto.ClienteDTO;
import com.alquimiasoft.minegocio.dto.DireccionDTO;
import com.alquimiasoft.minegocio.service.ClienteService;
import com.alquimiasoft.minegocio.service.exception.ClienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscarClientes(@RequestParam String query) {
        List<ClienteDTO> clientes = clienteService.buscarClientes(query);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO cliente = clienteService.crearCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO cliente = clienteService.actualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/direcciones")
    public ResponseEntity<ClienteDTO> agregarDireccion(@PathVariable Long id, @RequestBody DireccionDTO direccionDTO) {
        ClienteDTO cliente = clienteService.agregarDireccion(id, direccionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping("/{id}/direcciones")
    public ResponseEntity<List<DireccionDTO>> listarDirecciones(@PathVariable Long id) {
        List<DireccionDTO> direcciones = clienteService.listarDirecciones(id);
        return ResponseEntity.ok(direcciones);
    }

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<String> handleClienteException(ClienteException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}