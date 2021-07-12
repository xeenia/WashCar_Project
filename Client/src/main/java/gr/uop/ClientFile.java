package gr.uop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientFile {
    
    ClientFile(){
        try (Socket socket = new Socket("localhost", 5555);
             PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
             ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream())) {

            String filename = "CarWash.txt";

            // Send the filename to the server
            toServer.println(filename);

            try (FileOutputStream fileWriter = new FileOutputStream(filename)) {
                int bufferSize = 100;
                byte[] fileBytes = new byte[bufferSize];

                int bytesRead = fromServer.read(fileBytes);
                while (bytesRead > 0) {
                    System.out.println(bytesRead);

                    // Write the bytes received to the file
                    fileWriter.write(fileBytes, 0, bytesRead);

                    // Read the next group of bytes from the server
                    bytesRead = fromServer.read(fileBytes);
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
