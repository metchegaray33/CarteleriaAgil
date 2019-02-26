/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.printer;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

/**
 * Ejemplo basado en
 * https://www.developer.com/java/data/how-to-add-java-print-services-to-your-java-application.html
 * Mando a imprimir el png
 * @author Usuario
 */
public class CarteleraPrinter {

    public static void main(String args[]) throws IOException, PrinterException {
        CarteleraPrinter cp = new CarteleraPrinter();
//         cp.imprimirPNG("slideine2.png");
    }

    /**
     * Envía a imprimir un PNG previo selección del cuadro de diálogo     *
     * @param nombreDoc
     */
    public void imprimirPNG(String nombreDoc) throws PrintException {

        // Create a Doc
        DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
        Doc doc = new InputStreamDoc(nombreDoc, flavor);
        //Creo un print service default
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();
        PrintService selectedPrintService = ServiceUI.printDialog(null, 150, 150, printServices, defaultPrintService, null, attrib);

        if (selectedPrintService != null) {
            System.out.println("selected printer:" + selectedPrintService.getName());

            //Creo el job de impresion a imprimir
            DocPrintJob job = defaultPrintService.createPrintJob();

            job.print(doc, attrib);

        } else {
            System.out.println("selection cancelled");
        }

    }

    /**
     * Un caso de impresión dónde los atributos de la impresora y la selección
     * de la impresora se hace por código
     *
     * @param nombreImpresora
     */
    public static void impresionForma2(String nombreImpresora) {

        DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
        /* Create a set which specifies how the job is to be printed */
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.NA_LETTER);
        aset.add(new Copies(1));
        aset.add(OrientationRequested.LANDSCAPE);

        // Create a Doc
        InputStreamDoc doc = new InputStreamDoc("slideine2.png", flavor);

        // Discover the printers that can print the format according to the instructions in the attribute set
        PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
        DocPrintJob job = null;

        if (pservices.length > 0) {

            for (PrintService ps : pservices) {
              //  System.out.println("Impressora Encontrada: " + ps.getName());

                if (ps.getName().contains(nombreImpresora)) {
                   // System.out.println("Impressora Selecionada: " + nombreImpresora);
                    job = ps.createPrintJob();
                    break;
                }
            }
            try {
                job.print(doc, aset);
            } catch (PrintException ex) {
                Logger.getLogger(CarteleraPrinter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
