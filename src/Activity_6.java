import java.awt.event.KeyEvent;
import java.awt.FlowLayout;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import java.io.File;
import java.io.IOException;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.util.Arrays;
import java.util.Collections;

public class Activity_6 implements KeyListener{

    private static WebcamPanel webcamDisplay;  
    private static Webcam webcam; 

	public static void main(String[] args) throws IOException{

		webcam = Webcam.getWebcamByName("A4tech USB2.0 Camera 1");
        webcam.setViewSize(WebcamResolution.QVGA.getSize());

        webcamDisplay = new WebcamPanel(webcam);
        webcamDisplay.setBounds(0,0,0,0);        
        webcamDisplay.setSize(500,500);

		JPanel panel = new JPanel();
		JFrame frame = new JFrame();

		frame.setTitle("Activity #6 - Determine Shape");
		frame.setSize(340,290);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new Activity_6());
		frame.add(panel);

        panel.setLayout(new FlowLayout());
        panel.add(webcamDisplay);

		frame.setVisible(true);	
	}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == 71){
            // webcam.open();
            try {

                System.out.println("==============================================");

                String file = "D://41/CS_4103/lib/Snapshot.png";

                ImageIO.write(webcam.getImage(), "PNG", new File(file));

                BufferedImage image = ImageIO.read(new File("D://41/CS_4103/lib/Snapshot.png"));

                BufferedImage grayscale = image;

                int height = grayscale.getHeight();
                int width = grayscale.getWidth();

                for(int i=0; i<height; i++) {
                    for(int j=0; j<width; j++) {
                    
                       Color c = new Color(image.getRGB(j, i));
                       int red = (int)(c.getRed() * 0.299);
                       int green = (int)(c.getGreen() * 0.587);
                       int blue = (int)(c.getBlue() *0.114);
                       Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
                       
                       grayscale.setRGB(j,i,newColor.getRGB());
                    }
                 }

                 ImageIO.write(grayscale, "png", new File("D://41/CS_4103/lib/Grayscale.png"));
                 
                 BufferedImage ThresholdImage = grayscale; //black and white image

                 for(int i=0; i<height; i++) {
                    for(int j=0; j<width; j++) {
                    
                       Color c = new Color(ThresholdImage.getRGB(j, i));
                       int red = (int)(c.getRed());

                        if(red > 70){
                            Color newColor = new Color(255,255,255);
                            ThresholdImage.setRGB(j,i,newColor.getRGB());
                            
                        }else{
                            Color newColor = new Color(0,0,0);
                            ThresholdImage.setRGB(j,i,newColor.getRGB());
                        }
                    }
                 }

                 Color newColor = new Color(255,0,0);

                 ImageIO.write(ThresholdImage, "png", new File("./images/ThresholdImage.png"));

                 int count = 0, found_black = 0, array_count = 0, max = 0;

                 int[] pixel_counts = new int[200];

                 System.out.println("START HERE!");

                 for(int i = 0 ; i < height ; i++){
                    for(int j = 0 ; j < width ; j++){
                        Color c = new Color(ThresholdImage.getRGB(j,i));
                        int red = (int)(c.getRed());
                        if(red == 0){//pixel is found / strong response
                            found_black = 1;
                            ThresholdImage.setRGB(j,i,newColor.getRGB());
                            count++;
                        }else{//pixel is white, stop counting
                            if(found_black == 1){
                                if(count > 10){
                                    pixel_counts[array_count++] = count;
                                    if(count > max){
                                        max = count;
                                    }
                                }
                                found_black = 0;
                                count = 0;
                                break;
                            }else{
                                continue;
                            }
                        }
                    }
                 }

                 int increased = 0 , decreased = 0, no_change = 0;
                 //23 25 27
                 //25 23
                 //25 25
                 for(int i = 1 ; i < array_count - 1; i++){
                    if(pixel_counts[i+1] > pixel_counts[i]){
                        increased++;
                    }else if(pixel_counts[i + 1] < pixel_counts[i]){
                        decreased++;
                    }else{//equal
                        no_change++;
                    }
                 }

                 System.out.println("increased: " + increased);
                 System.out.println("decreased: " + decreased);
                 System.out.println("no_change: " + no_change);
                 System.out.println("MAX: " + max);
                 System.out.println("array_count: " + array_count);

                 if(no_change > increased && no_change > decreased){
                    if(max > 100){//rectangle threshold
                        System.out.println("Rectangle");
                    }else{
                        System.out.println("Square");
                    }
                 }else if(increased > no_change && increased > decreased){
                    System.out.println("Triangle");
                 }else if(increased + 10 >= decreased || increased - 10 <= decreased || increased == decreased){
                    System.out.println("Circle");
                 }

                 ImageIO.write(ThresholdImage, "png", new File("./images/ThresholdImage.png"));
                 System.out.println("==============================================");

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }        
    }
}