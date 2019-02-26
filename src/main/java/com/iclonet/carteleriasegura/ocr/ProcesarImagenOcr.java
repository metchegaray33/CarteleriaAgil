/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.ocr;

import com.iclonet.carteleriasegura.controller.FlejeVentanaControlador;
import com.iclonet.carteleriasegura.entidades.Fleje;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * La clase toma una imagen y extrae su descripcion
 * https://unpocodejava.com/2017/04/26/tess4j-ocr-desde-java-de-forma-sencilla/
 * https://github.com/piersy/BasicTesseractExample/blob/master/src/test/java/BasicTesseractExampleTest.java
 * @author iClonet
 */
public class ProcesarImagenOcr {

    public static void main(String args[]) throws IOException, PrinterException {
        ProcesarImagenOcr procesarImagenOcr=new ProcesarImagenOcr();   
       // procesarImagenOcr.procesarImagen(FlejeVentanaControlador.INPUT_JPG_SCAN_NAME);
       
        procesarImagenOcr.procesarImagen("testdimension.png");
        //System.out.println(procesarImagenOcr.getImgText("caso1_flejeoriginal.jpg"));
    }

    /**
     * Lee el texto de la imagen y genera un fleje con los datos leidos
     * Por ahora solamente carga datos en la descripcion
     * @param nombreArchivoFlejeScaneado nombre del archivo a escanear
     * @return Fleje con los valores en sus atributos
     */
    public Fleje procesarImagen(String nombreArchivoFlejeScaneado){
        
        Fleje fleje=new Fleje();
        
        String textoImagen=getImgText(nombreArchivoFlejeScaneado);
        System.out.println("El texto leido de la imagen es:" + textoImagen);
        String [] textoImagenArray=textoImagen.split("\\r?\\n");
         /**
          * Las dos primeras lineas son la descripcion
            */ 
       String descripcionFleje=textoImagenArray[0] + textoImagenArray[1];       
       fleje.setDescripcion(descripcionFleje);  
     // System.out.println("Descripcion del producto leido de la imagen es:" + descripcionFleje);
     /**
      * Debajo se hace un ejemplo para cargar los restantes atributos del fleje y devolver.
      */
      // fleje.setDescripcion("SET DE BUCEO NIÑOS \"TORTUGA\" ANTIP-SNOFK");  
       fleje.setPrecioActual(new Double(335));
       fleje.setPrecioAntes(new Double(670));
       fleje.setModelo("OFERTA");
       fleje.setOrigen("CHINA");
       fleje.setSap(1036523);
       fleje.setFechaActual("0");
      
       return fleje;
    }
    /**
     * Extrae el texto de una imagen
     * @param imageLocation ubicacion del archivo
     * @return String de la imagen
     */
    public String getImgText(String imageLocation) {
        
        String imgText =null;
                
        try {
            ITesseract instance = new Tesseract();
            instance.setLanguage("spa");
            imgText = instance.doOCR(new File(imageLocation));
            
        } catch (TesseractException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProcesarImagenOcr.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imgText;
        
    }

}
