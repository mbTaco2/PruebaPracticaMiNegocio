// src/main/java/com/alquimiasoft/minnegocio/dto/DireccionDTO.java
package com.alquimiasoft.minegocio.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {
    private Long id;
    private String provincia;
    private String ciudad;
    private String direccion;
    private boolean esMatriz;
}