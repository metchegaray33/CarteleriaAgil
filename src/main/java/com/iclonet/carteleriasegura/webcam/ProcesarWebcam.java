/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.webcam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.WritableRaster;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * http://webcam-capture.sarxos.pl/
 *
 * @author Usuario
 */
public class ProcesarWebcam {

    public static void main(String[] args) {
                
       ProcesarWebcam pw=new ProcesarWebcam();
       pw.procesarWebcam("png", "outputmaricel.png", 180);
    }
    
  /**
   * Procesa la imagen tomada de la webcam, toma info de la webcam default,
   * le asigna una resolución de 640 por 480 y luego llama al método para que la rote
   * @param typeOfOutputScanFile tipo del archivo generado por la foto de la webcam
   * @param nameOutputScanFile nombre del archivo generado por la foto de la webcam ej output.png
   * @param angleRotation angulo de rotación, si pongo 0 no rota
   */  
  public void procesarWebcam(String typeOfOutputScanFile,String nameOutputScanFile,int angleRotation){       
              
        // 1-get default webcam and open it
        Webcam webcam = Webcam.getDefault();
        /**
         * Dimensiones posibles[176x144] [320x240] [640x480]
         */
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();
        // 2- get image from scan
        BufferedImage image = webcam.getImage();
        webcam.close();
        
        System.out.print("Imagen tomada\n");
          
        try {
            roateImage(image,angleRotation,typeOfOutputScanFile,nameOutputScanFile);
        } catch (IOException ex) {
            Logger.getLogger(ProcesarWebcam.class.getName()).log(Level.SEVERE, null, ex);
        }
        
  }
  
  /**
   * Rota y salva una imagen
   * https://codereview.stackexchange.com/questions/146603/image-processing-rotation
   * @param pic1
   * @throws IOException 
   */ 
  
  private void roateImage(BufferedImage pic1, double anguloRotacion,String tipoImagenOutput,String nombreImagenOutputScan) throws IOException {
    int width = pic1.getWidth(null);
    int height = pic1.getHeight(null);

    double angle = Math.toRadians(anguloRotacion);
    double sin = Math.sin(angle);
    double cos = Math.cos(angle);
    double x0 = 0.5 * (width - 1);     // point to rotate about
    double y0 = 0.5 * (height - 1);     // center of image

    WritableRaster inRaster = pic1.getRaster();
    BufferedImage pic2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    WritableRaster outRaster = pic2.getRaster();
    int[] pixel = new int[3];

    // rotation
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            double a = x - x0;
            double b = y - y0;
            int xx = (int) (+a * cos - b * sin + x0);
            int yy = (int) (+a * sin + b * cos + y0);

            if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
                outRaster.setPixel(x, y, inRaster.getPixel(xx, yy, pixel));
            }
        }
    }
   // ImageIO.write(pic2, tipoImagenOutput, new File("src/" + nombreImagenOutputScan));
    ImageIO.write(pic2, tipoImagenOutput, new File(nombreImagenOutputScan));
    System.out.print("Imagen guardada\n");
}
}
