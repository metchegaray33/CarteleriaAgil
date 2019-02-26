/*
 * Auto.java
 *
 */
package com.iclonet.carteleriasegura.entidades;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Fleje {

    // Atributos
    private StringProperty descripcion;
    private IntegerProperty sap;
    private StringProperty origen;
    private StringProperty modelo;
    private DoubleProperty precioActual;
    private DoubleProperty precioAntes;
    private StringProperty fechaActual;

    public String getFechaActual() {
        return fechaActual.get();
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual.set(fechaActual); 
    }
    /**
     * Creates a new instance of Auto
     */
    public Fleje() {
        this(null,null,null,0,0,0,"0");
    }

  

    public Fleje(String descripcion,String origen, String modelo, Integer precioActual, Integer precioAntes, Integer sap,String fechaActual) {
        this.descripcion=new SimpleStringProperty(descripcion);
        this.origen = new SimpleStringProperty(origen);
        this.modelo = new SimpleStringProperty(modelo); 
        this.precioActual = new SimpleDoubleProperty(precioActual);
        this.precioAntes=new SimpleDoubleProperty(precioAntes);
        this.sap = new SimpleIntegerProperty(sap);      
        this.fechaActual=new SimpleStringProperty(fechaActual);

    }

    public String toString() {
        return descripcion.get() + " " + origen.get() + " " +  sap.get() + " " +  modelo.get() + " " +  precioAntes.get() + " " + precioActual.get() + " " + fechaActual.get();
    }

    
     public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }
    
    public String getOrigen() {
        return origen.get();
    }

    public void setOrigen(String marca) {
        this.origen.set(marca);
    }

    public String getModelo() {
        return modelo.get();
    }

    public void setModelo(String modelo) {
        this.modelo.set(modelo);
    }
    
     public int getSap() {
        return sap.get();
    }

    public void setSap(int sap) {
        this.sap.set(sap);
    }

    public Double  getPrecioActual() {
        return precioActual.get();
    }

    public void setPrecioActual(Double precio) {
        this.precioActual.set(precio);
    }
    
     public Double getPrecioAntes() {
        return precioAntes.get();
    }

    public void setPrecioAntes(Double precioAntes) {
        this.precioAntes.set(precioAntes);
    }
    

    public StringProperty modeloProperty(){
        return modelo;
    }
    
    public StringProperty origenProperty(){
        return origen;
    }
    
     public StringProperty descripcionProperty(){
        return descripcion;
    }
     
}
