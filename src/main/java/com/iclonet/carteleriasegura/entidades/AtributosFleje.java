/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.entidades;

/**
 *
 * @author Usuario
 */
public class AtributosFleje {

    public AtributosFleje(double size, boolean tachado) {
        this.size = size;
        this.tachado = tachado;
    }
    
    private double size;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isTachado() {
        return tachado;
    }

    public void setTachado(boolean tachado) {
        this.tachado = tachado;
    }
    private boolean tachado;
}
