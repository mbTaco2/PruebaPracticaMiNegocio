package com.alquimiasoft.minegocio.dto.response;

import java.util.List;

public class ClienteResponse {
    private Long id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String correo;
    private String celular;
    private DireccionResponse matriz;
    private List<DireccionResponse> otras;

    // ---- getters/setters ----
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }

    public DireccionResponse getMatriz() {
        return matriz;
    }
    public void setMatriz(DireccionResponse matriz) {
        this.matriz = matriz;
    }

    public List<DireccionResponse> getOtras() {
        return otras;
    }
    public void setOtras(List<DireccionResponse> otras) {
        this.otras = otras;
    }

    // Puedes dejar DireccionResponse con campos públicos o ponerle getters/setters también.
    public static class DireccionResponse {
        public Long id;
        public String provincia;
        public String ciudad;
        public String direccion;
        public boolean matriz;

        // Si prefieres getters/setters:
        // public Long getId() { return id; }
        // public void setId(Long id) { this.id = id; }
        // ... etc.
    }
}
