// src/main/java/com/alquimiasoft/minnegocio/dto/ClienteDTO.java
package com.alquimiasoft.minegocio.dto;

import com.alquimiasoft.minegocio.model.TipoIdentificacion;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private TipoIdentificacion tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String correo;
    private String celular;
    private List<DireccionDTO> direcciones;
}