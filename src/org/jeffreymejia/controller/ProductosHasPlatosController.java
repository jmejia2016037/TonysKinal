
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
import org.jeffreymejia.bean.Productos;
import org.jeffreymejia.bean.ProductosHasPlatos;
import org.jeffreymejia.db.Conexion;
import org.jeffreymejia.system.Principal;



public class ProductosHasPlatosController 
  implements Initializable{
     private Principal escenarioPrincipal; 
     private ObservableList<ProductosHasPlatos> listaProductosHasPlatos;
     private ObservableList<Productos>ListaProductos;
     private ObservableList<Platos> listaPlatos;
     @FXML private TableView tblProductosHasplatos;
     @FXML private TableColumn colProductosCodigoProducto;
     @FXML private TableColumn colPlatosCodigoPlato;
     @FXML private ComboBox cmbProductoCodigoProducto;
     @FXML private ComboBox cmbPlatoCodigoPlato;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        this.cmbPlatoCodigoPlato.setItems(getPlato());
        this.cmbProductoCodigoProducto.setItems(getProductos());
     
    }
    
    
     public void cargarDatos(){
        tblProductosHasplatos.setItems(getProductos_has_Platos());
        colProductosCodigoProducto.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos, Integer>("codigoProducto"));
        colPlatosCodigoPlato.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos, Integer>("codigoPlato"));
    }
    
    public ObservableList<ProductosHasPlatos> getProductos_has_Platos(){
        ArrayList<ProductosHasPlatos> lista = new ArrayList<ProductosHasPlatos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductosHasPlatos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ProductosHasPlatos(resultado.getInt("codigoProducto"), resultado.getInt("codigoPlatos")));
                
                        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductosHasPlatos = FXCollections.observableArrayList(lista);
    }
    public ObservableList<Productos> getProductos(){
           ArrayList<Productos> lista = new ArrayList<Productos>();
                try{
                    PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos}");
                    ResultSet resultado = procedimiento.executeQuery();
              while(resultado.next()){
                      lista.add(new Productos (resultado.getInt("codigoProducto"),
                                        resultado.getString("nombreProducto"),
                                        resultado.getString("cantidad")));
              }
                }catch(Exception e){
                    e.printStackTrace();
                }
                return ListaProductos = FXCollections.observableArrayList(lista);
    }
   public ObservableList<Platos> getPlato(){
        ArrayList<Platos> lista = new ArrayList<Platos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlato}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Platos(resultado.getInt("codigoPlatos"), 
                                    resultado.getString("cantidad"), 
                                    resultado.getString("nombrePlato"), 
                                    resultado.getString("descripcionPlato"), 
                                    resultado.getDouble("precioPlato"), 
                                    resultado.getInt("codigoTipoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPlatos = FXCollections.observableArrayList(lista);
    }
    //buscar de las entidades 
    public Productos buscarProductos(int codigoProducto){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
            procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
                while(registro.next()){
                    resultado = new Productos(
                            registro.getInt("codigoProducto"),
                            registro.getString("nombreProducto"),
                            registro.getString("cantidad"));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    public Platos buscarPlato(int codigoPlato){
        Platos resultado = null;
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlato(?)}");
                procedimiento.setInt(1, codigoPlato);
                ResultSet registro = procedimiento.executeQuery();
                while(registro.next()){
                    resultado = new Platos(
                    registro.getInt("codigoPlatos"),
                    registro.getString("cantidad"),
                    registro.getString("nombrePlato"),
                    registro.getString("descripcionPlato"),
                    registro.getDouble("precioPlato"),
                    registro.getInt("codigoTipoPlato"));
                }
                    
                        }catch(Exception e){
                e.printStackTrace();
            }
            
            return resultado;
    }
    public void seleccionarElemento(){
        cmbProductoCodigoProducto.getSelectionModel().select(buscarProductos(((ProductosHasPlatos)tblProductosHasplatos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        cmbPlatoCodigoPlato.getSelectionModel().select(buscarPlato(((ProductosHasPlatos)tblProductosHasplatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
    }
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
     public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    } 
    
}

    
    

