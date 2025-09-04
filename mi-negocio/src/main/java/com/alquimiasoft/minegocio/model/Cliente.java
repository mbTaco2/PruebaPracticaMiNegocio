// src/main/java/com/alquimiasoft/minegocio/model/Cliente.java
package com.alquimiasoft.minegocio.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "clientes",
    uniqueConstraints = { @UniqueConstraint(columnNames = "numeroIdentificacion") }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoIdentificacion tipoIdentificacion;

    @NotBlank
    @Size(max = 13)
    @Column(nullable = false, length = 13, updatable = false)
    private String numeroIdentificacion;

    @NotBlank
    @Size(max = 150)
    private String nombres;

    @Email
    @Size(max = 150)
    private String correo;

    @Size(max = 20)
    private String celular;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Direccion> direcciones = new ArrayList<>();

    public void addDireccion(Direccion direccion) {
        if (direccion == null) return;
        direccion.setCliente(this);
        this.direcciones.add(direccion);
    }

    public void removeDireccion(Direccion direccion) {
        if (direccion == null) return;
        this.direcciones.remove(direccion);
        direccion.setCliente(null);
    }
}
