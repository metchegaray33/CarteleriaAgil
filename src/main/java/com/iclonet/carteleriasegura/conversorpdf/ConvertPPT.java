/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.conversorpdf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

/**
 *Clase que uso para convertir una ppt a png
 * @author Usuario
 */
public class ConvertPPT {

    public static void main(String args[]) throws IOException {
      //  convertirAPNG("output1.pptx","outputPNG.png");
    }

    
/**
 * El método toma como entrada un ppt y genera un png
 * @param nombreArchiviPpt
 * @param nombreArchivoPng
 * @throws IOException 
 */
    public void convertirAPNG(String nombreArchiviPpt, String nombreArchivoPng) throws IOException {

        //   FileInputStream is = new FileInputStream("output2.pptx");
        FileInputStream is = new FileInputStream(nombreArchiviPpt);
        XMLSlideShow ppt = new XMLSlideShow(is);
        is.close();

        Dimension pgsize = ppt.getPageSize();

        List<XSLFSlide> slide = ppt.getSlides();
        for (int i = 0; i < slide.size(); i++) {

            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, 1);

            Graphics2D graphics = img.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            graphics.setColor(Color.white);
            graphics.clearRect(0, 0, pgsize.width, pgsize.height);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

            //render
            slide.get(i).draw(graphics);

            // save the output
            // FileOutputStream out = new FileOutputStream("slideine2.png");
            FileOutputStream out = new FileOutputStream(nombreArchivoPng);
            javax.imageio.ImageIO.write(img, "png", out);
            out.close();
        }

    }

}
