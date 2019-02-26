/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.util;

/**
 *
 * @author Rodolfo Durante
 */
public enum Ventanas {

    VentanaPrincipal("/fxml/VentanaMaestra.fxml", "Administracion de Carteles"), 
    FlejesVentana("/fxml/FlejeVentana.fxml", "Administracion de Fleje"),    
    FlejesEdicion("/fxml/FlejeEdicion.fxml", "Edicion de Fleje");       


    
    private String path;
    private String title;
    private Boolean isopen;

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIsopen() {
        return isopen;
    }

    public void setIsopen(Boolean isopen) {
        this.isopen = isopen;
    }

    
    private Ventanas(String path, String title) {
        this.path = path;
        this.title = title;
        isopen = false;
    }
    
    

}
