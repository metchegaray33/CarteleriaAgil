/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.controller;

import com.iclonet.carteleriasegura.aplicacion.APPCarteles;



/**
 *
 * @author MCE
 */
public abstract class CarteleriaControlador {

    protected APPCarteles mainApp;

    public void setMainApp(APPCarteles runner) {
        this.mainApp = runner;
    }
}
