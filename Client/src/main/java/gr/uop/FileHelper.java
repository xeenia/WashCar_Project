package gr.uop;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileHelper {

    public static void createFile(){
        try {
            File myObj = new File("CarWash.txt");
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    //write data to file
    public static void WriteToFile(String data) {
      try {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("CarWash.txt", true)));
        out.println(data);
        out.close();
        System.out.println("Successfully wrote to the file.");
      }catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      ClientFile toServer = new ClientFile();
    }
}
