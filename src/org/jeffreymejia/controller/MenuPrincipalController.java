
package org.jeffreymejia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.jeffreymejia.system.Principal;


public class MenuPrincipalController implements Initializable{
  private Principal escenarioPrincipal ;

  
  @Override 
    public void initialize(URL location, ResourceBundle rusources) {
  
    }
  
    public Principal getEscenarioPrincipal (){
        return escenarioPrincipal;
    
   }
    
    public void  setEscenarioPrincipal (Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
        
    }
   
    public void  ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador(); 
    }

   public void ventanaEmpresa (){
       escenarioPrincipal.ventanaEmpresa();

  
   }
 public void ventanaPresupuesto (){
       escenarioPrincipal.ventanaPresupuesto();   
         
}
 
 public void ventanaTipoEmpleado (){
       escenarioPrincipal.ventanaTipoEmpleado();
       
 }
 
 public void ventanaEmpleado (){
    escenarioPrincipal.ventanaEmpleado();  
 
 }
 public void ventanaProducto(){
    escenarioPrincipal.ventanaProducto();
 
}
 public void ventanaTipoPlato(){
    escenarioPrincipal.ventanaTipoPlato();
}
 public void ventanaPlato(){
    escenarioPrincipal.ventanaPlato();
 }
 
 public void ventanaHasPlato(){
    escenarioPrincipal.ventanaHasPlato();
 }
 public void ventanaServiciosHasEmpleados(){
    escenarioPrincipal.ventanaServiciosHasEmpleados();
 }
 public void ventanaProductoHasPlato(){
    escenarioPrincipal.ventanaProductoHasPlato();
 }
}

