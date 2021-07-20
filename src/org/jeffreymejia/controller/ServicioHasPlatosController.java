
package org.jeffreymejia.controller;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jeffreymejia.bean.Platos;
import org.jeffreymejia.bean.Servicio;
import org.jeffreymejia.bean.ServicioHasPlat;
import org.jeffreymejia.db.Conexion;
import org.jeffreymejia.system.Principal;

public class ServicioHasPlatosController implements Initializable {
   private Principal escenariPrincipal;
    private  ObservableList <ServicioHasPlat> listarSerhasPla;
    private  ObservableList <Servicio> listarServicio;
    private  ObservableList <Platos> listarPlatos;
    @FXML private ComboBox  cmbCodigoServico;
    @FXML private ComboBox cmbcodigoPlato;
    @FXML private TableView tblServicioshasPlatos;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colcodigoPlatos;
    
    
    @Override
    public void initialize(URL location, ResourceBundle rusources) {
        cargarDatos();
        cmbCodigoServico.setItems(getServicio());
        cmbcodigoPlato.setItems(getPlatos());
    }
    
    public void cargarDatos(){
        tblServicioshasPlatos.setItems(getServiciosHasPlatos());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioHasPlat,Integer>("codigoServicio"));
        colcodigoPlatos.setCellValueFactory(new PropertyValueFactory<ServicioHasPlat,Integer>("codigoPlatos"));
    }
    
    public void seleccionarElemento(){
        cmbCodigoServico.getSelectionModel().select(buscarServicio(((ServicioHasPlat)tblServicioshasPlatos.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbcodigoPlato.getSelectionModel().select(buscarPlatos(((ServicioHasPlat)tblServicioshasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlatos()));
    }

    
    
    public ObservableList <ServicioHasPlat> getServiciosHasPlatos(){
        ArrayList<ServicioHasPlat> lista = new ArrayList<ServicioHasPlat>();
        try{
           PreparedStatement   procedimietno = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicioplatos}");
           ResultSet resultado = procedimietno.executeQuery();
           while(resultado.next()){
                   lista.add(new ServicioHasPlat(resultado.getInt("codigoServicio"),
                                                    resultado.getInt("codigoPlatos")));
               
           }
            
        }catch(Exception e){
          e.printStackTrace();
        }
        return listarSerhasPla = FXCollections.observableArrayList(lista);
    }
    
    
    
    public ObservableList <Servicio> getServicio(){
        ArrayList<Servicio> lista = new ArrayList<Servicio>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarServicio}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicio(resultado.getInt("codigoServicio"),
                                       resultado.getDate("fechaServicio"),
                                       resultado.getString("tipoServicio"),
                                       resultado.getString("horaServicio"),
                                       resultado.getString("lugarServicio"),
                                       resultado.getString("telefonoContacto"),
                                       resultado.getInt("codigoEmpresas")));
            }
        }catch(Exception e ){
        e.printStackTrace();
        } 
        return listarServicio = FXCollections.observableArrayList(lista);
    }
    
      
      
      public Servicio buscarServicio(int codigoServicio){
         Servicio resultado = null;
          try{
           PreparedStatement procedimiento  = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicio(?)}");
           procedimiento.setInt(1, codigoServicio);
           ResultSet registro = procedimiento.executeQuery();   
           while(registro.next()){
             resultado = new Servicio(registro.getInt("codigoServicio"),
                                          registro.getDate("fechaServicio"),
                                          registro.getString("tipoServicio"),
                                          registro.getString("horaServicio"),
                                          registro.getString("lugarServicio"),
                                          registro.getString("telefonoContacto"),
                                          registro.getInt("codigoEmpresas")
                                      );
           }                          
      }catch(Exception e){
          e.printStackTrace();
      }
             return resultado;

      }
   
      
         
    public ObservableList<Platos>getPlatos(){
        ArrayList<Platos> lista = new ArrayList<Platos>();
        try{
            PreparedStatement procedimiento =  Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlato}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Platos(resultado.getInt("codigoPlatos"),
                                resultado.getString("cantidad"),
                                resultado.getString("nombrePlato"),
                                resultado.getString("descripcionPlato"),
                                resultado.getDouble("precioPlato"),
                                resultado.getInt("CodigoTipoPlatos")));
                
            }
        }catch(Exception e ){
            e.printStackTrace();
        }
        return  listarPlatos = FXCollections.observableArrayList(lista);
    }
    
    
    
    
      public Platos buscarPlatos(int codigoPlatos){
         Platos resultado = null;
          try{
           PreparedStatement procedimiento  = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlato(?)}");
           procedimiento.setInt(1,codigoPlatos);
           ResultSet registro = procedimiento.executeQuery();   
           while(registro.next()){
                resultado = new Platos(registro.getInt("codigoPlatos"),
                                       registro.getString("cantidad"),
                                       registro.getString("nombrePlato"),
                                       registro.getString("descripcionPlato"),
                                       registro.getDouble("precioPlato"),
                                       registro.getInt("CodigoTipoPlatos"));
           }                          
      }catch(Exception e){
          e.printStackTrace();
      }
           return resultado;
      }
       
    
    
      
    public Principal getEscenariPrincipal() {
        return escenariPrincipal;
    }

    public void setEscenariPrincipal(Principal escenariPrincipal) {
        this.escenariPrincipal = escenariPrincipal;
    }
    
     public void menuprincipal(){
        escenariPrincipal.menuPrincipal();
       
    }

    
    }
      

    

