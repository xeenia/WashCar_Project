package gr.uop;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket; 

public class  FileHelper{

    public static void CreateFile(){
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
      new Thread(()->{
        try{
          //μπαινουμε στο αρχειο και διαβάζουμε την πρώτη γραμμή, δηλαδή το αμάξι που μόλις αποθηκευσάμε
          File file = new File("CarWash.txt");
          Scanner fileScanner = new Scanner(file);
          String carInfo=fileScanner.nextLine();
          //συνδεόμαστε με τον σέρβερ
          Socket socket = new Socket("localhost", 5555);
          PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
          //στέλνουμε την πληροφορία στον σέρβερ
          toServer.println(carInfo);
          //κλείνουμε την σύνδεση
          socket.close();
          //σβήνουμε το αρχείο για να μη ξαναπαίρνουμε την ίδια πληροφορία, κι έτσι κι αλλιώς αφού ο σέρβερ πήρε το αμάξι τότε δεν το χρειαζόμαστε πάλι
          PrintWriter writer = new PrintWriter(file);
          writer.print("");
          // other operations
          writer.close();
        }catch (IOException ex) {
          System.out.println(ex);
        }
       
      }).start();
    }
}
