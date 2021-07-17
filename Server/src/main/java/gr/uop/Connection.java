package gr.uop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Connection {
    ServerSocket serverSocket;
    Connection(){
      try {
        serverSocket = new ServerSocket(5555);
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
    public void closeConnection() throws IOException{
      serverSocket.close();
    }
    public void makeConnection(){
      
        new Thread (()->{
            try {
              
            while(true){
              
             Socket socket = serverSocket.accept();
            
             Scanner fromClient = new Scanner(socket.getInputStream());
             //παίρνουμε την πληροφορία από τον client
             String carInfo = fromClient.nextLine();
             try {
              PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("CarWash.txt", true)));
              PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("SavedCars.txt", true)));
              out.print(carInfo+",");
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
              DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
              out.println(java.time.LocalDate.now().format(formatter)+","+java.time.LocalTime.now().format(dtf));
              out.close();
              out2.print(carInfo+",");
              out2.println(java.time.LocalDate.now().format(formatter)+","+java.time.LocalTime.now().format(dtf));
              out2.close();
              System.out.println("Successfully wrote to the file.");
            }catch (IOException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
          }
        }catch(IOException ex) {
          System.out.println(ex);
        }
      
          
         }).start();
    }
}
