
package org.jeffreymejia.controller;


import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.jeffreymejia.bean.Empresa;
import org.jeffreymejia.bean.Presupuesto;
import org.jeffreymejia.db.Conexion;
import org.jeffreymejia.report.GenerarReporte;
import org.jeffreymejia.system.Principal;



public class PresupuestoController implements Initializable { 
 private Principal escenarioPrincipal;
 private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
 private operaciones tipoDeOperacion = operaciones.NINGUNO;
 private ObservableList<Empresa> listaEmpresa;
 private DatePicker fecha;
 private ObservableList<Presupuesto> listaPresupuesto;
 
 @FXML private TextField txtCodigoPresupuesto;
 @FXML private TextField txtCantidadPresupuesto;
 @FXML private GridPane grpFechaSolicitud;
 @FXML private ComboBox cmbCodigoEmpresa;
 @FXML private TableView tblPresupuestos;
 @FXML private TableColumn colCodigoPresupuesto;
 @FXML private TableColumn colFechaSolicitud;
 @FXML private TableColumn colCantidadPresupuesto;
 @FXML private TableColumn colCodigoEmpresa;
 @FXML private Button btnNuevo;
 @FXML private Button btnEditar;
 @FXML private Button btnReporte;
 @FXML private Button btnEliminar;
    @Override
    public void initialize(URL location, ResourceBundle rescurces) {
      cargarDatos();
      fecha = new DatePicker(Locale.ENGLISH);
      fecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
      fecha.getCalendarView().todayButtonTextProperty().set("Today");
      fecha.getCalendarView().setShowWeeks(false);
      fecha.getStylesheets().add("/org/jeffreymejia/resource/DatePicker.css");
      grpFechaSolicitud.add(fecha, 0, 0);
     
      
      
      
      cmbCodigoEmpresa.setItems(getEmpresa());
     }
    
    public void cargarDatos (){
        tblPresupuestos.setItems(getPresupuesto());
        colCodigoPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("codigoPresupuesto")); 
        colFechaSolicitud.setCellValueFactory(new PropertyValueFactory<Presupuesto, Date>("fechaSolicitud"));
        colCantidadPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto, Double>("cantidadPresupuesto"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("codigoEmpresas"));

    }
    public void seleccionarElemento(){
      txtCodigoPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoPresupuesto()));
      fecha.selectedDateProperty().set(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getFechaSolicitud());
      txtCantidadPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCantidadPresupuesto()));
      cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoEmpresas()));
        
        
    }
    
    
    
    public ObservableList<Presupuesto>getPresupuesto(){
        ArrayList<Presupuesto> lista = new ArrayList<Presupuesto>();
        try{
            PreparedStatement procedimiento  = Conexion.getInstance().getConexion().prepareCall("{call  sp_ListarPresupuesto}");
            ResultSet resultado = procedimiento.executeQuery();
             while(resultado.next()){
                 lista.add(new Presupuesto(resultado.getInt("codigoPresupuesto"),
                         resultado.getDate("fechaSolicitud"),
                         resultado.getDouble("cantidadPresupuesto"),
                         resultado.getInt("codigoEmpresas")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
     return listaPresupuesto = FXCollections.observableArrayList(lista);
    }
    
  public ObservableList<Empresa> getEmpresa(){
     ArrayList<Empresa> lista = new ArrayList<Empresa>();
     try {
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpresas}");
         ResultSet resultado = procedimiento.executeQuery();
         while(resultado.next()){
             lista.add(new Empresa(resultado.getInt("codigoEmpresas"),
                                    resultado.getString("nombreEmpresa"),
                                    resultado.getString("direccion"),
                                    resultado.getString("telefono")));
         }
     }catch(Exception e){
        e.printStackTrace();
     }
          
        return listaEmpresa = FXCollections.observableArrayList(lista);
    }
  
  public Empresa buscarEmpresa(int codigoEmpresa){
      Empresa resultado = null;
      try{
      
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpresa(?)}");
           procedimiento.setInt(1, codigoEmpresa);
           ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
            resultado = new Empresa(registro.getInt("codigoEmpresas"),
                                    registro.getString("nombreEmpresa"),
                                    registro.getString("direccion"),
                                    registro.getString("telefono"));
           
            };
      }catch(Exception e){
          e.printStackTrace();
      }
      
      return resultado;
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
                cargarDatos();
                break;    
        }
    }
    
  public void guardar(){
      Presupuesto registro = new Presupuesto ();
      registro.setFechaSolicitud(fecha.getSelectedDate());
      registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
      registro.setCodigoEmpresas(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresas());
      try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPresupuesto(?,?,?)}");
          procedimiento.setDate(1, new java.sql.Date(registro.getFechaSolicitud().getTime()));
          procedimiento.setDouble(2,registro.getCantidadPresupuesto());
          procedimiento.setInt(3,registro.getCodigoEmpresas());
          procedimiento.executeQuery();
          listaPresupuesto.add(registro);
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
              if(tblPresupuestos.getSelectionModel().getSelectedItem() !=null){
                        int resultado = JOptionPane.showConfirmDialog(null, "Estas seguro de Eliminar el registro?", "Eliminar Presupuesto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(resultado == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_EliminarPresupuesto(?)}");
                                procedimiento.setInt(1,((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoPresupuesto());        
                                listaPresupuesto.remove(tblPresupuestos.getSelectionModel().getSelectedItem());
                                limpiarControles();
                            }catch(Exception e ){
                                e.printStackTrace();
                                
                            }
                            
                        }
              }else{
                  JOptionPane.showConfirmDialog(null, "Debe Seleccionar un Elemento");
              }
          
      }
  }

  
  public void editar(){
      switch (tipoDeOperacion){
          case NINGUNO:
              if(tblPresupuestos.getSelectionModel().getSelectedItem() !=null){
                  btnEditar.setText("Actualizar");
                  btnReporte.setText("Cancelar");
                  btnNuevo.setDisable(true);
                  btnEliminar.setDisable(true);
                  activarControles();
                  tipoDeOperacion = operaciones.ACTUALIZAR;
              }else{
                  JOptionPane.showMessageDialog(null, "Debe Seleccionar un Elemento");
              }
          break;
          case ACTUALIZAR:
              actualizar();
              btnEditar.setText("Editar");
              btnReporte.setText("Repote");
              btnEliminar.setDisable(false);
              btnNuevo.setDisable(true);
              tipoDeOperacion = operaciones.NINGUNO;
              cargarDatos();
              break; 
      }
  }
  
  
  public void actualizar(){
      try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPresupuesto(?,?)}");
          Presupuesto registro = (Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem();
          registro.setFechaSolicitud(fecha.getSelectedDate());
          registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
          procedimiento.setDate(1, new java.sql.Date(registro.getFechaSolicitud().getTime()));
          procedimiento.setDouble(2,registro.getCantidadPresupuesto());
      }catch(Exception e ){
          e.printStackTrace();
      }
  }
  
  public void generarReporte (){
        switch(tipoDeOperacion){
            case NINGUNO:
                imprimirReporte();
            break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnEditar.setDisable(false);
                btnReporte.setText("Reporte");
                btnReporte.setDisable(false);
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            
                        
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        int codEmpresa = Integer.valueOf(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresas());
          parametros.put("codEmpresa",codEmpresa);
        GenerarReporte.mostrarReporte("ReportePresupuesto.jasper","ReportePresupuesto",parametros);                 
    }
  

  public void desactivarControles(){
      txtCodigoPresupuesto.setEditable(false);
      txtCantidadPresupuesto.setEditable(false);
      grpFechaSolicitud.setDisable(false);
      cmbCodigoEmpresa.setEditable(false);
  }
      public void activarControles(){
       txtCodigoPresupuesto.setEditable(false);
      txtCantidadPresupuesto.setEditable(true);
      grpFechaSolicitud.setDisable(false);
      cmbCodigoEmpresa.setEditable(false);  
      }
      
     public void limpiarControles(){
         txtCodigoPresupuesto.setText("");
      txtCantidadPresupuesto.setText("");
      cmbCodigoEmpresa.getSelectionModel().clearSelection();
}
      
      
      
    
       public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaEmpresa (){
       escenarioPrincipal.ventanaEmpresa();
  
   }
}