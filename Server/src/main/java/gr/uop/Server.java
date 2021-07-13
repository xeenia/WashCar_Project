package gr.uop;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class Server extends Application {
  
    @Override
    public void start(Stage stage) {   
      
    HBox p_hb_logo = createLogo();
    TableView table = createTable();
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

    IncomeBook book = new IncomeBook();
    
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
        }else if(car.getCar_number().indexOf(text)!= -1){
          return true;
        }else return false;
      });
    });
    table.setItems(filteredlist);
    String filename = "CarWash.txt"; 

    refreshButton.setOnAction((e)->{
      book.getCarsFromFile("CarWash.txt",true);
    });
    new Thread (()->{
      try {
        //δημιουργία υποδοχή εξυπηρετή
        ServerSocket ServerSocket = new ServerSocket(5555);
      while(true){
        
       Socket socket = ServerSocket.accept();
      
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
 
   // Send the filename to the server
  // toServer.println(filename);
 
   
   
   
   

    

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
          tf_search.setPromptText("Search for ID or Car Number");
          tf_search.setPrefWidth(210);
          BorderPane p_bp_search = new BorderPane();
          p_bp_search.setLeft(tf_search);         
          return tf_search;
    }
    public TableView createTable(){
      TableView table = new TableView<>();
      table.setEditable(true);
      table.setPrefHeight(Integer.MAX_VALUE);
      TableColumn idCol = new TableColumn<Car,Integer>("ID");
      TableColumn dateCol = new TableColumn<Car,String>("Date");
      TableColumn timeCol = new TableColumn<Car,String>("Time");
      TableColumn typeCol = new TableColumn<Car,String>("Type");
      TableColumn carCol = new TableColumn<Car,String>("Car Number");
      TableColumn costCol = new TableColumn<Car,Integer>("Cost");
      TableColumn b1Col = new TableColumn<Car,Button>("Acceptance");
      TableColumn b2Col = new TableColumn<Car,Button>("Annulment");

      idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
      dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
      timeCol.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));
      carCol.setCellValueFactory(new PropertyValueFactory<>("car_number"));
      costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
      b1Col.setCellValueFactory(new PropertyValueFactory<>("acceptButton"));
      b2Col.setCellValueFactory(new PropertyValueFactory<>("cancelButton"));
      typeCol.setCellValueFactory(new PropertyValueFactory<>("carType"));

      idCol.setStyle("-fx-alignment: CENTER;");
      dateCol.setStyle("-fx-alignment: CENTER;");
      timeCol.setStyle("-fx-alignment: CENTER;");
      carCol.setStyle("-fx-alignment: CENTER;");
      costCol.setStyle("-fx-alignment: CENTER;");
      b1Col.setStyle("-fx-alignment: CENTER;");
      b2Col.setStyle("-fx-alignment: CENTER;");
      typeCol.setStyle("-fx-alignment: CENTER;");
      
      table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
         
      idCol.minWidthProperty().bind(table.widthProperty().multiply(0.05));
      dateCol.minWidthProperty().bind(table.widthProperty().multiply(.1));
      timeCol.minWidthProperty().bind(table.widthProperty().multiply(.1));
      carCol.minWidthProperty().bind(table.widthProperty().multiply(.1));
      costCol.minWidthProperty().bind(table.widthProperty().multiply(.1));
      b1Col.minWidthProperty().bind(table.widthProperty().multiply(.05));
      b2Col.minWidthProperty().bind(table.widthProperty().multiply(.05));
      typeCol.minWidthProperty().bind(table.widthProperty().multiply(.1));
      table.getColumns().addAll(idCol,dateCol, timeCol,typeCol, carCol, costCol,b1Col,b2Col);
      
      return table;
    }
    public static void main(String[] args) {
        launch(args);
    }

}
