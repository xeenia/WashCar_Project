package gr.uop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView; 
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Client extends Application {
  
String stringbuilder ="";
String[] servicesplit;
String data ="";
Button car_button = new Button();
Button jeep_button = new Button();
Button moto_button = new Button();
TextField text = new TextField();
TextField price_textfield = new TextField();
TextField carpricefield = new TextField();
TextField jeeppricefield = new TextField();
TextField motopricefield = new TextField();
TableView<Product> listtable;
TableColumn<Product, String> idColumn ;
TableColumn<Product, String> nameColumn ;
TableColumn<Product, String> carpriceColumn ;
TableColumn<Product, String> jeeppriceColumn ;
TableColumn<Product, String> motorbikepriceColumn ;
ObservableList<RadioButton> radiobuttons  = FXCollections.observableArrayList();
Stage second_stage = new Stage();
Stage stage = new Stage();
//ClientFile clientfile = new ClientFile();
FileHelper file = new FileHelper();
HelperClass helper= new HelperClass();
    @Override
    public void start(Stage stage) {

      
        
    


      this.stage=stage;
      HBox logo = createLogo();
      HBox textPane = new HBox();
      VBox mainPage = new VBox();
      
      text.setPromptText("Please enter your licence plate");
      text.setPrefSize(200, 40);
      text.setEditable(false);
      text.setMouseTransparent(true);
      text.setFocusTraversable(false);

      Button enter_button = new Button("Enter");
      enter_button.setPrefSize(80, 40);
      textPane.getChildren().addAll(text,enter_button);
      textPane.setSpacing(15);
      textPane.setAlignment(Pos.CENTER);

      GridPane lettersPane = new GridPane();
      GridPane.setColumnSpan(lettersPane, 10);
      GridPane.setRowSpan(lettersPane, 3);

      GridPane numbersPane = new GridPane();
      GridPane.setColumnSpan(numbersPane, 3);
      GridPane.setRowSpan(numbersPane, 4);

      lettersPane.setHgap(5);
      lettersPane.setVgap(5);
      lettersPane.setPadding(new Insets(10, 5, 10, 5));

      numbersPane.setHgap(5);
      numbersPane.setVgap(5);
      numbersPane.setPadding(new Insets(10, 5, 10, 5));

      Map<String, Button> buttons = new TreeMap<String, Button>();
      HBox big_keysPane = new HBox();

      //creates keyboard using method from HelperClass
      HelperClass.keyboardLayout(lettersPane,numbersPane,big_keysPane,buttons,enter_button);

      HBox letters_numbersPane = new HBox();
      letters_numbersPane.getChildren().addAll(lettersPane,numbersPane);
      letters_numbersPane.setSpacing(20);
      letters_numbersPane.setAlignment(Pos.CENTER);

      VBox keyboard = new VBox();
      keyboard.getChildren().addAll(letters_numbersPane,big_keysPane);
      keyboard.setSpacing(0);
      keyboard.setAlignment(Pos.CENTER);
      

      mainPage.setStyle("-fx-background-color:#abdbe3;");
      mainPage.setSpacing(15);
      mainPage.setPadding(new Insets(20,20,20,20));
      mainPage.getChildren().addAll(logo,keyboard,textPane);
      mainPage.setSpacing(20);

      var scene = new Scene(mainPage, 1024, 768);
      stage.setScene(scene);
      stage.setMinHeight(768);
      stage.setMinWidth(1024);
      stage.setTitle("CAR WASH");
      stage.setResizable(false);
      stage.show();

      // we simulate every key stroke and create and display our clients licence plate
      for(Map.Entry<String,Button> m : buttons.entrySet()){

        m.getValue().setOnAction((e) -> {
          //when client presses the "Enter" key he is redirected to our services window
          if(m.getKey().equals("Enter")){
            if(!text.getText().isBlank() && text.getText().length()>=2){
              Alert alert = new Alert(AlertType.CONFIRMATION, "Your licence plate is " + stringbuilder + "\nTo continue press YES", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
              alert.showAndWait();
              
              if (alert.getResult() == ButtonType.YES) {
                //calls methos that creates service window
                secondaryScene();
              // reset stringbuilder value
              }else if(alert.getResult() == ButtonType.NO || alert.getResult() == ButtonType.CANCEL){
                stringbuilder = "";
              }
            //wrong type o licence plate warning
            }else{
                Alert warning_alert = new Alert(AlertType.CONFIRMATION, "Please enter a valid license plate" , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                warning_alert.showAndWait();
                stringbuilder = "";
            }
            // redirected to vehicle based service windows
            car_button.setOnAction(car->{
              buttonPressed(car_button);
            });
            jeep_button.setOnAction(jeep->{
              buttonPressed(jeep_button);
            });
            moto_button.setOnAction(moto->{
              buttonPressed(moto_button);
            });
          // simulate backspace and Space keys functionality
          }else{
            if(!m.getKey().equals("Enter") && !m.getKey().equals("Backspace")){
              text.setText(m.getKey());
              System.out.println(text.getText());
            }else if (m.getKey().equals("Backspace")){
              String result = null;
              if ((text.getText() != null) && (text.getText().length() > 0)) {
                result = text.getText().substring(0, text.getText().length() - 1);
                stringbuilder = "";
                text.setText("");
                text.setText(result);
              }
            }
          }
          //fixed a bug where after closing second window the license plate was added to itself
          if(!stringbuilder.equals(text.getText()) || stringbuilder.length()<2){
            stringbuilder+=text.getText();
            System.out.println(stringbuilder); 
            text.setText(stringbuilder);
          }
        });
      }  
      
     

      
      }




     

    // our company's logo
    public HBox createLogo(){
      var lb_incBook = new Label("\t \t \tWelcome!! \n \t Please enter your license plate");
      lb_incBook.setTextFill(Color.web("#FFFFFF"));
      lb_incBook.setFont(Font.font("Arial",FontWeight.BOLD,30));
      StackPane p_st_lbIncBook = new StackPane(lb_incBook);

      Image im_logo = new Image(Client.class.getResourceAsStream("img/logo.png"));
      ImageView iv_logo = new ImageView(im_logo);
      iv_logo.setFitHeight(150);
      iv_logo.setFitWidth(200);

      HBox p_hb_logo = new HBox();
      p_hb_logo.getChildren().addAll(iv_logo,p_st_lbIncBook);
      return p_hb_logo;
    }
    //Creates services list scene
    public void secondaryScene(){
      HBox table_box = productlistWindow();
                VBox scene_box = new VBox();
                VBox bottom_box = new VBox();
                HBox button_box = new HBox();
                
                Button cancel_button = new Button("Cancel");
                cancel_button.setPadding(new Insets(5,5,5,5));
                
                button_box.getChildren().addAll(cancel_button);
                button_box.setAlignment(Pos.CENTER);
                button_box.setSpacing(10);

                bottom_box.getChildren().addAll(button_box);
                bottom_box.setAlignment(Pos.CENTER);
                bottom_box.setPadding(new Insets(10, 0, 10, 0));
                bottom_box.setSpacing(10);

                scene_box.getChildren().addAll(table_box, bottom_box);
                scene_box.setAlignment(Pos.CENTER);
                scene_box.setPadding(new Insets(10, 0, 0, 0));
                scene_box.setStyle("-fx-background-color:#abdbe3;");

                var scene2 = new Scene(scene_box, 1024, 768); 
                second_stage.setScene(scene2);
                second_stage.setMinWidth(800);
                second_stage.setMinHeight(400);
                second_stage.setTitle("SERVICES");
                second_stage.setResizable(false);
                //switch focus to new window
                stage.hide();
                second_stage.show();
                
                System.out.println(stringbuilder);
                second_stage.setOnCloseRequest(event ->{
                  stringbuilder = "";
                  price_textfield.setText("");
                  //text.setText("");
                  second_stage.close();
                  stage.show();
                });

                cancel_button.setOnAction(c->{
                  Alert cancel_alert = new Alert(AlertType.CONFIRMATION,"To get back to main menu press YES", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                  cancel_alert.showAndWait();
                  if (cancel_alert.getResult() == ButtonType.YES) {
                    price_textfield.setText("");
                    second_stage.close();
                    stage.show();
                  }
                });
    }

    //created services ObservableList which will use to make our services' TableViews
    public ObservableList<Product> getProduct(){
      ObservableList<Product> products = FXCollections.observableArrayList();
      products.add(new Product(1, "Πλύσιμο εξωτερικό", "7", "8", "6"));
      products.add(new Product(2, "Πλύσιμο εσωτερικό", "6", "7", "-"));
      products.add(new Product(3, "Πλύσιμο εξωτ.+εσωτ.", "12", "14", "-"));
      products.add(new Product(4, "Πλύσιμο εξωτ. σπέσιαλ", "9", "10", "8"));
      products.add(new Product(5, "Πλύσιμο εσωτ. σπέσιαλ", "8", "9", "-"));
      products.add(new Product(6, "Πλύσιμο εξωτ. + εσωτ. σπέσιαλ", "15", "17", "-"));
      products.add(new Product(7, "Βιολογικός καθαρισμός εσωτ.", "80", "80", "-"));
      products.add(new Product(8, "Κέρωμα‐Γυάλισμα", "80", "90", "40"));
      products.add(new Product(9, "Καθαρισμός κινητήρα", "20", "20", "10"));
      products.add(new Product(10, "Πλύσιμο σασί", "3", "3", "-"));
      return products;
    }

    //created service list with all vehicle prices
    public HBox productlistWindow(){

      //Id column
      idColumn = new TableColumn<>("ID");
      idColumn.setMinWidth(30);
      idColumn.setSortable(false);
      idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));
      //prevent column resize to stop a bug which made listtable expand
      idColumn.setResizable(false);

      //Name column
      nameColumn = new TableColumn<>("Υπηρεσία");
      nameColumn.setMinWidth(200);
      nameColumn.setSortable(false);
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));
      //prevent column resize to stop a bug which made listtable expand
      nameColumn.setResizable(false);
      
      //carprice column
      carpriceColumn = new TableColumn<>("Τιμή Αμαξιού");
      carpriceColumn.setMinWidth(100);
      carpriceColumn.setSortable(false);
      carpriceColumn.setCellValueFactory(new PropertyValueFactory<>("car_price"));
      //prevent column resize to stop a bug which made listtable expand
      carpriceColumn.setResizable(false);

      //jeepprice column
      jeeppriceColumn = new TableColumn<>("Τιμή Τζιπ");
      jeeppriceColumn.setMinWidth(100);
      jeeppriceColumn.setSortable(false);
      jeeppriceColumn.setCellValueFactory(new PropertyValueFactory<>("jeep_price"));
      //prevent column resize to stop a bug which made listtable expand
      jeeppriceColumn.setResizable(false);

      //motorbikeprice column
      motorbikepriceColumn = new TableColumn<>("Τιμή Μηχανής");
      motorbikepriceColumn.setMinWidth(100);
      motorbikepriceColumn.setSortable(false);
      motorbikepriceColumn.setCellValueFactory(new PropertyValueFactory<>("motorbike_price"));
      //prevent column resize to stop a bug which made listtable expand
      motorbikepriceColumn.setResizable(false);

      listtable = new TableView<>();
      listtable.setItems(getProduct());
      listtable.getColumns().addAll(nameColumn, carpriceColumn, jeeppriceColumn, motorbikepriceColumn);
      //without this line of code we get and extra empty column which we dont need
      listtable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      listtable.setEditable(false);  
      listtable.setMouseTransparent(true);
      listtable.setFocusTraversable(false);
      
      car_button.setPrefSize(50, 50);
      car_button.setPadding(Insets.EMPTY);
      Image car_logo = new Image(Client.class.getResourceAsStream("img/car_logo.jpg"));
      ImageView ivcar_logo = new ImageView(car_logo);
      //ivcar_logo.setPreserveRatio(true);
      ivcar_logo.setFitHeight(50);
      ivcar_logo.setFitWidth(50);
      car_button.setGraphic(ivcar_logo);

     
      jeep_button.setPrefSize(50, 50);
      jeep_button.setPadding(Insets.EMPTY);
      Image jeep_logo = new Image(Client.class.getResourceAsStream("img/jeep_logo.png"));
      ImageView ivjeep_logo = new ImageView(jeep_logo);
      //ivjeep_logo.setPreserveRatio(true);
      ivjeep_logo.setFitHeight(50);
      ivjeep_logo.setFitWidth(50);
      jeep_button.setGraphic(ivjeep_logo);
      
      moto_button.setPrefSize(50, 50);
      moto_button.setPadding(Insets.EMPTY);
      Image moto_logo = new Image(Client.class.getResourceAsStream("img/moto_logo.png"));
      ImageView ivmoto_logo = new ImageView(moto_logo);
      //ivjeep_logo.setPreserveRatio(true);
      ivmoto_logo.setFitHeight(50);
      ivmoto_logo.setFitWidth(50);
      moto_button.setGraphic(ivmoto_logo);
      
      VBox button_box = new VBox();
      button_box.setPadding(new Insets(70, 10, 0, 10));
      button_box.setSpacing(10);
      button_box.getChildren().addAll(car_button,jeep_button,moto_button);

      HBox box = new HBox();
      box.getChildren().addAll(listtable,button_box);
      box.setAlignment(Pos.CENTER);

      return box; 
    }

    //calls vehicle's scene 
    public void buttonPressed(Button button){
      if(button.getGraphic().equals(car_button.getGraphic())){
        carScene(button);
      }else if(button.getGraphic().equals(jeep_button.getGraphic())){
        jeepScene(button);
      }else if(button.getGraphic().equals(moto_button.getGraphic())){
        motoScene(button);
      }
    }

    //car scene
    public void carScene(Button button) {

      TableView carlist = new TableView<>();
      carlist.setItems(getProduct());
      carlist.getColumns().addAll(idColumn, nameColumn, carpriceColumn);
      //without this line of code we get and extra empty column which we dont need
      carlist.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      carlist.setEditable(false);  
      carlist.setMouseTransparent(true);
      carlist.setFocusTraversable(false);

      carpricefield.setPromptText("Services price");
      carpricefield.setEditable(false);
      carpricefield.setMouseTransparent(true);
      carpricefield.setFocusTraversable(false);

      Button carconfirmbutton = new Button("Confirm");

      VBox radioButtonsVBox = radiobuttonbox(button);

      HBox carbox = new HBox();
      carbox.setAlignment(Pos.CENTER);
      carbox.getChildren().addAll(carlist,radioButtonsVBox);

      HBox carpricebox = new HBox();
      carpricebox.setAlignment(Pos.CENTER);
      carpricebox.setPadding(new Insets(10, 0, 0, 0));
      carpricebox.setSpacing(10);
      carpricebox.getChildren().addAll(carpricefield,carconfirmbutton);

      VBox car = new VBox();
      car.setStyle("-fx-background-color:#abdbe3;");
      car.setPadding(new Insets(10, 0, 10, 0));
      car.setAlignment(Pos.CENTER);
      car.getChildren().addAll(carbox, carpricebox);
      
      Stage car_stage = new Stage();
      var carscene = new Scene(car,500,350);

      car_stage.setScene(carscene);
      car_stage.setTitle("CAR SERVICES");
      //switch focus to new window
      car_stage.initModality(Modality.APPLICATION_MODAL);
      car_stage.show();
      //prevent full screen
      car_stage.setResizable(false);

      //send total price to second stage plus resetting price
      carconfirmbutton.setOnAction(e->{
        Alert confirm_alert = new Alert(AlertType.CONFIRMATION,"Your total is: "+ carpricefield.getText() + "€"  + "\nIf you want to continue with payment press YES", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        confirm_alert.showAndWait();
        if (confirm_alert.getResult() == ButtonType.YES) {
          List<String> selectedValues = new ArrayList<String>();
          for(RadioButton b  : radiobuttons){
            if(b.isSelected()){
              selectedValues.add(b.getText());
            }
          }
          String rbData = String.join("-", selectedValues);
          System.out.println(rbData);
          // String data ="";
          //write data in file type(licence plate, vehicle type,service id, service price for car)
          data = String.join(",", stringbuilder, "Car", rbData, carpricefield.getText());
          file.WriteToFile(data);
          rbData="";
          data="";
          for(RadioButton b  : radiobuttons){
            if(b.isSelected()){
              //selectedValues.add(b.getText());
              b.setSelected(false);
            }
          }
          carpricefield.setText("");
          car_stage.close();
          second_stage.close();
          stringbuilder = "";
          text.setText("");
          stage.show();
        }
        else if (confirm_alert.getResult() == ButtonType.CANCEL) {
          carpricefield.setText("");
          car_stage.close();
          second_stage.show();
        }

        
      });

      //reset price on close
      car_stage.setOnCloseRequest(event ->{
        carpricefield.setText("");
        car_stage.close();
        second_stage.show();
      });
    }

    //jeep scene
    public void jeepScene(Button button) {

      TableView jeeplist = new TableView<>();
      jeeplist.setItems(getProduct());
      jeeplist.getColumns().addAll(idColumn, nameColumn, jeeppriceColumn);
      //without this line of code we get and extra empty column which we dont need
      jeeplist.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      jeeplist.setEditable(false);  
      jeeplist.setMouseTransparent(true);
      jeeplist.setFocusTraversable(false);

      jeeppricefield.setPromptText("Services price");
      jeeppricefield.setEditable(false);
      jeeppricefield.setMouseTransparent(true);
      jeeppricefield.setFocusTraversable(false);

      Button jeepconfirmbutton = new Button("Confirm");

      VBox radioButtonsVBox = radiobuttonbox(button);

      HBox jeepbox = new HBox();
      jeepbox.setAlignment(Pos.CENTER);
      jeepbox.getChildren().addAll(jeeplist,radioButtonsVBox);

      HBox jeeppricebox = new HBox();
      jeeppricebox.setAlignment(Pos.CENTER);
      jeeppricebox.setPadding(new Insets(10, 0, 0, 0));
      jeeppricebox.setSpacing(10);
      jeeppricebox.getChildren().addAll(jeeppricefield,jeepconfirmbutton);
      
      VBox jeep = new VBox();
      jeep.setStyle("-fx-background-color:#abdbe3;");
      jeep.setPadding(new Insets(10, 0, 10, 0));
      jeep.setAlignment(Pos.CENTER);
      jeep.getChildren().addAll(jeepbox, jeeppricebox);
      
      Stage jeep_stage = new Stage();
      var jeepscene = new Scene(jeep,500,350);

      jeep_stage.setScene(jeepscene);
      jeep_stage.setTitle("JEEP SERVICES");
      //switch focus to new window
      jeep_stage.initModality(Modality.APPLICATION_MODAL);
      jeep_stage.show();
      //prevent full screen
      jeep_stage.setResizable(false);

      //send total price to second stage plus reseting price
      jeepconfirmbutton.setOnAction(e->{
        Alert confirm_alert = new Alert(AlertType.CONFIRMATION,"Your total is: "+ jeeppricefield.getText() + "€"  + "\nIf you want to continue with payment press YES", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        confirm_alert.showAndWait();
        if (confirm_alert.getResult() == ButtonType.YES) {
          List<String> selectedValues = new ArrayList<String>();
          for(RadioButton b  : radiobuttons){
            if(b.isSelected()){
              selectedValues.add(b.getText());
            }
          }
          String rbData = String.join("-", selectedValues);
          System.out.println(rbData);
          //String data ="";
          data = String.join(",", stringbuilder, "Jeep", rbData, jeeppricefield.getText());
          file.WriteToFile(data);
          rbData="";
          data="";
          for(RadioButton b  : radiobuttons){
            if(b.isSelected()){
              b.setSelected(false);
            }
          }

          jeeppricefield.setText("");
          jeep_stage.close();
          second_stage.close();
          stringbuilder = "";
          text.setText("");
          stage.show();
        }else if (confirm_alert.getResult() == ButtonType.CANCEL) {
          jeeppricefield.setText("");
          jeep_stage.close();
          second_stage.show();
        }

        
      });

      //reset price on close
      jeep_stage.setOnCloseRequest(event ->{
        jeeppricefield.setText("");
        jeep_stage.close();
        second_stage.show();
      });
      
    }

    //motorbike scene
    public void motoScene(Button button) {
      
      TableView motolist = new TableView<>();
      motolist.setItems(getProduct());
      motolist.getColumns().addAll(idColumn, nameColumn, motorbikepriceColumn);
      //without this line of code we get and extra empty column which we dont need
      motolist.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      motolist.setEditable(false);  
      motolist.setMouseTransparent(true);
      motolist.setFocusTraversable(false);

      motopricefield.setPromptText("Services price");
      motopricefield.setEditable(false);
      motopricefield.setMouseTransparent(true);
      motopricefield.setFocusTraversable(false);

      Button motoconfirmbutton = new Button("Confirm");

      HBox motolistbox = new HBox();
      motolistbox.getChildren().addAll(motolist);

      VBox radioButtonsVBox = radiobuttonbox(button);
      HBox motobox = new HBox();

      motobox.setAlignment(Pos.CENTER);
      motobox.getChildren().addAll(motolistbox, radioButtonsVBox);

      HBox motopricebox = new HBox();
      motopricebox.setAlignment(Pos.CENTER);
      motopricebox.setPadding(new Insets(10, 0, 0, 0));
      motopricebox.setSpacing(10);
      motopricebox.getChildren().addAll(motopricefield,motoconfirmbutton);

      VBox moto = new VBox();
      moto.setStyle("-fx-background-color:#abdbe3;");
      moto.setPadding(new Insets(10, 0, 10, 0));
      moto.setAlignment(Pos.CENTER);
      moto.getChildren().addAll(motobox, motopricebox);

      Stage moto_stage = new Stage();
      var motoscene = new Scene(moto,500,350);

      moto_stage.setScene(motoscene);
      moto_stage.setTitle("MOTORBIKE SERVICES");
      //switch focus to new window
      moto_stage.initModality(Modality.APPLICATION_MODAL);
      moto_stage.show();
      //prevent full screen
      moto_stage.setResizable(false);

      //send total price to second stage plus reseting price
      motoconfirmbutton.setOnAction(e->{
        Alert confirm_alert = new Alert(AlertType.CONFIRMATION,"Your total is: "+ motopricefield.getText() + "€"  + "\nIf you want to continue with payment press YES", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        confirm_alert.showAndWait();
        if (confirm_alert.getResult() == ButtonType.YES) {
          List<String> selectedValues = new ArrayList<String>();
          for(RadioButton b  : radiobuttons){
            if(b.isSelected()){
              selectedValues.add(b.getText());
            }
          }
          String rbData = String.join("-", selectedValues);
          //String data ="";
          data = String.join(",", stringbuilder, "Motorbike", rbData, motopricefield.getText());
          file.WriteToFile(data);
          rbData="";
          data="";
          for(RadioButton b  : radiobuttons){
            if(b.isSelected()){
              b.setSelected(false);
            }
          }
          motopricefield.setText("");
          moto_stage.close();
          second_stage.close();
          stringbuilder = "";
          text.setText("");
          stage.show();
        }else if (confirm_alert.getResult() == ButtonType.CANCEL) {
          motopricefield.setText("");
          moto_stage.close();
          second_stage.show();
        }
      });

      //reset price on close
      moto_stage.setOnCloseRequest(event ->{
        motopricefield.setText("");
        moto_stage.close();
        second_stage.show();
      });
    }

    // radiobuttons created plus restrictions
    public VBox radiobuttonbox(Button button){

      VBox rbbox = new VBox();
      // radiobuttons automaticly created 
     
      for(int i=0; i<10; i++){
        String s = String.valueOf(i+1);
        RadioButton rb = new RadioButton(s);
        radiobuttons.add(i, rb);
        rbbox.getChildren().add(rb);
      }
      rbbox.setPadding(new Insets(26, 0, 0, 5));
      rbbox.setSpacing(7);

      // cases when car button is pressed 
      if(button.getGraphic().equals(car_button.getGraphic())){
        helper.carRadiobuttons(radiobuttons, carpricefield);
      // cases when jeep button is pressed
      }else if(button.getGraphic().equals(jeep_button.getGraphic())){
        helper.jeepRadiobuttons(radiobuttons, jeeppricefield);
      // cases when motorbike button is pressed
      }else if(button.getGraphic().equals(moto_button.getGraphic())){
        helper.motoRadiobuttons(radiobuttons, motopricefield);
      }
      return rbbox;
    }
    
    public static void main(String[] args) {
        launch(args);
        
    }
}
