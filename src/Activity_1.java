import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;

public class Activity_1 {

   BufferedImage  image;
   int width;
   int height;
   
   public Activity_1() {
      try {
         File input = new File("./images/rose.jpg");
         image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
         
         int rounds = 3;

         Color white = new Color(255,255,255);
         Color black = new Color(0,0,0);


         for(int k=0; k<rounds; k++){
            for(int i=0; i<height; i++) {
               for(int j=0; j<width; j++) {    

                  Color c = new Color(image.getRGB(j, i));

                  if(k == 1){
                     int red = (int)(c.getRed());
                     int green = (int)(c.getGreen());
                     int blue = (int)(c.getBlue());
                     if(red < 240 && green > 30 && blue < 255){
                        image.setRGB(j,i,black.getRGB());
                     }
                  }
               }
            }
         }
         
         File ouptut = new File("./images/black.jpg");
         ImageIO.write(image, "jpg", ouptut);
         
      } catch (Exception e) {}
   }
   
   static public void main(String args[]) throws Exception {
      Activity_1 obj = new Activity_1();
   }
}