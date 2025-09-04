// src/main/java/com/alquimiasoft/minegocio/util/TestUtils.java
package com.alquimiasoft.minegocio.util;

import com.alquimiasoft.minegocio.dto.request.ClienteRequest;
import com.alquimiasoft.minegocio.dto.request.DireccionRequest;

import java.util.Collections;

public class TestUtils {

    public static ClienteRequest crearClienteRequest() {
        ClienteRequest request = new ClienteRequest();
        request.setTipoIdentificacion("CEDULA");
        request.setNumeroIdentificacion("1234567890");
        request.setNombres("Juan Pérez");
        request.setCorreo("juan@example.com");
        request.setCelular("0987654321");

        DireccionRequest dir = new DireccionRequest();
        dir.setProvincia("Pichincha");
        dir.setCiudad("Quito");
        dir.setDireccion("Av. Amazonas");
        dir.setMatriz(true);

        request.setDirecciones(Collections.singletonList(dir));
        return request;
    }
}