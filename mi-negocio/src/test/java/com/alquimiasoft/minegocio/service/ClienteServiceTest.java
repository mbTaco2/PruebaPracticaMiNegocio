// src/test/java/com/alquimiasoft/minnegocio/service/ClienteServiceTest.java
package com.alquimiasoft.minegocio.service;

import com.alquimiasoft.minegocio.dto.ClienteDTO;
import com.alquimiasoft.minegocio.dto.DireccionDTO;
import com.alquimiasoft.minegocio.model.Cliente;
import com.alquimiasoft.minegocio.model.Direccion;
import com.alquimiasoft.minegocio.model.TipoIdentificacion;
import com.alquimiasoft.minegocio.repository.ClienteRepository;
import com.alquimiasoft.minegocio.service.exception.ClienteException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void debeCrearClienteConDireccionMatriz() {
        DireccionDTO direccion = new DireccionDTO(null, "Pichincha", "Quito", "Av. Amazonas", true);
        ClienteDTO clienteDTO = new ClienteDTO(null, TipoIdentificacion.CEDULA, "1712345678", "Ana López", "ana@email.com", "0987654321", Arrays.asList(direccion));

        when(clienteRepository.findByNumeroIdentificacion("1712345678")).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(i -> {
            Cliente c = i.getArgument(0);
            c.setId(1L);
            c.getDirecciones().forEach(d -> d.setId(1L));
            return c;
        });

        ClienteDTO resultado = clienteService.crearCliente(clienteDTO);

        assertNotNull(resultado.getId());
        assertEquals("Ana López", resultado.getNombres());
        assertTrue(resultado.getDirecciones().get(0).isEsMatriz());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void debeLanzarErrorSiYaExisteCliente() {
        ClienteDTO clienteDTO = new ClienteDTO(null, TipoIdentificacion.CEDULA, "1712345678", "Juan", "juan@email.com", "099", Arrays.asList(new DireccionDTO(null, "P", "Q", "A", true)));

        when(clienteRepository.findByNumeroIdentificacion("1712345678")).thenReturn(Optional.of(new Cliente()));

        ClienteException exception = assertThrows(
        ClienteException.class,
        () -> clienteService.crearCliente(clienteDTO)
    );

    // Opcional: Verifica el mensaje
    assertEquals("Ya existe un cliente con ese número de identificación", exception.getMessage());
    }
}