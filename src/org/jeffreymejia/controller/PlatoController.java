
package org.jeffreymejia.controller;

import org.jeffreymejia.system.Principal;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.jeffreymejia.bean.Platos;
import org.jeffreymejia.bean.TipoPlato;
import org.jeffreymejia.db.Conexion;
import org.jeffreymejia.system.Principal;

public class PlatoController implements Initializable {

   private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
 private operaciones tipoDeOperacion = operaciones.NINGUNO;
 private ObservableList<Platos> listarPlatos;
 private ObservableList<TipoPlato> listarTipoPlato;
 @FXML private TextField txtcodigoPlatos;
 @FXML private TextField txtcantidad;
 @FXML private TextField txtnombrePlato;
 @FXML private TextField txtdescripcionPlato;
 @FXML private TextField txtprecioPlato;
 @FXML private ComboBox cmbCodigoTipoPlatos;
 @FXML private TableView tblPlatos;
 @FXML private TableColumn  colcodigoPlatos;
 @FXML private TableColumn colcantidad;
 @FXML private TableColumn colnombrePlato;
 @FXML private TableColumn coldescripcionPlato;
 @FXML private TableColumn colprecioPlato;
 @FXML private TableColumn colCodigoTipoPlatos;
 @FXML private Button btnNuevo;
 @FXML private Button btnEditar;
 @FXML private Button btnReporte;
 @FXML private Button btnEliminar;
 
 @Override
    public void initialize(URL location, ResourceBundle rusources) {
       
    }
    public void cargardatos(){
    tblPlatos.setItems(getPlatos());
    colcodigoPlatos.setCellValueFactory(new PropertyValueFactory<Platos,Integer>("codigoPlatos"));
    colcantidad.setCellValueFactory(new PropertyValueFactory<Platos,String>("cantidad"));
    colnombrePlato.setCellValueFactory(new PropertyValueFactory<Platos,String>("nombrePlato"));
    coldescripcionPlato.setCellValueFactory(new PropertyValueFactory<Platos,String>("descripcionPlato"));
    colprecioPlato.setCellValueFactory(new PropertyValueFactory<Platos,Double>("precioPlato"));
    colCodigoTipoPlatos.setCellValueFactory(new PropertyValueFactory<Platos,Integer>("CodigoTipoPlatos"));
}
     public void seleccionarElemento(){
       txtcodigoPlatos.setText(String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
       txtcantidad.setText(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCantidad());
       txtnombrePlato.setText(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getNombrePlato());
       txtdescripcionPlato.setText(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getDescripcionPlato());
       txtprecioPlato.setText(String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getPrecioPlato()));
       cmbCodigoTipoPlatos.getSelectionModel().select(buscarTipoPlato(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
}
     
public TipoPlato buscarTipoPlato(int CodigoTipoPlatos){
       TipoPlato resultado = null;
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlato(?)}");
           procedimiento.setInt(1, CodigoTipoPlatos);
           ResultSet registro = procedimiento.executeQuery();
           while(registro.next()){
               resultado = new TipoPlato(registro.getInt("CodigoTipoPlatos"),
                                         registro.getString("DescripcionTipoPlatos"));
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       return resultado;
           
       }



    public ObservableList<Platos>getPlatos(){
        ArrayList<Platos> lista = new ArrayList<Platos>();
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlato}");
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
    
     public void nuevo(){
        switch (tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.NINGUNO;
                cargardatos();
                break;    
        }
    }
     public void guardar(){
       Platos registro = new Platos();
       registro.setCantidad(txtcantidad.getText());
       registro.setNombrePlato(txtnombrePlato.getText());
       registro.setDescripcionPlato(txtdescripcionPlato.getText());
       registro.setPrecioPlato(Double.parseDouble(txtprecioPlato.getText()));
       /*registro.setCodigoTipoPlatos(((TipoPlato)cmbCodigoTipoPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlatos());*/
     try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPlato(?, ?, ?, ?, ?)}");
         procedimiento.setString(1, registro.getCantidad());
         procedimiento.setString(2, registro.getNombrePlato());
         procedimiento.setString(3, registro.getDescripcionPlato());
         procedimiento.setDouble(4, registro.getPrecioPlato());
         procedimiento.setInt(5, registro.getCodigoTipoPlato());
     }catch(Exception e){
         e.printStackTrace();
     }
     
     }
      public void eliminar(){
       switch(tipoDeOperacion){
           case GUARDAR:
               desactivarControles();
               limpiarControles();
               btnNuevo.setText("Nuevo");
               btnNuevo.setDisable(false);
               btnEliminar.setText("Eliminar");
               btnEliminar.setDisable(false);
               btnEditar.setDisable(false);
               btnReporte.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
           default:
               if (tblPlatos.getSelectionModel().getSelectedItem()!=null){
                    int resultado = JOptionPane.showConfirmDialog(null,"Estas segura de Eliminar el registro?", "Eliminar Empresa",JOptionPane. YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(resultado == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
                            procedimiento.setInt(1,((TipoPlato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlatos());
                            procedimiento.execute();
                            listarPlatos.remove(tblPlatos.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
               }else{
                   JOptionPane.showMessageDialog(null,"Debe Seleccionar un Elemento");
               }
        }
   }
     
    
     
      public void desactivarControles(){
      txtcodigoPlatos.setEditable(false);
      txtcantidad.setEditable(false);
      txtnombrePlato.setDisable(false);
      txtdescripcionPlato.setDisable(false);
      txtprecioPlato.setDisable(false);
      cmbCodigoTipoPlatos.setDisable(false);
      }

  
  public void activarControles(){
      txtcodigoPlatos.setDisable(false);
      txtcantidad.setEditable(true);
      txtnombrePlato.setEditable(true);
      txtdescripcionPlato.setEditable(true);
      txtprecioPlato.setEditable(true);
      cmbCodigoTipoPlatos.setDisable(false);
  }
  
  public void limpiarControles(){
      txtcodigoPlatos.setText("");
      txtcantidad.setText("");
      txtnombrePlato.setText("");
      txtdescripcionPlato.setText("");
      txtprecioPlato.setText("");
      cmbCodigoTipoPlatos.getSelectionModel().clearSelection();
  }  
    
  public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    
   }
    
    public void  setEscenarioPrincipal (Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
        
    }
     
     public void menuprincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
     public void ventanaTipoPlato(){
        escenarioPrincipal.ventanaTipoPlato();
     }
}
