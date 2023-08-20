import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Activity_4{

    public static void main(String args[]) throws IOException{

        // int angle  = 90;
        
        File input = new File("./images/rose.jpg");
        BufferedImage image = ImageIO.read(input);

        int height = image.getHeight();
        int width = image.getWidth();

        int chosen_angle = 60;

        double angle = Math.toRadians(chosen_angle);
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));

        int new_width = (int) Math.floor(Math.abs(width * cos) + Math.abs(height * sin))+1;
        int new_height = (int) Math.floor(Math.abs(height * cos) + Math.abs(width * sin))+1;

        BufferedImage resize = new BufferedImage(new_width, new_height, image.getType());
    
        int original_center_height = Math.round((height+1)/2-1);
        int original_center_width = Math.round((width+1)/2-1);

        int new_center_height = Math.round(((new_height+1)/2)-1);
        int new_center_width = Math.round(((new_width+1)/2)-1);

        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                
                int y = height - 1 - i  - original_center_height;
                int x = width - 1 - j - original_center_width;

                double tan = Math.tan(angle/2);
                int new_x  = (int)(Math.round(x - y * tan));
                int new_y = (int)(Math.round(new_x * sin + y));
                new_x = (int)(Math.round(new_x - new_y*tan));


                new_y  = new_center_height - new_y;
                new_x = new_center_width - new_x;

                if(new_x >= 0 && new_x < new_width && new_y >=0 && new_y < new_height){
                    resize.setRGB(new_x, new_y, image.getRGB(j,i));
                }
            }
        }

        File output = new File("./images/rotate.jpg");
        ImageIO.write(resize, "jpg", output);
    }
}