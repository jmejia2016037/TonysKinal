
package org.jeffreymejia.bean;


import java.util.Date;


public class Servicio {
    private int codigoServicio;
    private Date fechaServicio;
    private String TipoServicio;
    private String horaServicio;
    private String lugarServicio;
    private String telefonoContacto;
    private int codigoEmpresas;
    
    public Servicio() {
        
    }

    public Servicio(int codigoServicio, Date fechaServicio, String TipoServicio, String horaServicio, String lugarServicio, String telefonoContacto, int codigoEmpresas) {
        this.codigoServicio = codigoServicio;
        this.fechaServicio = fechaServicio;
        this.TipoServicio = TipoServicio;
        this.horaServicio = horaServicio;
        this.lugarServicio = lugarServicio;
        this.telefonoContacto = telefonoContacto;
        this.codigoEmpresas = codigoEmpresas;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getTipoServicio() {
        return TipoServicio;
    }

    public void setTipoServicio(String TipoServicio) {
        this.TipoServicio = TipoServicio;
    }

    public String getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(String horaServicio) {
        this.horaServicio = horaServicio;
    }

    public String getLugarServicio() {
        return lugarServicio;
    }

    public void setLugarServicio(String lugarServicio) {
        this.lugarServicio = lugarServicio;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public int getCodigoEmpresas() {
        return codigoEmpresas;
    }

    public void setCodigoEmpresas(int codigoEmpresas) {
        this.codigoEmpresas = codigoEmpresas;
    }
 public String toString(){
       return getCodigoEmpresas() + " | " + getTipoServicio();
   }
   
}
