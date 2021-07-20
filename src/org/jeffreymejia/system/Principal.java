
package org.jeffreymejia.system;



import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import org.jeffreymejia.bean.Productos;
import org.jeffreymejia.controller.DatosPersonalesController;
import org.jeffreymejia.controller.EmpleadosController;
import org.jeffreymejia.controller.EmpresaController;
import org.jeffreymejia.controller.MenuPrincipalController;
import org.jeffreymejia.controller.PlatoController;
import org.jeffreymejia.controller.PresupuestoController;
import org.jeffreymejia.controller.ProductoController;
import org.jeffreymejia.controller.ProductosHasPlatosController;
import org.jeffreymejia.controller.ServicioController;
import org.jeffreymejia.controller.ServicioHasEmpleadosController;
import org.jeffreymejia.controller.ServicioHasPlatosController;
import org.jeffreymejia.controller.TipoEmpleadoController;
import org.jeffreymejia.controller.tipoPlatoController;

public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/jeffreymejia/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal)throws Exception {
        
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony`s Kinal App");
        escenarioPrincipal.getIcons().add(new Image("/org/jeffreymejia/image/LogoKinal.png"));
        /*Parent root = FXMLLoader.load(getClass().getResource("/org/jeffreymejia/view/MenuPrincipalView.fxml"));
        Scene escena = new Scene(root);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();*/
      
        menuPrincipal();
        escenarioPrincipal.show();
        
    
    }
           public void  menuPrincipal(){
              try{
               MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",409,400); 
               menuPrincipal.setEscenarioPrincipal(this);
              }catch(Exception e){
                  e.printStackTrace();
              }      
           }
           public void ventanaProgramador(){
        try {
            DatosPersonalesController datosPersonales = (DatosPersonalesController)cambiarEscena("DatosPersonalesView.fxml",557,372);
            datosPersonales.setEscenarioPrincipal(this);
        }catch (Exception a){
            a.printStackTrace();
        }
    }
           
           
           public void  ventanaEmpresa(){
                try{
               EmpresaController Empresa = (EmpresaController)cambiarEscena("EmpresaView.fxml",1005,784); 
               Empresa.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }      
           }
           
           public void  ventanaPresupuesto(){
                try{
               PresupuestoController Presupuesto = (PresupuestoController)cambiarEscena("presupuestoView.fxml",955,637); 
               Presupuesto.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }      
           }
            public void  ventanaTipoEmpleado(){
                try{
               TipoEmpleadoController TipoEmpleado = (TipoEmpleadoController)cambiarEscena("TipoEmpleadoView.fxml",909,619); 
               TipoEmpleado.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }      
           }
            
             public void  ventanaEmpleado(){
                try{
               EmpleadosController Empleado = (EmpleadosController)cambiarEscena("EmpleadosView.fxml",1124,684); 
               Empleado.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }      
           }
           
              public void  ventanaServicios(){
                try{
               ServicioController Servicio = (ServicioController)cambiarEscena("ServiciosView.fxml",1124,616); 
               Servicio.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }  
              }
                
                 public void  ventanaProducto(){
                try{
               ProductoController Productos = (ProductoController)cambiarEscena("ProductosView.fxml",599,400); 
               Productos.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }
             }
                
             public void ventanaTipoPlato(){
                try{
               tipoPlatoController TipoPlato = (tipoPlatoController)cambiarEscena("TipoPlatoView.fxml",667,434); 
               TipoPlato.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }      
                
                }
                
             public void ventanaPlato(){
                try{
               PlatoController Platos = (PlatoController)cambiarEscena("PlatoView.fxml",868,569); 
               Platos.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }  
     
           }
             
              public void ventanaHasPlato(){
                try{
               ServicioHasPlatosController ServicioHasPlat = (ServicioHasPlatosController)cambiarEscena("ServicioHasPlatosView.fxml",600,400); 
               ServicioHasPlat.setEscenariPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }  
     
           }
           public void ventanaServiciosHasEmpleados(){
                try{
               ServicioHasEmpleadosController ServiciosHasEmpleados = (ServicioHasEmpleadosController)cambiarEscena("ServicioHasEmpleadosView.fxml",600,400); 
               ServiciosHasEmpleados.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();      
           
              }
           }
            public void ventanaProductoHasPlato(){
                try{
               ProductosHasPlatosController ProductosHasPlatos = (ProductosHasPlatosController)cambiarEscena("ProductosHasPlatosView.fxml",600,400); 
               ProductosHasPlatos.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();      
           
              }
            }
           
    
    public static void main(String[] args) {
        launch(args);
    }
   public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
      Initializable resultado=null; 
       FXMLLoader cargadorFXML= new FXMLLoader();
       InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
       cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
       cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
       escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto); 
       escenarioPrincipal.setScene(escena);
       escenarioPrincipal.sizeToScene();
       resultado =(Initializable)cargadorFXML.getController();
        
       
       return resultado;
       
   }

    
}