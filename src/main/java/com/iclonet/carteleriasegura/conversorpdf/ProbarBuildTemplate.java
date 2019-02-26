/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.conversorpdf;

import com.iclonet.carteleriasegura.entidades.Fleje;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

/**
 *En ésta prueba toma comoo base un template y genera un output con los 
 * nuevos datos
 * @author Usuario
 */
public class ProbarBuildTemplate {
    
    static String INPUT_PPT_NAME = "template.pptx";
    static String OUTPUT_PPT_NAME = "output.pptx";
    static int TEMPLATE_SLIDE_INDEX = 0;
      
     public static void main(String[] args) throws IOException {

        Fleje fleje=new Fleje();
        fleje.setDescripcion("Tomate");
        fleje.setModelo("Oferta");
        fleje.setOrigen("China");
        fleje.setSap(8989);
        fleje.setPrecioActual(563.60);
        fleje.setPrecioAntes(55.60);
        fleje.setFechaActual(new Date().toString());
        
        BuildPPT buildPPT=new BuildPPT();         
        XMLSlideShow ppt = buildPPT.openPpt(INPUT_PPT_NAME);
        buildPPT.setEngineData(ppt,fleje);       
        ppt.removeSlide(TEMPLATE_SLIDE_INDEX); // remove the template slide
        buildPPT.savePpt(ppt,OUTPUT_PPT_NAME);
    
            
        
      
    }
}
