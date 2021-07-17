package gr.uop;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Server extends Application {
  ServerSocket serverSocket;
      
    @Override
    public void start(Stage stage) {   
      try {
        serverSocket = new ServerSocket(5555);
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    Connection connection = new Connection(serverSocket);
    IncomeBook book = new IncomeBook();
    UI ui = new UI();
    
    HBox p_hb_logo = ui.createLogo();
    VBox p_vb_center = new VBox();
    TextField tx = ui.createSearchField();
    TableView table = book.createTable();
    table.setItems(book.getCars()); 
    p_vb_center.getChildren().addAll(tx,table);
    VBox p_vb_mainPage = new VBox(); 
    p_vb_mainPage.setStyle("-fx-background-color:#abdbe3;");
    p_vb_mainPage.setSpacing(15);
    p_vb_mainPage.setPadding(new Insets(20,20,20,20));
    Button refreshButton = new Button();
    p_vb_mainPage.getChildren().addAll(ui.createRefreshButton(refreshButton),p_hb_logo,p_vb_center);
    p_vb_mainPage.setSpacing(0);

    var scene = new Scene(p_vb_mainPage, 1024, 768);
    stage.setScene(scene);
    stage.setMinHeight(768);
    stage.setMinWidth(1024);
    stage.setTitle("CASH DESK");
    stage.show();   
    
    FilteredList<Car> filteredlist = new FilteredList<>(book.getCars(), b-> true);
    tx.textProperty().addListener((Observable, oldValue, newValue)->{
      filteredlist.setPredicate(car -> {
        if(newValue == null || newValue.isEmpty()){
          return true;
        }

        String text = newValue.toLowerCase();
        if(String.valueOf(car.getId()).indexOf(text) != -1){
          return true;
        }else if(car.getCar_number().toLowerCase().indexOf(text)!= -1){
          return true;
        }else if(car.getCarType().toLowerCase().indexOf(text)!= -1){
          return true;
        }else return false;
      });
    });
    table.setItems(filteredlist);

    refreshButton.setOnAction((e)->{
      book.getCarsFromFile("CarWash.txt",true);
    });

    
  connection.makeConnection(); 
    
   stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (e) -> {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Είστε σίγουροι ότι θέλετε να κλείσετε το πρόγραμμα;");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
        if (result.get() == ButtonType.OK) {
            try {
        
              serverSocket.close();
              File carwash = new File("CarWash.txt");
              PrintWriter writer = new PrintWriter(carwash);
              writer.print("");
              writer.close();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
        }
        else if (result.get() == ButtonType.CANCEL) {
            System.out.println("Cancel");
            e.consume();
        }
   }
});
   
    }



    
    
    public static void main(String[] args) {
        launch(args);
    }

}
