package com.develop.appprotov1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Cuestionario {
    private int id;
    private String fechaInspeccion;
    private String propietario;
    private String idOrganico;
    private String comunidad;
    private String municipio;
    private String estado;
    private List<Apiario> apiarios;
    private String estatusProductor;
    private String pre21;
    private String pre22;
    private String pre23;
    private String pre24;
    private String pre25;
    private String pre26;
    private String pre27;
    private String pre28;
    private String pre31;
    private String pre32;
    private String pre33;
    private String pre34;
    private String pre35;
    private String pre36;
    private String pre37;
    private String pre38;
    private String pre41;
    private String pre51;
    private String pre52;
    private String pre61;
    private String pre62;
    private String pre63;
    private String pre64;
    private String pre65;
    private String pre66;
    private String observaciones;
    private String pre81;
    private String productor;
    private String latitud;
    private String longitud;

    public Cuestionario(int id, String fechaInspeccion, String propietario, String idOrganico, String comunidad, String municipio, String estado, List<Apiario> apiarios, String estatusProductor, String pre21, String pre22, String pre23, String pre24, String pre25, String pre26, String pre27, String pre28, String pre31, String pre32, String pre33, String pre34, String pre35, String pre36, String pre37, String pre38, String pre41, String pre51, String pre52, String pre61, String pre62, String pre63, String pre64, String pre65, String pre66, String observaciones, String pre81, String productor, String latitud, String longitud) {
        this.id = id;
        this.fechaInspeccion = fechaInspeccion;
        this.propietario = propietario;
        this.idOrganico = idOrganico;
        this.comunidad = comunidad;
        this.municipio = municipio;
        this.estado = estado;
        this.apiarios = apiarios;
        this.estatusProductor = estatusProductor;
        this.pre21 = pre21;
        this.pre22 = pre22;
        this.pre23 = pre23;
        this.pre24 = pre24;
        this.pre25 = pre25;
        this.pre26 = pre26;
        this.pre27 = pre27;
        this.pre28 = pre28;
        this.pre31 = pre31;
        this.pre32 = pre32;
        this.pre33 = pre33;
        this.pre34 = pre34;
        this.pre35 = pre35;
        this.pre36 = pre36;
        this.pre37 = pre37;
        this.pre38 = pre38;
        this.pre41 = pre41;
        this.pre51 = pre51;
        this.pre52 = pre52;
        this.pre61 = pre61;
        this.pre62 = pre62;
        this.pre63 = pre63;
        this.pre64 = pre64;
        this.pre65 = pre65;
        this.pre66 = pre66;
        this.observaciones = observaciones;
        this.pre81 = pre81;
        this.productor = productor;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(String fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getIdOrganico() {
        return idOrganico;
    }

    public void setIdOrganico(String idOrganico) {
        this.idOrganico = idOrganico;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Apiario> getApiarios() {
        return apiarios;
    }

    public void setApiarios(List<Apiario> apiarios) {
        this.apiarios = apiarios;
    }

    public String getEstatusProductor() {
        return estatusProductor;
    }

    public void setEstatusProductor(String estatusProductor) {
        this.estatusProductor = estatusProductor;
    }

    public String getPre21() {
        return pre21;
    }

    public void setPre21(String pre21) {
        this.pre21 = pre21;
    }

    public String getPre22() {
        return pre22;
    }

    public void setPre22(String pre22) {
        this.pre22 = pre22;
    }

    public String getPre23() {
        return pre23;
    }

    public void setPre23(String pre23) {
        this.pre23 = pre23;
    }

    public String getPre24() {
        return pre24;
    }

    public void setPre24(String pre24) {
        this.pre24 = pre24;
    }

    public String getPre25() {
        return pre25;
    }

    public void setPre25(String pre25) {
        this.pre25 = pre25;
    }

    public String getPre26() {
        return pre26;
    }

    public void setPre26(String pre26) {
        this.pre26 = pre26;
    }

    public String getPre27() {
        return pre27;
    }

    public void setPre27(String pre27) {
        this.pre27 = pre27;
    }

    public String getPre28() {
        return pre28;
    }

    public void setPre28(String pre28) {
        this.pre28 = pre28;
    }

    public String getPre31() {
        return pre31;
    }

    public void setPre31(String pre31) {
        this.pre31 = pre31;
    }

    public String getPre32() {
        return pre32;
    }

    public void setPre32(String pre32) {
        this.pre32 = pre32;
    }

    public String getPre33() {
        return pre33;
    }

    public void setPre33(String pre33) {
        this.pre33 = pre33;
    }

    public String getPre34() {
        return pre34;
    }

    public void setPre34(String pre34) {
        this.pre34 = pre34;
    }

    public String getPre35() {
        return pre35;
    }

    public void setPre35(String pre35) {
        this.pre35 = pre35;
    }

    public String getPre36() {
        return pre36;
    }

    public void setPre36(String pre36) {
        this.pre36 = pre36;
    }

    public String getPre37() {
        return pre37;
    }

    public void setPre37(String pre37) {
        this.pre37 = pre37;
    }

    public String getPre38() {
        return pre38;
    }

    public void setPre38(String pre38) {
        this.pre38 = pre38;
    }

    public String getPre41() {
        return pre41;
    }

    public void setPre41(String pre41) {
        this.pre41 = pre41;
    }

    public String getPre51() {
        return pre51;
    }

    public void setPre51(String pre51) {
        this.pre51 = pre51;
    }

    public String getPre52() {
        return pre52;
    }

    public void setPre52(String pre52) {
        this.pre52 = pre52;
    }

    public String getPre61() {
        return pre61;
    }

    public void setPre61(String pre61) {
        this.pre61 = pre61;
    }

    public String getPre62() {
        return pre62;
    }

    public void setPre62(String pre62) {
        this.pre62 = pre62;
    }

    public String getPre63() {
        return pre63;
    }

    public void setPre63(String pre63) {
        this.pre63 = pre63;
    }

    public String getPre64() {
        return pre64;
    }

    public void setPre64(String pre64) {
        this.pre64 = pre64;
    }

    public String getPre65() {
        return pre65;
    }

    public void setPre65(String pre65) {
        this.pre65 = pre65;
    }

    public String getPre66() {
        return pre66;
    }

    public void setPre66(String pre66) {
        this.pre66 = pre66;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPre81() {
        return pre81;
    }

    public void setPre81(String pre81) {
        this.pre81 = pre81;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
