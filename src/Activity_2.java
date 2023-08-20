import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Activity_2{
    public static void main(String args[]) throws IOException{
    
        int scale=4;

        File input = new File("./images/rose.jpg");
        BufferedImage image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();
    
        int scaled_width = width*scale;
        int scaled_height = height*scale;

        int pixel_height =  scaled_height / height;
        int pixel_width =  scaled_width / width;
    
        BufferedImage resize = new BufferedImage(scaled_width,scaled_height, image.getType());

        int current_pixel_width = 0;
        int current_pixel_height = 0;
    
        for(int i=0; i<height; i++) {
           for(int j=0; j<width; j++) {    
    
              Color c = new Color(image.getRGB(j, i));
    
              for(int k = 0; k < pixel_height; k++){
                 for(int x = 0 ; x < pixel_width; x++){
                    resize.setRGB(x+current_pixel_height, k+current_pixel_width,c.getRGB());
                 }
              }
              current_pixel_height+=pixel_height;
           }
           current_pixel_width+=pixel_width;
           current_pixel_height=0;
        }
    
        File output = new File("./images/enlarge.jpg");
        ImageIO.write(resize, "jpg", output);
    }
}