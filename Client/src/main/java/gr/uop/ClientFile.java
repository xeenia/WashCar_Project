package gr.uop;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientFile {
    
    ClientFile(){
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
