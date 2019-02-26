/*
 * CONSTANTES.java
 *
 */

package com.iclonet.carteleriasegura.util;


/**
 *
 * 
 */
public enum CONSTANTES {
    
    UBICACION_RECURSOS("/com/iclonet/carteleriasegura/recursos/"),
    COMBO_SIN_SELECCION("Seleccione...");
    
    private String valor;

    private CONSTANTES(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor;
    }
    
    
}
