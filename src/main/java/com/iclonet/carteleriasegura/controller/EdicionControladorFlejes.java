/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.controller;

import com.iclonet.carteleriasegura.util.CONSTANTES;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 *
 */
public abstract class EdicionControladorFlejes implements Initializable {

    private List<Control> campos;

    public EdicionControladorFlejes() {
        this.campos = new ArrayList<>();
    }

    public abstract void setearEntidad(Object entidad);

    protected void agregarCampo(Control field) {
        this.campos.add(field);
    }

    protected boolean validarCampos2() {
        for (Control campo : campos) {

            if (campo instanceof ComboBox) {
                ComboBox c = (ComboBox) campo;
                if (c.getValue() == null || c.getValue().equals(CONSTANTES.COMBO_SIN_SELECCION.toString())) {
                    validacionFalla(campo);
                    return false;
                }
            }

            if (campo instanceof TextField) {

                TextField c = (TextField) campo;
                if (c.getText().isEmpty()) {
                    validacionFalla(campo);
                    return false;
                }

                /**
                 * Valido que se ingresen numeros enteros
                 */
                if (c.getId().equals("fld_sap")) {
                    try {
                        // validarNumerico(campo);                        
                        Integer.parseInt(c.getText());
                    } catch (NumberFormatException n) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Valor incorrecto");
                        alert.setHeaderText("Por favor ingrese un número entero");
                        alert.setContentText("El campo " + c.getTooltip().getText() + " debe ser numerico.");
                        alert.showAndWait();
                        c.requestFocus();
                        return false;
                    }
                }

                /**
                 * Valido que se ingrese un formato de fecha correcta
                 *
                 */
                if (c.getId().equals("fld_fecha")) {

                    //Convertir un String a Date
                    System.out.println("**********************convertir un String a Date ************************");
                   // SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yy");
                    String date3 = c.getText();
                    String[] fechaArray = date3.split("-");

                    System.out.println("fecha ingresada:" + date3);

                    try {

                      
                        String month = fechaArray[1];
                        String dayOfMonth = fechaArray[0];
                        String year = fechaArray[2];

                      //  Date dateNew = sdf2.parse(date3);
                      //  System.out.println("nueva fecha:" + dateNew.toString());

                        LocalDate today = LocalDate.of(new Integer(year).intValue(), new Integer(month).intValue(), new Integer(dayOfMonth).intValue());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                        System.out.println(formatter.format(today)); // 01/01/16

                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Verifique la fecha");
                        alert.setHeaderText("Por favor ingrese una fecha con formato dd-mm-aa");
                        alert.setContentText("El campo " + c.getTooltip().getText() + " debe ser fecha");
                        alert.showAndWait();
                        c.requestFocus();
                        return false;
                    }

                }

                /**
                 * Valido que se ingresen numeros doubles
                 */
                if (c.getId().equals("fld_precio_actual") || c.getId().equals("fld_precio_antes")) {

                    try {
                        Double.parseDouble(c.getText());
                    } catch (NumberFormatException n) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Valor incorrecto");
                        alert.setHeaderText("Por favor ingrese un número decimal");
                        alert.setContentText("El campo " + c.getTooltip().getText() + " debe ser decimal.");
                        alert.showAndWait();
                        c.requestFocus();
                        return false;

                    }
                }

            }

            if (campo instanceof TextArea) {
                TextArea c = (TextArea) campo;

                if (c.getText() == null || c.getText().isEmpty()) {
                    validacionFalla(campo);
                    return false;
                }
            }

        }
        return validacionOk();
    }

    private boolean validacionOk() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("Esta a punto de guardar los cambios...");
        alert.setContentText("desea continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    private void validacionFalla(Control control) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Datos incompletos");
        alert.setHeaderText("Por favor complete todos los campos");
        alert.setContentText("El campo " + control.getTooltip().getText() + " no puede estar vacio.");
        alert.showAndWait();
        control.requestFocus();
    }

}
