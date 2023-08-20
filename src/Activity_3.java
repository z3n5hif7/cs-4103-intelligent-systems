import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Activity_3 {
    public static void main(String args[]) throws IOException{
        
        int scale=2;

        File input = new File("./images/rose.jpg");
        BufferedImage image = ImageIO.read(input);
        
        int width = image.getWidth();
        int height = image.getHeight();

        int scaled_height = height;
        int scaled_width = width;

        for( int x = 0 ; x < scale -1 ; x ++){
            scaled_height/=2;
            scaled_width/=2;
        }

        int rescale=scale;
        scale = 1;
        for(int x = 0; x < rescale -1 ; x++){
            scale*=2;
         }

        int current_pixel_width=0;
        int current_pixel_height=0;

        BufferedImage resize = new BufferedImage(scaled_width,scaled_height, image.getType());

        int sum_red, sum_green, sum_blue;

        for(int i=0; i<=height - scale; i+=scale) {
           for(int j=0; j<=width - scale; j+=scale) {  
              sum_red=sum_green=sum_blue=0;
              for(int k = 0; k < scale; k++){
                 for(int x = 0 ; x < scale ; x++){
                        Color c = new Color(image.getRGB(x+j, k+i));
                        sum_red+=(int)(c.getRed());
                        sum_green+=(int)(c.getGreen());
                        sum_blue+=(int)(c.getBlue());
                 }
              }
              int average_scaler=scale*scale;
              Color newColor = new Color((sum_red/average_scaler),sum_green/average_scaler,sum_blue/average_scaler);
              resize.setRGB(current_pixel_height, current_pixel_width, newColor.getRGB());
              current_pixel_height++;
           }
           current_pixel_width++;
           current_pixel_height=0;
        }

        File output = new File("./images/reduced.jpg");
        ImageIO.write(resize, "jpg", output);
    }    
}   