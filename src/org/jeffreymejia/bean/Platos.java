
package org.jeffreymejia.bean;


public class Platos {
    private int codigoPlatos;
    private String cantidad;
    private String nombrePlato;
    private String descripcionPlato;
    private Double precioPlato;
    private int CodigoTipoPlatos;
    
    
    public Platos() {
    
    }

    public Platos(int codigoPlato, String cantidad, String nombrePlato, String descripcionPlato, Double precioPlato, int codigoTipoPlato) {
        this.codigoPlatos = codigoPlatos;
        this.cantidad = cantidad;
        this.nombrePlato = nombrePlato;
        this.descripcionPlato = descripcionPlato;
        this.precioPlato = precioPlato;
        this.CodigoTipoPlatos= codigoTipoPlato;
    }

    public int getCodigoPlato() {
        return codigoPlatos;
    }

    public void setCodigoPlato(int codigoPlato) {
        this.codigoPlatos = codigoPlato;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public String getDescripcionPlato() {
        return descripcionPlato;
    }

    public void setDescripcionPlato(String descripcionPlato) {
        this.descripcionPlato = descripcionPlato;
    }

    public Double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(Double precioPlato) {
        this.precioPlato = precioPlato;
    }

    public int getCodigoTipoPlato() {
        return CodigoTipoPlatos;
    }

    public void setCodigoTipoPlato(int codigoTipoPlato) {
        this.CodigoTipoPlatos = codigoTipoPlato;
      }
    public String toString(){
        return getCodigoPlato()+ "  " + getNombrePlato();
    }
}
    


   
