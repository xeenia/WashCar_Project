
package gr.uop;

import java.util.Map;
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
TableView<Product> listtable;
int price = 0;
Button car_button = new Button();
Button jeep_button = new Button();
Button moto_button = new Button();
Stage second_stage = new Stage();

TableColumn<Product, String> idColumn ;
TableColumn<Product, String> nameColumn ;
TableColumn<Product, String> carpriceColumn ;
TableColumn<Product, String> jeeppriceColumn ;
TableColumn<Product, String> motorbikepriceColumn ;

    @Override
    public void start(Stage stage) {
      HBox logo = createLogo();
      HBox textPane = new HBox();
      VBox mainPage = new VBox();
 
      TextField text = new TextField();
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

      String[] letters = new String[]{"Q","W","E","R","T","Y","U","I","O","P",
                                      "A","S","D","F","G","H","J","K","L",
                                      "Z","X","C","V","B","N","M"};
      
      String[] numbers = new String[]{"7","8","9",
                                      "4","5","6",
                                      "1","2","3"};

      Map<String, Button> buttons = new TreeMap<String, Button>();
      
      var index=0;
      for(int i=0; i< 3; i++){
        for(int j=0; j< 10; j++){
          if(index == 0 ){
            Button letter_button = new Button(letters[index]);
            letter_button.setPrefSize(40, 40);
            lettersPane.add(letter_button,j,i);
            buttons.put(letters[index], letter_button);
          }else if(index == 26){
            break;
          }
          
          if((index!=0) && (!letters[index-1].equals("P") || !letters[index-1].equals("L"))){
            Button letter_button = new Button(letters[index]);
            letter_button.setPrefSize(40, 40);
            lettersPane.add(letter_button,j,i);
            buttons.put(letters[index], letter_button);
            if(letters[index].equals("L")){
              j=j+2;
              index++;
              continue;
            }
          }
          index++;
        }
      }

      index=0;
      for(int i=0; i< 3; i++){
        for(int j=0; j< 3; j++){
          if(index == 0 ){
            Button number_button = new Button(numbers[index]);
            number_button.setPrefSize(40, 40);
            numbersPane.add(number_button,j,i);
            buttons.put(numbers[index], number_button);
          }else if(index == 10){
            break;
          }
          Button number_button = new Button(numbers[index]);
          number_button.setPrefSize(40, 40);
          numbersPane.add(number_button,j,i);
          buttons.put(numbers[index], number_button);
          index++;
        }
      }

      Button backspace_button = new Button ("Backspace");
      backspace_button.setPrefSize(95, 40);
      Button space_button = new Button ();
      space_button.setPrefSize(340, 40);
      Button zero_button = new Button ("0");
      zero_button.setPrefSize(130, 40);

      buttons.put("Backspace", backspace_button);
      buttons.put(" ", space_button);
      buttons.put("0", zero_button);
      buttons.put("Enter", enter_button);

      HBox big_keysPane = new HBox();
      big_keysPane.getChildren().addAll(space_button,backspace_button,zero_button);
      big_keysPane.setSpacing(20);
      big_keysPane.setAlignment(Pos.CENTER);

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

      for(Map.Entry<String,Button> m : buttons.entrySet()){

        m.getValue().setOnAction((e) -> {
          if(m.getKey().equals("Enter")){
            if(!text.getText().isBlank() && text.getText().length()>=2){
              Alert alert = new Alert(AlertType.CONFIRMATION, "Your licence plate is " + stringbuilder + "\nTo continue press YES", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
              alert.showAndWait();
              
              if (alert.getResult() == ButtonType.YES) {
                HBox table_box = productlistWindow();
                VBox scene_box = new VBox();
                VBox bottom_box = new VBox();
                HBox button_box = new HBox();

                Button checkout_button = new Button ("Ckeckout");
                
                Button cancel_button = new Button("Cancel");
                
                TextField price_textfield = new TextField();
                price_textfield.setMaxSize(200, 40);
                price_textfield.setPromptText("The total price is shown here");
                price_textfield.setEditable(false);
                price_textfield.setMouseTransparent(true);
                price_textfield.setFocusTraversable(false);

                button_box.getChildren().addAll(checkout_button,cancel_button);
                button_box.setAlignment(Pos.CENTER);
                button_box.setSpacing(10);

                bottom_box.getChildren().addAll(price_textfield, button_box);
                bottom_box.setAlignment(Pos.CENTER);
                bottom_box.setPadding(new Insets(10, 0, 10, 0));
                bottom_box.setSpacing(10);

                scene_box.getChildren().addAll(table_box, bottom_box);
                scene_box.setStyle("-fx-background-color:#abdbe3;");

                var scene2 = new Scene(scene_box, 700, 400); 
                second_stage.setScene(scene2);
                second_stage.setMinWidth(800);
                second_stage.setMinHeight(400);
                second_stage.setTitle("SERVICES");
                second_stage.setResizable(false);
                stage.hide();
                second_stage.show();
                //checkout and cancel buttons same size
                cancel_button.setPrefWidth(checkout_button.getWidth());
                System.out.println(stringbuilder);
                second_stage.setOnCloseRequest(event ->{
                  stringbuilder = "";
                  //text.setText("");
                  second_stage.hide();
                  stage.show();
                });
                cancel_button.setOnAction(c->{
                  Alert cancel_alert = new Alert(AlertType.CONFIRMATION,"To get back to main menu press YES", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                  cancel_alert.showAndWait();
                  if (cancel_alert.getResult() == ButtonType.YES) {
                    second_stage.hide();
                    stage.show();
                  }
                });
              }else if(alert.getResult() == ButtonType.NO || alert.getResult() == ButtonType.CANCEL){
                stringbuilder = "";
              }
            }else{
                Alert warning_alert = new Alert(AlertType.CONFIRMATION, "Please enter a valid license plate" , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                warning_alert.showAndWait();
                stringbuilder = "";
            }
            car_button.setOnAction(car->{
              buttonPressed(car_button);
            });
            jeep_button.setOnAction(jeep->{
              buttonPressed(jeep_button);
            });
            moto_button.setOnAction(moto->{
              buttonPressed(moto_button);
            });
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

    public HBox createLogo(){
      var lb_incBook = new Label("Income Book");
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
    public HBox productlistWindow(){

      //Id column
      idColumn = new TableColumn<>("ID");
      idColumn.setMinWidth(30);
      idColumn.setSortable(false);
      idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));
      //prevent column resize to stop a bug which made listtable expand
      idColumn.setResizable(false);

      //Name column
      nameColumn = new TableColumn<>("Product");
      nameColumn.setMinWidth(200);
      nameColumn.setSortable(false);
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));
      //prevent column resize to stop a bug which made listtable expand
      nameColumn.setResizable(false);
      
      //carprice column
      carpriceColumn = new TableColumn<>("Car Price");
      carpriceColumn.setMinWidth(100);
      carpriceColumn.setSortable(false);
      carpriceColumn.setCellValueFactory(new PropertyValueFactory<>("car_price"));
      //prevent column resize to stop a bug which made listtable expand
      carpriceColumn.setResizable(false);

      //jeepprice column
      jeeppriceColumn = new TableColumn<>("Jeep Price");
      jeeppriceColumn.setMinWidth(100);
      jeeppriceColumn.setSortable(false);
      jeeppriceColumn.setCellValueFactory(new PropertyValueFactory<>("jeep_price"));
      //prevent column resize to stop a bug which made listtable expand
      jeeppriceColumn.setResizable(false);

      //motorbikeprice column
      motorbikepriceColumn = new TableColumn<>("Motorbike Price");
      motorbikepriceColumn.setMinWidth(100);
      motorbikepriceColumn.setSortable(false);
      motorbikepriceColumn.setCellValueFactory(new PropertyValueFactory<>("motorbike_price"));
      //prevent column resize to stop a bug which made listtable expand
      motorbikepriceColumn.setResizable(false);

      listtable = new TableView<>();
      listtable.setItems(getProduct());
      listtable.getColumns().addAll(idColumn, nameColumn, carpriceColumn, jeeppriceColumn, motorbikepriceColumn);
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

    public void buttonPressed(Button button){
      if(button.getGraphic().equals(car_button.getGraphic())){
        carScene(button);
      }else if(button.getGraphic().equals(jeep_button.getGraphic())){
        jeepScene(button);
      }else if(button.getGraphic().equals(moto_button.getGraphic())){
        motoScene(button);
      }
    }

    public void carScene(Button button) {

      TableView carlist = new TableView<>();
      carlist.setItems(getProduct());
      carlist.getColumns().addAll(idColumn, nameColumn, carpriceColumn);
      //without this line of code we get and extra empty column which we dont need
      carlist.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      carlist.setEditable(false);  
      carlist.setMouseTransparent(true);
      carlist.setFocusTraversable(false);

      VBox radiobuttons = radiobuttonbox(button);

      HBox carbox = new HBox();
      carbox.getChildren().addAll(carlist,radiobuttons);
      
      Stage car_stage = new Stage();
      var carscene = new Scene(carbox,500,300);

      car_stage.setScene(carscene);
      car_stage.setTitle("CAR SERVICES");
      //switch focus to new window
      car_stage.initModality(Modality.APPLICATION_MODAL);
      car_stage.show();
      //prevent full screen
      car_stage.setResizable(false);
    }

    public void jeepScene(Button button) {

      TableView jeeplist = new TableView<>();
      jeeplist.setItems(getProduct());
      jeeplist.getColumns().addAll(idColumn, nameColumn, jeeppriceColumn);
      //without this line of code we get and extra empty column which we dont need
      jeeplist.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      jeeplist.setEditable(false);  
      jeeplist.setMouseTransparent(true);
      jeeplist.setFocusTraversable(false);

      VBox radiobuttons = radiobuttonbox(button);

      HBox jeepbox = new HBox();
      jeepbox.getChildren().addAll(jeeplist,radiobuttons);

      Stage jeep_stage = new Stage();
      var jeepscene = new Scene(jeepbox,500,300);

      jeep_stage.setScene(jeepscene);
      jeep_stage.setTitle("JEEP SERVICES");
      //switch focus to new window
      jeep_stage.initModality(Modality.APPLICATION_MODAL);
      jeep_stage.show();
      //prevent full screen
      jeep_stage.setResizable(false);
      
    }

    public void motoScene(Button button) {
      
      TableView motolist = new TableView<>();
      motolist.setItems(getProduct());
      motolist.getColumns().addAll(idColumn, nameColumn, motorbikepriceColumn);
      //without this line of code we get and extra empty column which we dont need
      motolist.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      motolist.setEditable(false);  
      motolist.setMouseTransparent(true);
      motolist.setFocusTraversable(false);

      HBox motolistbox = new HBox();
      motolistbox.getChildren().addAll(motolist);

      VBox radiobuttons = radiobuttonbox(button);

      HBox motobox = new HBox();
      motobox.getChildren().addAll(motolistbox, radiobuttons);

      Stage moto_stage = new Stage();
      var motoscene = new Scene(motobox,500,300);

      moto_stage.setScene(motoscene);
      moto_stage.setTitle("MOTORBIKE SERVICES");
      //switch focus to new window
      moto_stage.initModality(Modality.APPLICATION_MODAL);
      moto_stage.show();
      //prevent full screen
      moto_stage.setResizable(false);
    }

    public VBox radiobuttonbox(Button button){

      VBox rbbox = new VBox();

      ObservableList<RadioButton> radiobuttons  = FXCollections.observableArrayList();
      for(int i=0; i<10; i++){
        String s = String.valueOf(i+1);
        RadioButton rb = new RadioButton(s);
        radiobuttons.add(i, rb);
        rbbox.getChildren().add(rb);
      }
      rbbox.setPadding(new Insets(26, 0, 0, 5));
      rbbox.setSpacing(7);

      if(button.getGraphic().equals(car_button.getGraphic())){
        for(RadioButton b  : radiobuttons){
          b.setOnAction((k)->{
            switch (b.getText()) {
              case "1":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "2":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("3") || rb.getText().equals("5") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("3") || rb.getText().equals("5") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "3":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("4") || rb.getText().equals("5") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("4") || rb.getText().equals("5") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "4":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("1") || rb.getText().equals("3") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("3") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "5":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "6":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("5")){
                    rb.setDisable(true);
                  }
                }
                //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("5")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "7":
                
                break;
              case "8":
                
                break;
              case "9": 
                
                break;
              case "10":

                break;
            }
          });
        }
      // radiobuttons for jeep window
      }else if(button.getGraphic().equals(jeep_button.getGraphic())){
        for(RadioButton b  : radiobuttons){
          b.setOnAction((k)->{
            switch (b.getText()) {
              case "1":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "2":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("3") || rb.getText().equals("5") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                 //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("3") || rb.getText().equals("5") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "3":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("4") || rb.getText().equals("5") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                 //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("4") || rb.getText().equals("5") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "4":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("1") || rb.getText().equals("3") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                 //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("3") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "5":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("6")){
                    rb.setDisable(true);
                  }
                }
                 //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("6")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "6":
              //disables radiobuttons
                for(RadioButton rb  : radiobuttons){
                  if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("5")){
                    rb.setDisable(true);
                  }
                }
                 //reactivates disabled radiobuttons
                if(!b.isSelected()){
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("5")){
                      rb.setDisable(false);
                    }
                  }
                }
                break;
              case "7":
             
                break;
              case "8":
                
                break;
              case "9":
                
                break;
              case "10":
                
                break;
            }
          });
        }
      // radiobuttons ofr motorbike window
      }else if(button.getGraphic().equals(moto_button.getGraphic())){
        for(RadioButton b  : radiobuttons){
          if(b.getText().equals("2") || b.getText().equals("3") || b.getText().equals("5") || b.getText().equals("6") || b.getText().equals("7") || b.getText().equals("10")){
            b.setDisable(true);
          }else{
            b.setOnAction((k)->{
              switch (b.getText()) {
                case "1":
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("4")){
                      rb.setDisable(true);
                    }
                  }
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("4")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  break;
                case "2":
                 
                  break;
                case "3":
                  
                  break;
                case "4":
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1")){
                      rb.setDisable(true);
                    }
                  }
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("1")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  break;
                case "5":
                  
                  break;
                case "6":
                  
                  break;
                case "7":
                  
                  break;
                case "8":
                  
                  break;
                case "9":
                  
                  break;
                case "10":
                  
                  break;
              }
            });
          }
        }
        //System.out.println("moto");
      }
      return rbbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
