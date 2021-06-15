package gr.uop;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Application;
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
        p_vb_center.getChildren().addAll(createSearchField(),table);
        VBox p_vb_mainPage = new VBox(); 
        p_vb_mainPage.setStyle("-fx-background-color:#abdbe3;");
        p_vb_mainPage.setSpacing(15);
        p_vb_mainPage.setPadding(new Insets(20,20,20,20));
        p_vb_mainPage.getChildren().addAll(createRefreshButton(),p_hb_logo,p_vb_center);
        p_vb_mainPage.setSpacing(0);


        IncomeBook book = new IncomeBook();
        try {
            File myObj = new File("C:/Users/Polyxeni/pl_projects/project-motsi-linardos/Server/src/main/java/gr/uop/info.txt");
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
              String[] data= myReader.nextLine().split(",");
            int id=Integer.parseInt(data[0]);
              String car=data[3];
              String date=data[1];
              String time=data[2];
              int cost=Integer.parseInt(data[4]);
              System.out.println(data[0]+" "+data[1]+ " "+data[2]+ " "+data[3]+ " "+data[4]);
              Car car1 = new Car(id,date,time,car,cost);
              book.addCar(car1);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          table.setItems(book.getCars()); 
          book.addCar(new Car(100,"30-30-30","20:21","HNM2345",100));
          book.deleteCar(3);
        var scene = new Scene(p_vb_mainPage, 1024, 768);

        stage.setScene(scene);
        stage.setMinHeight(768);
        stage.setMinWidth(1024);
        stage.setTitle("CASH DESK");
        stage.show();       
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
  
    public HBox createRefreshButton(){
      Button refreshButton = new Button();
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
          tf_search.setPromptText("Search for time, date or Car Number");
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

      idCol.setStyle("-fx-alignment: CENTER;");
      dateCol.setStyle("-fx-alignment: CENTER;");
      timeCol.setStyle("-fx-alignment: CENTER;");
      carCol.setStyle("-fx-alignment: CENTER;");
      costCol.setStyle("-fx-alignment: CENTER;");
      b1Col.setStyle("-fx-alignment: CENTER;");
      b2Col.setStyle("-fx-alignment: CENTER;");
      
      table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
         
      idCol.minWidthProperty().bind(table.widthProperty().multiply(0.05));
      dateCol.minWidthProperty().bind(table.widthProperty().multiply(.1));
      timeCol.minWidthProperty().bind(table.widthProperty().multiply(.1));
      carCol.minWidthProperty().bind(table.widthProperty().multiply(.25));
      costCol.minWidthProperty().bind(table.widthProperty().multiply(.25));
      b1Col.minWidthProperty().bind(table.widthProperty().multiply(.05));
      b2Col.minWidthProperty().bind(table.widthProperty().multiply(.05));
      table.getColumns().addAll(idCol,dateCol, timeCol, carCol, costCol,b1Col,b2Col);
      
      return table;
    }
    public static void main(String[] args) {
        launch(args);
    }

}
