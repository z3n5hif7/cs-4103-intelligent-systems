import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;

public class Activity_0 {

   BufferedImage  image;
   int width;
   int height;
   
   public Activity_0() {
      try {
         File input = new File("./images/rose.jpg");
         image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
         
         int rounds = 3;

         Color white = new Color(255,255,255);


         for(int k=0; k<rounds; k++){
            for(int i=0; i<height; i++) {
               for(int j=0; j<width; j++) {    

                  Color c = new Color(image.getRGB(j, i));

                  if(k == 1){
                     int red = (int)(c.getRed());
                     int green = (int)(c.getGreen());
                     int blue = (int)(c.getBlue());
                     // if(red > 45  && green > 45 && blue > 1 ){
                     // if(red > 0  && green > 25 && blue > 1 ){
                     // if(red > 0  && green > 65 && blue > 1 ){
                     // if(red > 30  && green > 65 && blue > 1 ){
                     // if(red > 30  && green > 65 && blue > 10 ){
                     if(red > 30  && green > 65 && blue > 1 ){
                        image.setRGB(j,i,white.getRGB());
                     }
                  }else{
                     int red = (int)(c.getRed());
                     int green = (int)(c.getGreen());
                     int blue = (int)(c.getBlue());
                     if(red > 40 && green < 20 && blue < 5){
                        Color newRoseColor = new Color(0,0,255);
                        image.setRGB(j,i,newRoseColor.getRGB());
                     }
                  }
               }
            }
         }
         
         File ouptut = new File("./images/remove.jpg");
         ImageIO.write(image, "jpg", ouptut);
         
      } catch (Exception e) {}
   }
   
   static public void main(String args[]) throws Exception {
      Activity_0 obj = new Activity_0();
   }
}