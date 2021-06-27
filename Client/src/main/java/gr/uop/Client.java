
package gr.uop;

import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.stage.Stage;

public class Client extends Application {

String stringbuilder ="";
TableView<Product> listtable;

    @Override
    public void start(Stage stage) {
      HBox logo = createLogo();
      HBox textPane = new HBox();
      VBox mainPage = new VBox();
 
      TextField text = new TextField();
      text.setPromptText("Please enter your licence plate");
      text.setPrefSize(200, 40);

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
      buttons.put("", space_button);
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
      stage.show();

      for(Map.Entry<String,Button> m : buttons.entrySet()){
        
        m.getValue().setOnAction((e) -> {
          
          if(m.getKey().equals("Enter")){
            Alert alert = new Alert(AlertType.CONFIRMATION, "Your licence plate is " + stringbuilder + "\nTo continue press YES", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            Stage second_stage = new Stage();
            if (alert.getResult() == ButtonType.YES) {
              HBox table_box = productlistWindow();
              VBox scene_box = new VBox();
              VBox bottom_box = new VBox();

              Button cancel_button = new Button("Cancel");
              TextField price_textfield = new TextField();
              price_textfield.setMaxSize(200, 40);
              price_textfield.setPromptText("The total price is shown here");
              price_textfield.setEditable(false);

              bottom_box.getChildren().addAll(price_textfield, cancel_button);
              bottom_box.setAlignment(Pos.CENTER);
              bottom_box.setPadding(new Insets(10, 0, 5, 0));
              bottom_box.setSpacing(10);

              scene_box.getChildren().addAll(table_box, bottom_box);
              scene_box.setStyle("-fx-background-color:#abdbe3;");

              var scene2 = new Scene(scene_box, 700, 400); 
              second_stage.setScene(scene2);
              second_stage.setMinWidth(700);
              second_stage.setMinHeight(400);
              second_stage.setTitle("SERVICES");
              stage.hide();
              second_stage.show();
                  
              System.out.println(price_textfield.getWidth());
              System.out.println(price_textfield.getHeight());
            }
          }else{
            text.setText(m.getKey());
            System.out.println(text.getText());
          }
          stringbuilder+=text.getText();
          System.out.println(stringbuilder); 
          text.setText(stringbuilder);
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
      TableColumn<Product, String> idColumn = new TableColumn<>("ID");
      idColumn.setMinWidth(30);
      idColumn.setSortable(false);
      idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));

      //Name column
      TableColumn<Product, String> nameColumn = new TableColumn<>("Product");
      nameColumn.setMinWidth(200);
      nameColumn.setSortable(false);
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));
      
      //carprice column
      TableColumn<Product, String> carpriceColumn = new TableColumn<>("Car Price");
      carpriceColumn.setMinWidth(100);
      carpriceColumn.setSortable(false);
      carpriceColumn.setCellValueFactory(new PropertyValueFactory<>("car_price"));

      //jeepprice column
      TableColumn<Product, String> jeeppriceColumn = new TableColumn<>("Jeep Price");
      jeeppriceColumn.setMinWidth(100);
      jeeppriceColumn.setSortable(false);
      jeeppriceColumn.setCellValueFactory(new PropertyValueFactory<>("jeep_price"));

      //motorbikeprice column
      TableColumn<Product, String> motorbikepriceColumn = new TableColumn<>("Motorbike Price");
      motorbikepriceColumn.setMinWidth(100);
      motorbikepriceColumn.setSortable(false);
      motorbikepriceColumn.setCellValueFactory(new PropertyValueFactory<>("motorbike_price"));

      listtable = new TableView<>();
      //listtable.setItems(filteredData);
      listtable.setItems(getProduct());
      listtable.getColumns().addAll(idColumn, nameColumn, carpriceColumn, jeeppriceColumn, motorbikepriceColumn);
      //without this line of code we get and extra empty column which we dont need
      listtable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
      VBox radiobuttonsbox = new VBox();
      ObservableList<RadioButton> radiobuttons  = FXCollections.observableArrayList();
      for(int i=0; i<10; i++){
        String s = String.valueOf(i+1);
        RadioButton rb = new RadioButton(s);
        radiobuttons.add(i, rb);
        radiobuttonsbox.getChildren().add(rb);
      }
      radiobuttonsbox.setPadding(new Insets(26, 0, 0, 5));
      radiobuttonsbox.setSpacing(7);

      HBox box = new HBox();
      box.getChildren().addAll(listtable,radiobuttonsbox);
      box.setAlignment(Pos.CENTER);

      return box; 
    }
  
    public static void main(String[] args) {
        launch(args);
    }
}
