
package org.jeffreymejia.bean;

import org.jeffreymejia.system.Principal;


public class Productos {

    public static void setEscenarioPrincipal(Principal aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     private int codigoProducto;
    private String nombreProducto;
    private String cantidad;
            
    public Productos() {
    }

    public Productos(int codigoProducto, String nombreProducto, String cantidad) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    
    public String toString(){
        return getCodigoProducto()+ "  " +  getNombreProducto();
    }
    
}


