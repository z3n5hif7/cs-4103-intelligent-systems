import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.awt.*;

public class Activity_7 {

    private static Random random = new Random(0);

    private static final double learning_rate = 0.01;
                                        //1 x 3600         3600 x 10
    private static double[][] multiply(double[][] input1, double[][] input2) {
        double[][] output = new double[input1.length][input2[0].length];
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[0].length; j++) {
                double value = 0;
                for (int k = 0; k < input1[0].length; k++) {
                    value += input1[i][k] * input2[k][j];
                }
                output[i][j] = value;
            }
        }
        return output;
    }

    private static double[][] add(double[][] input1, double[] input2) {
        double[][] output = new double[input1.length][input1[0].length];
        for (int i = 0; i < input1.length; i++) {
            for (int j = 0; j < input1[0].length; j++) {
                output[i][j] = input1[i][j] + input2[j];
            }
        }
        return output;
    }

    private static double[][] add2d(double[][] input1, double[][] input2) {
      double[][] output = new double[input1.length][input1[0].length];
      for (int i = 0; i < input1.length; i++) {
          for (int j = 0; j < input1[0].length; j++) {
              output[i][j] = input1[i][j] + (-learning_rate * input2[i][j]);
          }
      }
      return output;
    }

    private static double[] add1d(double[] input1, double[] input2) {
      double[] output = new double[input1.length];
      for (int i = 0; i < input1.length; i++) {
              output[i] = input1[i] +(-learning_rate * input2[i]);
      }
      return output;
    }

    public static double[][] relu(double[][] input1){
        double[][] output = new double[input1.length][input1[0].length];
        for (int i = 0; i < input1.length; i++) {
            for (int j = 0; j < input1[0].length; j++) {
                output[i][j] = input1[i][j] < 0 ? 0 : input1[i][j];
            }
        }
        return output;   
    }

    public static double[][] softmax(double[][] input){
      double[][] output = new double[input.length][input[0].length];
      double E = 2.71828182846;

      double max = 0;
      for(int i = 0 ; i < input.length ; i++){
        max = input[i][0];
        for(int j = 0 ; j < input[0].length ; j++){
            if(max < input[i][j]){
              max = input[i][j];
            }
        }
        for(int k = 0 ; k < output[0].length ; k++){
          input[i][k]-=max;
        }
      }

      for(int i = 0 ; i < input.length ; i++){
        int sum = 0; 
        for(int j = 0 ; j < input[0].length ; j++){
          output[i][j] = Math.pow(E, input[i][j]);
          sum+=output[i][j];
        }
        for(int k = 0 ; k < input[0].length ; k++){
          output[i][k]/=sum;
        }
      }
      return output;
    }

    public static double[][] initializer(int rows, int cols){
        double[][] output = new double[rows][cols];
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[0].length; j++) {
                output[i][j] = 0.1 * random.nextGaussian();
            }
        }
        return output;
    }

    public static double[] initializer1d(int size){
      double[] output = new double[size];
      for(int i = 0 ; i < output.length ; i++){
        output[i] = 0.1 * random.nextGaussian();
      }
      return output;
    }

    public static double[][] transpose(double[][] input){
      double[][] output = new double[input[0].length][input.length];

      for(int i = 0 ; i < input[0].length ; i++){
        for(int j = 0 ; j < input.length ; j++){
          output[i][j] = input[j][i];
        }
      }
      return output;
    }

    public static double[][] transpose1d(double[] input){
      double[][] output = new double[input.length][1];

      for(int i = 0 ; i < input.length ; i++){
        for(int j= 0 ; j < 1 ; j++){
          output[i][j] = input[i];
        }
      }
      return output;
    }

    public static void print2d(double[][] input){
      System.out.println("Printing 2D Matrix [ROWS: " + input.length + " COLS: " + input[0].length + "]");
      for(int i = 0 ; i < input.length ; i++){
        for(int j = 0 ; j < input[0].length ; j++){
          System.out.print(input[i][j] + " ");
        }
        System.out.println("");
      }
      System.out.println("");
    }

    public static void print1d(double[] input){
      for(int i = 0 ; i < input.length ; i++){
        System.out.print(input[i] + " ");
      }
      System.out.println("");
    }

    public static void print1dint(int[] input){
        for(int i = 0 ; i < input.length ; i++){
          System.out.print(input[i] + " ");
        }
        System.out.println("");
      }

    public static double[][] copy2d(double[][] input){
      double[][] output = new double[input.length][input[0].length];
      for(int i = 0 ; i < input.length ; i++){
        for(int j = 0 ; j < input[0].length ; j++){
          output[i][j] = input[i][j];
        }
      }
      return output;
    }

    public static void main(String[] args) throws IOException{

        int samples_per_letter = 30;//number of samples of a letter directory
    
        int training_split = (int)(samples_per_letter * .70); //21
    
        int testing_split = (int)(samples_per_letter * .30); //9
    
        File directory = new File("./images/training");
    
        int classifications = 5; //5 letters A-E
        
        int total_training_samples = classifications * training_split; //21 * 5 = 105
        
        int total_testing_samples = classifications * testing_split; // 9 * 5 = 45
    
        File[] directory_list = directory.listFiles(), data_train = new File[total_training_samples], data_test = new File[total_testing_samples];
    
        int train_count = 0, test_count = 0;
    
        if (directory_list != null) {
          for (File file : directory_list) {
    
            File folder = new File("./images/training/" + file.getName());
    
            File[] image_list = folder.listFiles();
    
            if(image_list != null){
              File[] letters  = new File[samples_per_letter];
              int letter_count = 0;
              for(File image : image_list){
                letters[letter_count++] = image; 
              }
    
              for(int i = 0 ; i < samples_per_letter; i++){
                int index = (int)(Math.random() * samples_per_letter);
                File temp = letters[i];
                letters[i] = letters[index];
                letters[index] = temp;
              }
    
              int i = 0;
    
              for(;i<training_split; i++){
                data_train[train_count++] = letters[i];
              }
    
              for(;i<samples_per_letter; i++){
                data_test[test_count++] = letters[i];
              }
            }
          }
        }
    
        for(int i=0; i < total_training_samples ; i++){
          int index = (int)(Math.random() * total_training_samples);
          File temp = data_train[i];
          data_train[i] = data_train[index];
          data_train[index] = temp;
        }
    
        for(int i=0; i < total_testing_samples ; i++){
          int index = (int)(Math.random() * total_testing_samples);
          File temp = data_test[i];
          data_test[i] = data_test[index];
          data_test[index] = temp;
        }
    
        int  height = 60, width = 60, one_hot_dimension = height * width;
    
        double[][] x_training =  new double[total_training_samples][one_hot_dimension];
    
        double[][] x_testing = new double[total_testing_samples][one_hot_dimension];
    
        int[] y_training = new int[total_training_samples];
    
        int[] y_testing = new int[total_testing_samples];
    
        int training_count = 0;
        for(File image : data_train){
          int pixel_count = 0 ; 
    
          BufferedImage training_image = ImageIO.read(new File(image.getPath()));
          String image_name =  image.getName();
          
          y_training[training_count] =  image_name.charAt(0);
          for(int i = 0 ; i < height; i++){
            for(int j = 0 ; j < width; j++){
              Color c = new Color(training_image.getRGB(j, i));
                double value = c.getRed() == 255 ? 1 : 0 ;
                x_training[training_count][pixel_count++] = value;
            }
          }
          training_count++;
        }
    
        int testing_count = 0;
        for(File image : data_test){
          int pixel_count = 0 ;
    
          BufferedImage testing_image = ImageIO.read(new File(image.getPath()));
          String image_name =  image.getName();
          y_testing[testing_count] =  image_name.charAt(0);
          for(int i = 0 ; i < height; i++){
            for(int j = 0 ; j < width; j++){
              Color c = new Color(testing_image.getRGB(j, i));
                double value = c.getRed() == 255 ? 1 : 0 ;
                x_testing[testing_count][pixel_count++] = value;

            }
          }
          testing_count++;
        }

        int hidden_layer = 128, output_layer = 5, batch_size = 1;

        double[][] W1 = initializer(one_hot_dimension, hidden_layer);//already transposed based on this 3600 x 10
        double[][] W2 = initializer(hidden_layer, output_layer);//already transposed based on this 3600 x 10

        double[] B1 = new double[hidden_layer];
        double[] B2 = new double[output_layer];

        double[][] X =  new double[batch_size][one_hot_dimension]; // 1 x 3600 1d array to 2d magic 
        int[] Y = new int[batch_size];

        for(int x = 0 ; x < total_training_samples; x+=batch_size){
            for(int i = 0 ; i < batch_size ; i++){
                X[i] = x_training[x + i];
                Y[i] = y_training[x + i] - 65;
              }
      
              double[][] Z1 = add(multiply(X, W1), B1);//1 x 3600 <=> 3600 x 10 = 1 x 10 
              double[][] Z1_relu = relu(Z1); // 1 x 10
              double[][] Z2 = add(multiply(Z1_relu, W2), B2); //1x10 <=> 10 x 5  = 1 x 5
              double[][] Z2_softmax = copy2d(Z2); //probability distribution

              double sum=0, E = 2.71828182846, max = 0;

              for(int i = 0 ; i < Z2_softmax.length ; i++){
                max = Z2_softmax[i][0];
                for(int j = 0 ; j < Z2_softmax[0].length ; j++){
                    if(max < Z2_softmax[i][j]){
                      max = Z2_softmax[i][j];
                    }
                }
                for(int j = 0 ; j < Z2_softmax[0].length ; j++){
                  Z2_softmax[i][j]-=max;
                }
              }
      
              for(int i = 0; i < Z2_softmax.length ; i++){
                sum = 0 ;
                for(int j = 0 ; j < Z2_softmax[0].length ; j++){
                  Z2_softmax[i][j] = Math.pow(E, Z2_softmax[i][j]);
                  sum+=Z2_softmax[i][j];
                }
                for(int j = 0 ; j < Z2_softmax[0].length ; j++){
                  Z2_softmax[i][j]/=sum;
                }
              }

              double[][] Z2_softmax_copy = copy2d(Z2_softmax);
      
              for(int i = 0 ; i < Z2_softmax_copy.length ; i++){
                for(int j = 0 ; j < Z2_softmax_copy[0].length ; j++){
                  if(j == Y[i]){ //Y = true label
                    Z2_softmax_copy[i][j]-=1;
                  }
                }
                for(int k = 0 ; k < Z2_softmax_copy[0].length ; k++){
                  Z2_softmax_copy[i][k] /= Z2_softmax_copy.length; // 3 x 5 matrix
                }
              }

              double[][] Z2_relu_copy = copy2d(Z1_relu); //it's not supposed to be Z1_relu!
      
              double[][] DW2 = multiply(transpose(Z2_relu_copy),  Z2_softmax_copy);
      
              double[] DB2 = new double[output_layer];
      
              for(int i = 0 ; i < Z2_softmax_copy[0].length ; i++){
                sum = 0;
                for(int j= 0 ; j < Z2_softmax_copy.length ; j++){
                    sum+=Z2_softmax_copy[j][i];
                }
                DB2[i] = sum;
              }
      
              double[][] next_input = multiply(Z2_softmax_copy, transpose(W2));
      
              double[][] next_input_copy = copy2d(next_input);
              for(int i = 0 ; i < next_input_copy.length ; i++){
                for(int j = 0 ; j < next_input_copy[0].length ; j++){
                  if(Z1[i][j] <= 0){
                    next_input_copy[i][j] = 0;
                  }
                }
              }
      
              double[][] last = copy2d(next_input_copy);
      
              double[][] X_copy = copy2d(X);//x = 3 x 3600
      
              double[][] DW1 = multiply(transpose(X_copy), last );
      
              double[] DB1 = new double[hidden_layer];
              
              
              for(int i = 0 ; i < last[0].length ; i++){
                sum = 0;
                for(int j= 0 ; j < last.length ; j++){
                    sum+=last[j][i];
                }
                DB1[i] = sum;
              }
               
              W1 = add2d(W1, DW1);
              W2 = add2d(W2, DW2);
              B1 = add1d(B1, DB1);
              B2 = add1d(B2, DB2);
        }

        int[] predictions = new int[total_testing_samples];
        int correct = 0 ;
        batch_size = 1;
        for(int x = 0 ; x < total_testing_samples; x++){
            // double[] X= x_testing[i];
            double[][] X_train = new double[1][one_hot_dimension];
            X_train[0] = x_testing[x];
            int y_train = y_testing[x] - 65;//index
      
            double[][] Z1 = add(multiply(X_train, W1), B1);//10 x 1 , 10 100
            double[][] Z1_relu = relu(Z1);
            double[][] Z2 = add(multiply(Z1_relu, W2), B2);
            double[][] Z2_softmax = copy2d(Z2);

            double sum=0, E = 2.71828182846, max = 0;
            for(int i = 0 ; i < Z2_softmax.length ; i++){
              max = Z2_softmax[i][0];
              for(int j = 0 ; j < Z2_softmax[0].length ; j++){
                  if(max < Z2_softmax[i][j]){
                    max = Z2_softmax[i][j];
                  }
              }
              for(int j = 0 ; j < Z2_softmax[0].length ; j++){
                Z2_softmax[i][j]-=max;
              }
            }
    
            for(int i = 0; i < Z2_softmax.length ; i++){
              sum = 0 ;
              for(int j = 0 ; j < Z2_softmax[0].length ; j++){
                Z2_softmax[i][j] = Math.pow(E, Z2_softmax[i][j]);
                sum+=Z2_softmax[i][j];
              }
              for(int j = 0 ; j < Z2_softmax[0].length ; j++){
                Z2_softmax[i][j]/=sum;
              }
            }
      
            int max_index = 0 ; 

            for(int i = 0 ; i < Z2_softmax.length ; i++){
              for(int j= 0 ; j < Z2_softmax[0].length ; j++){
                  if(Z2_softmax[i][max_index] < Z2_softmax[i][j]){
                      max_index = j;
                  }
              }
            }
  
            for(int i = 0 ; i < Z2_softmax[0].length ; i++){
              int letter = 65 + i;
              System.out.print("[" + (char)(letter) + " => " + Z2_softmax[0][i]*100 + " %] ");
            }
            System.out.println("");
            System.out.println("Predicted: " + (char)(max_index + 65));
            System.out.println("Truth: " + (char)(y_train + 65));
            System.out.print("========================================================================================================================");
            System.out.println("=======================");
            predictions[x] = max_index;
            if(max_index == y_train){
              correct++;
            }         
        }
    
        System.out.println("");
        System.out.println("Correct: " + correct + "/" + total_testing_samples);
        System.out.printf("Accuracy %.2f", (double)correct / (double)total_testing_samples*100);
        System.out.println("%");
    }
}