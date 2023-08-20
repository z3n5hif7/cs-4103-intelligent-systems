import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.lang.*;
import java.io.*;
import java.util.*;


import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;

public class Activity_5 implements ActionListener{

	private static JButton outputButton;
        
        private static WebcamPanel webcamDisplay;
        
        private static Webcam webcam; 

	public static void main(String[] args) throws IOException{
            
                webcam = Webcam.getWebcamByName("A4tech USB2.0 Camera 1");
                webcam.setViewSize(WebcamResolution.QVGA.getSize());

                webcamDisplay = new WebcamPanel(webcam);
                webcamDisplay.setBounds(0,0,0,0);
                webcamDisplay.setSize(100,100);
                
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();

		frame.setTitle("Activity #5 - Webcam snapshot");
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		outputButton = new JButton("Snapshot");
		outputButton.setBounds(500,500,500,100);
                                        //right ,down,length,width?
		outputButton.addActionListener(new Activity_5());
		outputButton.setBackground(Color.GREEN);

                panel.setLayout(null);
//                panel.setLayout(new BoxLayout(frame, BoxLayout.PAGE_AXIS));
		panel.add(outputButton);
                panel.add(webcamDisplay);

		frame.setVisible(true);	
	}

	@Override
	public void actionPerformed(ActionEvent e){
            if(e.getActionCommand() == "Check String"){
                webcam.open(); 
                try { 
                    ImageIO.write(webcam.getImage(), "JPG", new File("./images/snapshot.jpg"));
                } catch (IOException ex) {
                    Logger.getLogger(Activity_5.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

	}
}