/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.controller;

import com.iclonet.carteleriasegura.util.Ventanas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Rodolfo Durante
 */
public class VentanaMaestraControlador extends CarteleriaControlador implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void abrirVentanaAutos(ActionEvent event) {
        super.mainApp.mostrarVentanaInterna(Ventanas.FlejesVentana);
    }

 
  
    
    @FXML
    private void salir(ActionEvent event) {
        Platform.exit();
    }

}
