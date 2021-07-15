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
import javafx.scene.input.MouseEvent;
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
    IncomeBook book = new IncomeBook();
    HBox p_hb_logo = createLogo();
    TableView table = book.createTable();
    VBox p_vb_center = new VBox();
    TextField tx = createSearchField();
    p_vb_center.getChildren().addAll(tx,table);
    VBox p_vb_mainPage = new VBox(); 
    p_vb_mainPage.setStyle("-fx-background-color:#abdbe3;");
    p_vb_mainPage.setSpacing(15);
    p_vb_mainPage.setPadding(new Insets(20,20,20,20));
    Button refreshButton = new Button();
    p_vb_mainPage.getChildren().addAll(createRefreshButton(refreshButton),p_hb_logo,p_vb_center);
    p_vb_mainPage.setSpacing(0);

    
    table.setItems(book.getCars()); 


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
    String filename = "CarWash.txt"; 

    refreshButton.setOnAction((e)->{
      book.getCarsFromFile("CarWash.txt",true);
    });


    connect(serverSocket);
    
   stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (e) -> {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Είστε σίγουροι ότι θέλετε να κλείσετε το πρόγραμμα;");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
        if (result.get() == ButtonType.OK) {
            try {
              connect(serverSocket);
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

 
   
   
   
   

    public void connect(ServerSocket serversocket){
      new Thread (()->{
        try {
          //δημιουργία υποδοχή εξυπηρετή
          
          
        while(true){
          
         Socket socket = serverSocket.accept();
        
         Scanner fromClient = new Scanner(socket.getInputStream());
         //παίρνουμε την πληροφορία από τον client
         String carInfo = fromClient.nextLine();
         try {
          PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("CarWash.txt", true)));
          PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("SavedCars.txt", true)));
          out.print(carInfo+",");
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
          out.println(java.time.LocalDate.now()+","+java.time.LocalTime.now().format(dtf));
          out.close();
          out2.print(carInfo+",");
          out2.println(java.time.LocalDate.now()+","+java.time.LocalTime.now().format(dtf));
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

    public HBox createLogo(){
      var lb_incBook = new Label("Income Book");
      lb_incBook.setTextFill(Color.web("#FFFFFF"));
      lb_incBook.setFont(Font.font("Arial",FontWeight.BOLD,30));
      StackPane p_st_lbIncBook = new StackPane(lb_incBook);
  
      Image im_logo = new Image(Server.class.getResourceAsStream("img/logo.png"));
      ImageView iv_logo = new ImageView(im_logo);
      iv_logo.setFitHeight(150);
      iv_logo.setFitWidth(200);
  
      HBox p_hb_logo = new HBox(); 
      p_hb_logo.getChildren().addAll(iv_logo,p_st_lbIncBook);
      return p_hb_logo;
    }
  
    public HBox createRefreshButton(Button refreshButton){
      refreshButton.setPrefSize(60, 60);
      Image im_refresh = new Image(Server.class.getResourceAsStream("img/re2.png"));
      ImageView refresh_icon = new ImageView(im_refresh);
      refresh_icon.setFitHeight(20);
      refresh_icon.setFitWidth(20);
      refreshButton.setGraphic(refresh_icon);
      refreshButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;");
      refreshButton.setOnMousePressed(event -> refreshButton.setStyle("-fx-background-color: transparent; -fx-padding: 3 1 1 3;"));
      refreshButton.setOnMouseReleased(event -> refreshButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;"));
      HBox p_hb_refreshButton = new HBox();
      p_hb_refreshButton.getChildren().add(refreshButton);
      p_hb_refreshButton.setAlignment(Pos.TOP_RIGHT);
      p_hb_refreshButton.setPadding(new Insets(0));
  
      return p_hb_refreshButton;
    }
  
    public TextField createSearchField(){
          TextField tf_search = new TextField();
          tf_search.setPromptText("Αναζητήστε ID, Αριθμό Πινακίδας ή Όχημα");
          tf_search.setPrefWidth(210);
          BorderPane p_bp_search = new BorderPane();
          p_bp_search.setLeft(tf_search);         
          return tf_search;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
