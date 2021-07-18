package gr.uop;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Server extends Application {
      
    @Override
    public void start(Stage stage) {   
    Connection connection = new Connection();
    IncomeBook book = new IncomeBook();
    mainUI ui = new mainUI();

    VBox p_vb_center = new VBox();
    TextField tx = ui.createSearchField();
    TableView table = book.createTable();
    table.setItems(book.getVehicles()); 
    p_vb_center.getChildren().addAll(tx,table);
    VBox p_vb_mainPage = ui.createMainPage();
    Button refreshButton = new Button();
    p_vb_mainPage.getChildren().addAll(ui.createRefreshButton(refreshButton),ui.createLogo(),p_vb_center);
    var scene = new Scene(p_vb_mainPage, 1024, 768);
    stage.setScene(scene);
    stage.setMinHeight(768);
    stage.setMinWidth(1024);
    stage.setTitle("CASH DESK");
    stage.show();   
    
    FilteredList<Vehicle> filteredlist = new FilteredList<>(book.getVehicles(), b-> true);
    tx.textProperty().addListener((Observable, oldValue, newValue)->{
      filteredlist.setPredicate(car -> {
        if(newValue == null || newValue.isEmpty()){
          return true;
        }
        String text = newValue.toLowerCase();
        if(String.valueOf(car.getId()).indexOf(text) != -1){
          return true;
        }else if(car.getVehicle_number().toLowerCase().indexOf(text)!= -1){
          return true;
        }else if(car.getVehicleType().toLowerCase().indexOf(text)!= -1){
          return true;
        }else return false;
      });
    });
    table.setItems(filteredlist);

    refreshButton.setOnAction((e)->{
      book.getVehiclesFromFile("CarWash.txt",true);
    });

    
  connection.makeConnection(); 
    
   stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (e) -> {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Είστε σίγουροι ότι θέλετε να κλείσετε το πρόγραμμα;");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent()) {
        if (result.get() == ButtonType.OK) {
            try {
              connection.closeConnection();
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
