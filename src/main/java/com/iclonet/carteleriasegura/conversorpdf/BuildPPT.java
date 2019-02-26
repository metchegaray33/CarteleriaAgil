/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.conversorpdf;

import com.iclonet.carteleriasegura.conversorpdf.model.Slide;
import com.iclonet.carteleriasegura.conversorpdf.model.SlideText;
import com.iclonet.carteleriasegura.entidades.AtributosFleje;
import com.iclonet.carteleriasegura.entidades.Fleje;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

/**
 *Esta clase está encargada de crear un ppt en base a un template.
 * Para eso toma datos ingresados y luego los reemplaza.
 * @author Maricel
 */
public class BuildPPT {
    
    
    final static String STRING_FORMAT = "@%s@";   
    static int TEMPLATE_SLIDE_INDEX = 0;
    static String INPUT_PPT_NAME = "template.pptx";
    static String OUTPUT_PPT_NAME = "output.pptx";
    static boolean VERBOSE = false;    
    final static String PATH_SOURCE="/templates/";
    
    
    /**
     * Guardar el ppt generado
     * @param ppt 
     */
    public void savePpt(XMLSlideShow ppt, String PPTOutputName) {

        if (!OUTPUT_PPT_NAME.contains(".pptx")) {
            OUTPUT_PPT_NAME += ".pptx";
        }

        try {
            File file = new File(PPTOutputName);
            FileOutputStream out = new FileOutputStream(file);
            ppt.write(out);
            out.close();
            System.out.println("La Presentacion guardada");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("La Presentacion no puede ser guardada");
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Recorre los datos de entrada que se usarán para reemplazar
     * y luego recorre el ppt encontrando los puntos de reemplazo.     * 
     * @param ppt es la ppt template
     * @param a contiene los datos del fleje
     */
      public void setEngineData(XMLSlideShow ppt,Fleje a) {
    /**
     * Cargo los datos que usaré para reemplazar
     */
        ArrayList<Slide> replacementData = getDataFileContent(a);
        
        /**
         * Recorro el ppt reemplazando
         */
        
        XSLFSlide templateSlide = ppt.getSlides().get(TEMPLATE_SLIDE_INDEX);
        boolean somethingReplaced = false;
      //  System.out.println("------Por comenzar a iterar sobre los slides,cantidad--------------" + replacementData.size());
        for (Slide slide : replacementData) {
            XSLFSlide copiedSlide = copySlide(ppt, templateSlide);
            List<XSLFShape> slideShapes = copiedSlide.getShapes();
          //  System.out.println("------Por comenzar a iterar sobre las variables de la plantilla-, long--------------" + slideShapes.size());
            AtributosFleje atributosFlejes;           
            for (XSLFShape slideShape : slideShapes) {
                if (slideShape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) slideShape;
                    //pongo para ver la font
                    atributosFlejes = getAtritubosFleje(textShape);
                    // recorro la lista de datos a reempazar
                    for (SlideText slideText : slide.replacementData) {
                        // System.out.println("valor de la data a reemplazar:" + slideText.key);                                         
                        String nuevo = String.format(STRING_FORMAT, slideText.key);
                        if (textShape.getText().contains(String.format(STRING_FORMAT, slideText.key))) {
                            replaceContent(textShape, slideText, atributosFlejes);
                            somethingReplaced = true;
                        }
                    }
                }
            }
        }

        if (!somethingReplaced) {
            System.out.println("Nada remplazado en slide " + TEMPLATE_SLIDE_INDEX + " ");
            System.out.println("Desea especificar otro indice? \"pptmaker [-i indice]\"");
            System.exit(0);
        }
    }
      
      /**
     * Abre la ppt template
     * @return 
     */
    public  XMLSlideShow openPpt(String PPTInputName) {
        XMLSlideShow ppt = null;
        try {            
            File file = new File(PPTInputName);        
            FileInputStream inputstream = new FileInputStream(file);
            ppt = new XMLSlideShow(inputstream);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("La presentacion por defecto no existe (./" + INPUT_PPT_NAME + ")");
            System.err.println("Para seleccionar \"pptmaker -f [input-file]\"");
            System.exit(-1);
        }

        return ppt;
    }
      
       /**
     * Obtengo las propiedades del texto Basado en el ejemplo
     * https://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/xslf/usermodel/tutorial/Step1.java
     *
     * @param textShape
     * @return
     */
    private AtributosFleje getAtritubosFleje(XSLFTextShape textShape) {
        /**
         * recorro para saber las propiedades del texto
         */
        AtributosFleje atributosFleje=null;
        double size = 0;
            for (XSLFTextParagraph p : textShape) {             
                for (XSLFTextRun r : p) {                  
                    atributosFleje = new AtributosFleje(r.getFontSize(), r.isStrikethrough());
                }
            }

        return atributosFleje;
    }
    
      /**
       * Reemplaza los valores de la template en base a lo cargado en el
       * método getDataFileContent() chequeando los atributos de la letra para conservar en la copia
       * @param shape información sobre el texto del template original a reemplazar
       * @param data texto con los valores a reemplazar
       * @param atributosFleje 
       */
        private static void replaceContent(XSLFTextShape shape, SlideText data, AtributosFleje atributosFleje) {
        //texto del template
        String textInShape = shape.getText();
        //nuevo texto que reemplaza el original por el value
        String newText = textInShape.replace(String.format(STRING_FORMAT, data.key), data.value);
        System.out.println("---Se reemplaza:" + textInShape + " por :" + newText + "\n");
        //reemplazo el nuevo texto.
        XSLFTextRun addedText = shape.setText(newText.replace("\r", "")); // not include break line
        addedText.setFontFamily("Calibri");
        addedText.setFontColor(Color.BLACK);
        addedText.setStrikethrough(atributosFleje.isTachado());
        //addedText.setFontSize(getFlejeSize(shape));    
        addedText.setFontSize(atributosFleje.getSize());

    }
      /**
       * Copy la slide del template en el nuevo ppt generado
        *@param ppt nueva ppt
        *@param srcSlide slide del template
       * @return 
       */  
      private XSLFSlide copySlide(XMLSlideShow ppt, XSLFSlide srcSlide) {
        XSLFSlideLayout layout = srcSlide.getSlideLayout();
        XSLFSlide newSlide = ppt.createSlide(layout);
        ppt.setSlideOrder(newSlide, srcSlide.getSlideNumber());
        return newSlide.importContent(srcSlide);
    }
         
      /**
       * Se carga para cada uno de los valores que se quiere reemplazar en la ppt 
       * los valores.En la ppt template, éstas keys deben existir con el mismo nombre
       * para luego ser reemplazadas
       * @return un listado conteniendo en éste caso una slide
       */
    private ArrayList<Slide> getDataFileContent(Fleje a) {
        ArrayList<Slide> slidesData = new ArrayList<>();
        Slide mySlide = new Slide();
             
        mySlide.replacementData.add(new SlideText("PRODUCTO", a.getDescripcion().toUpperCase()));
        
        /**
         * Convierto a un formato decimal
         * Creación de un formato con separadores de decimales y millares, con 2 decimales
         */
         
        DecimalFormat formato = new DecimalFormat("#,###.00");
        //String valorFormateado = formato.format(123456.789);
        //Formateo el precio actual
        String precioActualFormateado = formato.format(a.getPrecioActual());
        //Muestra en pantalla el valor 123.456,79 teniendo en cuenta que se usa la puntuación española
        System.out.println(precioActualFormateado);
         //Formateo el precio antes
        String precioAntesFormateado = formato.format(a.getPrecioAntes());
        //Muestra en pantalla el valor 123.456,79 teniendo en cuenta que se usa la puntuación española
        System.out.println(precioAntesFormateado);
        
        mySlide.replacementData.add(new SlideText("PRE", precioActualFormateado));
      //  mySlide.replacementData.add(new SlideText("PREA",a.getPrecioAntes().toString()));      
        mySlide.replacementData.add(new SlideText("PREA",precioAntesFormateado));      
        /**
         * FECHAS
         */
        //SimpleDateFormat sdf=new SimpleDateFormat("M/dd/yyyy");
       // String fechaActual=sdf.format(new Date()); 
        mySlide.replacementData.add(new SlideText("FECHA", a.getFechaActual()));        
        mySlide.replacementData.add(new SlideText("SAP",Integer.toString(a.getSap())));
        mySlide.replacementData.add(new SlideText("ORIGEN", a.getOrigen()));
        
        
                
        slidesData.add(mySlide);
        return slidesData;
    }
}
