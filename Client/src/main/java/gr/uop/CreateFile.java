package gr.uop;

import java.io.File;
import java.io.IOException;  

public class CreateFile {

    public CreateFile(){
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
}
