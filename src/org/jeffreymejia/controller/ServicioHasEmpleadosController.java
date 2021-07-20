
package org.jeffreymejia.controller;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.jeffreymejia.bean.Empleado;
import org.jeffreymejia.bean.Servicio;
import org.jeffreymejia.bean.ServiciosHasEmpleados;
import org.jeffreymejia.db.Conexion;
import org.jeffreymejia.system.Principal;

public class ServicioHasEmpleadosController implements Initializable {
    private enum operaciones{NUEVO, GUARDAR , ELIMINAR , EDITAR ,  ACTUALIZAR , CANCELAR ,  NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<ServiciosHasEmpleados>listaServicios_has_Empleados;
    private ObservableList<Servicio>listaServicios;
    private ObservableList<Empleado>listaEmpleados;
    private DatePicker fechahas;
    @FXML private TextField txtCodigo;
    @FXML private TextField txthoraEvento;
    @FXML private TextField txtlugarEvento;
    @FXML private GridPane grpfechaHasEmpleado;
    @FXML private ComboBox cmbcodigoServicio;
    @FXML private ComboBox cmbcodigoEmpleado;
    @FXML private TableView tblServicishasEmpleado;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colCodigoServicios;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colFechaDeSolicitud1;
    @FXML private TableColumn colHoraEvento2;
    @FXML private TableColumn collugardelevento1;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    cargarDatos();   
    fechahas = new DatePicker(Locale.ENGLISH);
    fechahas.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
    fechahas.getCalendarView().todayButtonTextProperty().set("Today");
    fechahas.getCalendarView().setShowWeeks(false);
    grpfechaHasEmpleado.add(fechahas, 0, 0);
    cmbcodigoServicio.setItems(getServicios());
    cmbcodigoEmpleado.setItems(getEmpleados());
    }
    
    public void cargarDatos(){
        tblServicishasEmpleado.setItems(getServicios_has_Empleados());
        colCodigo.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados , Integer>("codigoServicioHasEmpleados"));
        colCodigoServicios.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados , Integer>("codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados , Integer>("codigoEmpleado"));
        colFechaDeSolicitud1.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados , Date>("fechaEvento"));
        colHoraEvento2.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados , String>("horaEvento"));
        collugardelevento1.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados , String>("lugarEvento"));
      
    }
    
    public void seleccionarElemento(){
        txtCodigo.setText(String.valueOf(((ServiciosHasEmpleados)tblServicishasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicioHasEmpleados()));
        cmbcodigoEmpleado.getSelectionModel().select(buscarEmpleados(((ServiciosHasEmpleados)tblServicishasEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        cmbcodigoServicio.getSelectionModel().select(buscarServicios(((ServiciosHasEmpleados)tblServicishasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        fechahas.selectedDateProperty().set(((ServiciosHasEmpleados)tblServicishasEmpleado.getSelectionModel().getSelectedItem()).getFechaEvento());
        txthoraEvento.setText((((ServiciosHasEmpleados)tblServicishasEmpleado.getSelectionModel().getSelectedItem()).getHoraEvento()));
        txtlugarEvento.setText(((ServiciosHasEmpleados)tblServicishasEmpleado.getSelectionModel().getSelectedItem()).getLugarEvento());
        
    }
   
    public ObservableList<ServiciosHasEmpleados>getServicios_has_Empleados(){
        ArrayList<ServiciosHasEmpleados> lista =  new ArrayList<ServiciosHasEmpleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServiciosEmpleados}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                    lista.add(new ServiciosHasEmpleados(resultado.getInt("codigoServicioHasEmpleados"),
                                                          resultado.getDate("fechaEvento"),
                                                          resultado.getString("horaEvento"),
                                                          resultado.getString("lugarEvento"),
                                                          resultado.getInt("codigoServicio"),
                                                          resultado.getInt("codigoEmpleado")
                                        ));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        
 
        return listaServicios_has_Empleados = FXCollections.observableArrayList(lista);
        
    }
    
        public ObservableList<Servicio>getServicios(){
           ArrayList<Servicio> lista = new ArrayList<Servicio>();
          try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicio}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Servicio(resultado.getInt("codigoServicio"),
                                        resultado.getDate("fechaServicio"),
                                        resultado.getString("tipoServicios"),
                                        resultado.getString("horaServicio"),
                                        resultado.getString("lugarServicio"),
                                        resultado.getString("telefonoContacto"),
                                        resultado.getInt("codigoEmpresa")
                
                                        ));
            }
          }catch(Exception e){
                  e.printStackTrace();
          }
          return listaServicios = FXCollections.observableArrayList(lista);
      }
        
        
            public ObservableList<Empleado>getEmpleados(){
         ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_ListarEmpleado}");
             ResultSet resultado  = procedimiento.executeQuery();
               while (resultado.next()){
                   lista.add(new Empleado( resultado.getInt("codigoEmpleado"),
                                        resultado.getString("numeroEmpleado"),
                                        resultado.getString("nombresEmpleado"),
                                        resultado.getString("apellidosEmpleado"),
                                        resultado.getString("direccionempleado"),
                                        resultado.getString("telefonoContacto"),
                                        resultado.getString("gradoCocINero"),
                                        resultado.getInt("codigoTipoEmpleado")  ));
               }
              
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaEmpleados = FXCollections.observableArrayList(lista);
    }
            

    
          public Servicio buscarServicios(int codigoServicio){
            Servicio resultado = null;
            try{
               PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicio(?)}");
               procedimiento.setInt(1, codigoServicio);
               ResultSet registro = procedimiento.executeQuery();
                while(registro.next()){
                    resultado =  new Servicio(registro.getInt("codigoServicio"),
                                        registro.getDate("fechaServicio"),
                                        registro.getString("tipoServicios"),
                                        registro.getString("horaServicio"),
                                        registro.getString("lugarServicio"),
                                        registro.getString("telefonoContacto"),
                                        registro.getInt("codigoEmpresa")
                    
                                                );
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            
            return resultado;
        }
          
       public Empleado buscarEmpleados(int CodigoEmpleado){
        Empleado resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_BuscarEmpleado(?)}");
            procedimiento.setInt(1, CodigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empleado(
                        registro.getInt("codigoEmpleado"),
                        registro.getString("numeroEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getString("direccionempleado"),
                        registro.getString("telefonoContacto"),
                        registro.getString("gradoCocINero"),
                        registro.getInt("codigoTipoEmpleado")
                           
                );
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
            
            public void nuevo (){
                switch(tipoDeOperacion){
                    case NINGUNO:
                        activarControles();
                        limpiarControles();
                        btnNuevo.setText("Guardar");
                        btnEliminar.setDisable(false);
                        btnEliminar.setText("Cancelar");
                        btnEditar.setDisable(true);
                        btnReporte.setDisable(true);
                        tipoDeOperacion = operaciones.GUARDAR;
                        break;
                    case GUARDAR:
                        guardar();
                        desactivarControles();
                        limpiarControles();
                        btnNuevo.setText("Guardar");
                        btnEliminar.setText("Eliminar");
                        btnEliminar.setDisable(false);
                        btnEditar.setDisable(false);
                        btnReporte.setDisable(false);
                        tipoDeOperacion = operaciones.NINGUNO;
                        cargarDatos();
                        break;
                           
                }
            }
            
       public void guardar(){
       
           
         ServiciosHasEmpleados registro = new ServiciosHasEmpleados();
         registro.setCodigoServicio(((Servicio)cmbcodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
         registro.setCodigoEmpleado(((Empleado)cmbcodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
         registro.setFechaEvento(fechahas.getSelectedDate());
         registro.setHoraEvento(txthoraEvento.getText());
         registro.setLugarEvento(txtlugarEvento.getText());
            try{
              PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServiciosEmpleados(?,?,?,?,?)}");
                procedimiento.setInt(1, registro.getCodigoServicio());
                procedimiento.setInt(2, registro.getCodigoEmpleado());
                procedimiento.setDate(3, new java.sql.Date(registro.getFechaEvento().getTime()));
                procedimiento.setString(4, registro.getHoraEvento());
                procedimiento.setString(5, registro.getLugarEvento());
                procedimiento.execute();
                    listaServicios_has_Empleados.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
       }
                 
            
           public void eliminar(){
               switch (tipoDeOperacion){
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
                       if(tblServicishasEmpleado.getSelectionModel().getSelectedItem() !=null){
                           int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere elminar el dato?","Eliminar el dato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if(respuesta == JOptionPane.YES_OPTION){
                                try{
                                  PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServiciosEmpleados(?)}");
                                  procedimiento.setInt(1, (((ServiciosHasEmpleados)tblServicishasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicioHasEmpleados()));
                                  procedimiento.execute();
                                  listaServicios_has_Empleados.remove(tblServicishasEmpleado.getSelectionModel().getFocusedIndex());
                                  limpiarControles();
                                  tblServicishasEmpleado.getSelectionModel().getSelectedItem();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                       }else{
                           JOptionPane.showMessageDialog(null, "Debes de selecciona un dato");
                       }
               }
           } 
            
           public void editar(){
        switch (tipoDeOperacion){
            case NINGUNO:
                if(tblServicishasEmpleado.getSelectionModel().getSelectedItem()!=null){
                    btnEditar.setText("Actuzalizar");
                    btnReporte.setText("Cancerlar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un resgistro para realizar la actualizacion");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
                
        }
    }
    
    public void actualizar(){
     try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServiciosEmpleados(?,?,?,?,?,?)}");
         ServiciosHasEmpleados registro = (ServiciosHasEmpleados)tblServicishasEmpleado.getSelectionModel().getSelectedItem();
         registro.setCodigoServicio(((Servicio)cmbcodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
         registro.setCodigoEmpleado(((Empleado)cmbcodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
         registro.setFechaEvento(fechahas.getSelectedDate());
         registro.setHoraEvento(txthoraEvento.getText());
         registro.setLugarEvento(txtlugarEvento.getText());
                procedimiento.setInt(1, registro.getCodigoServicioHasEmpleados());
                procedimiento.setInt(2, registro.getCodigoServicio());
                procedimiento.setInt(3, registro.getCodigoEmpleado());
                procedimiento.setDate(4, new java.sql.Date(registro.getFechaEvento().getTime()));
                procedimiento.setString(5, registro.getHoraEvento());
                procedimiento.setString(6, registro.getLugarEvento());
                procedimiento.execute();
            
     }catch(Exception e){
         e.printStackTrace();
     }
        
    }
    
    
    public void reporte(){
        switch (tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnReporte.setText("Reporte");
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnNuevo.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    } 
            
            
            
            

            
        public void desactivarControles(){
           txtCodigo.setEditable(false);
           cmbcodigoServicio.setDisable(false);
           cmbcodigoEmpleado.setDisable(false);
           grpfechaHasEmpleado.setDisable(true);
           txthoraEvento.setEditable(false);
           txtlugarEvento.setEditable(false);
        }    
            
        public void activarControles(){
           txtCodigo.setEditable(false);
           cmbcodigoServicio.setDisable(false);
           cmbcodigoEmpleado.setDisable(false);
           grpfechaHasEmpleado.setDisable(false);
           txthoraEvento.setEditable(true);
           txtlugarEvento.setEditable(true);
        }   
        
        public void limpiarControles(){
            txtCodigo.setText("");
            cmbcodigoServicio.getSelectionModel().clearSelection();
            cmbcodigoEmpleado.getSelectionModel().clearSelection();
            txthoraEvento.setText("");
            txtlugarEvento.setText("");
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
