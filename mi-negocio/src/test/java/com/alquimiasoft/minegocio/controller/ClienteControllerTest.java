package com.alquimiasoft.minegocio.controller;

import com.alquimiasoft.minegocio.dto.request.ClienteRequest;
import com.alquimiasoft.minegocio.dto.request.DireccionRequest;
import com.alquimiasoft.minegocio.model.Cliente;
import com.alquimiasoft.minegocio.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deberiaCrearCliente() throws Exception {
        ClienteRequest request = new ClienteRequest();
        request.setTipoIdentificacion("CEDULA");
        request.setNumeroIdentificacion("1234567890");
        request.setNombres("Juan Pérez");

        DireccionRequest dir = new DireccionRequest();
        dir.setProvincia("Pichincha");
        dir.setCiudad("Quito");
        dir.setDireccion("Av. Amazonas");
        dir.setMatriz(true);
        request.setDirecciones(Collections.singletonList(dir));

        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setId(1L);
        clienteGuardado.setNumeroIdentificacion("1234567890");
        clienteGuardado.setNombres("Juan Pérez");

        when(clienteService.crearCliente(any(ClienteRequest.class))).thenReturn(clienteGuardado);

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombres").value("Juan Pérez"));
    }
}