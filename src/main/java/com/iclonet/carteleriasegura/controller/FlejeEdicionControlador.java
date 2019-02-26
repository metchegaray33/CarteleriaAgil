/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.controller;

import com.iclonet.carteleriasegura.entidades.Fleje;
import com.iclonet.carteleriasegura.util.CONSTANTES;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rodolfo Durante
 */
public class FlejeEdicionControlador extends EdicionControladorFlejes {

    @FXML
    private TextField fld_sap;
    
    @FXML
    private TextField fld_fecha;
     
    @FXML
    private TextField fld_precio_actual;
    @FXML
    private TextField fld_precio_antes;
    @FXML
    private ComboBox<String> cmb_origen;
    @FXML
    private ComboBox<String> cmb_modelo;

    @FXML
    private TextArea txt_descripcion;

    @FXML
    private Button btn_ok;
    @FXML
    private Button btn_cancel;

    private Fleje fleje;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_descripcion.setTooltip(new Tooltip("Descripcion producto"));
        cmb_origen.setTooltip(new Tooltip("Origen"));
        cmb_modelo.setTooltip(new Tooltip("Tipo de cartel"));
        fld_precio_actual.setTooltip(new Tooltip("Precio actual"));
        fld_precio_antes.setTooltip(new Tooltip("Precio  antes"));
        fld_sap.setTooltip(new Tooltip("Sap"));
        fld_fecha.setTooltip(new Tooltip("Fecha"));
        agregarCampo(txt_descripcion);
        agregarCampo(cmb_origen);
        agregarCampo(cmb_modelo);
        agregarCampo(fld_precio_actual);
        agregarCampo(fld_precio_antes);
        agregarCampo(fld_sap);      
        agregarCampo(fld_fecha);      
        
        cargarModelos();
        cargarOrigenes();
        
        //DatePicker fecha1 = new DatePicker();
        
    }

    @FXML
    private void btnOkPresionado(ActionEvent event) {
        boolean ok = validarCampos2();
        if (ok) {
            
            String valor=txt_descripcion.getText();
            System.out.println(valor);
            this.fleje.setDescripcion(txt_descripcion.getText());           
            this.fleje.setOrigen(cmb_origen.getSelectionModel().getSelectedItem());
            this.fleje.setModelo(cmb_modelo.getSelectionModel().getSelectedItem());
            this.fleje.setPrecioActual(new Double(fld_precio_actual.getText()));
            this.fleje.setPrecioAntes(new Double(fld_precio_antes.getText()));
            this.fleje.setSap(new Integer(fld_sap.getText()));
            this.fleje.setFechaActual(fld_fecha.getText());
            cerrarVentana(event);
        }
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    /*
    private void cargarColores() {
        //  Obtiene la ubicacion del archivo colores.txt
        String ubicacion = CONSTANTES.UBICACION_RECURSOS + "colores.txt";
        try {
            // Obtiene los colores como un vector
            List colores = LectorDeArchivos.obtenerContenidoDeArchivoComoVector(ubicacion);
            Iterator itColores = colores.iterator();
            // Agrega la opcion "Seleccione..."
            cmb_color.getItems().add(CONSTANTES.COMBO_SIN_SELECCION.toString());
            // Carga los colores
            while (itColores.hasNext()) {
                // Obtiene el color
                String c = (String) itColores.next();
                // Agrega el color al combo
                cmb_color.getItems().add(c);
            }
            cmb_color.setValue(CONSTANTES.COMBO_SIN_SELECCION.toString());

        } catch (Exception e) {
            // Muestra el mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar las colores!");
            alert.setContentText("ERROR: " + e.getMessage());
            alert.showAndWait();
        }
    }
     */
    private void cargarModelos() {
        // Agrega la opcion "Seleccione..."
        cmb_modelo.getItems().add(CONSTANTES.COMBO_SIN_SELECCION.toString());
        // Carga el combo con los modelos
        /*  for (int i = 1985; i <= 2015; i++) {
            cmb_modelo.getItems().add(String.valueOf(i));        
        }*/

        cmb_modelo.getItems().add("OFERTA");       
        cmb_modelo.setValue(CONSTANTES.COMBO_SIN_SELECCION.toString());
    }

    private void cargarOrigenes() {
        // Agrega la opcion "Seleccione..."
        cmb_origen.getItems().add(CONSTANTES.COMBO_SIN_SELECCION.toString());
        // Carga el combo con los modelos
        /*  for (int i = 1985; i <= 2015; i++) {
            cmb_modelo.getItems().add(String.valueOf(i));        
        }*/

        cmb_origen.getItems().add("CHINA");
        cmb_origen.getItems().add("ARGENTINA");
        cmb_origen.setValue(CONSTANTES.COMBO_SIN_SELECCION.toString());
      
    }

    /* private void cargarMarcas() {
        //  Obtiene la ubicacion del archivo marcas.txt
        String ubicacion = CONSTANTES.UBICACION_RECURSOS + "marcas.txt";
        try {
            // Obtiene las marcas como un vector
            List marcas = LectorDeArchivos.obtenerContenidoDeArchivoComoVector(ubicacion);
            Iterator itMarcas = marcas.iterator();
            // Agrega la opcion "Seleccione..."
            cmb_marca.getItems().add(CONSTANTES.COMBO_SIN_SELECCION.toString());
            // Carga las marcas
            while (itMarcas.hasNext()) {
                // Obtiene la marca
                String m = (String) itMarcas.next();
                // Agrega la marca al combo
                cmb_marca.getItems().add(m);
            }
            cmb_marca.setValue(CONSTANTES.COMBO_SIN_SELECCION.toString());
        } catch (Exception e) {
            // Muestra el mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar las marcas!");
            alert.setContentText("ERROR: " + e.getMessage());
            alert.showAndWait();
        }
    }
     */
    @Override
    public void setearEntidad(Object entidad) {
        Fleje a = (Fleje) entidad;
        this.fleje = a;
        if (a.getDescripcion()!=null){
             txt_descripcion.setText(a.getDescripcion().toUpperCase());
        }else{
          txt_descripcion.setText(a.getDescripcion());
        }
       
        cmb_origen.setValue(a.getOrigen());
        cmb_modelo.setValue(a.getModelo());              
        fld_precio_actual.setText(String.valueOf(a.getPrecioActual()));
        fld_precio_antes.setText(String.valueOf(a.getPrecioAntes()));
        fld_sap.setText(String.valueOf(a.getSap()));
        fld_fecha.setText(a.getFechaActual());

    }
}
