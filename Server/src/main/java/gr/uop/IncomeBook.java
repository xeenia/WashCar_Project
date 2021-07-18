package gr.uop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IncomeBook {
    //προσθήκη οχημάτων για την εμφάνιση τους στο TableView
    private ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();     
    TableView table = new TableView<>(); 
    int lastID; //για τον έλεγχο των ID στο TableView έτσι ώστε όταν σβήνεται ένα όχημα ο αριθμός ID είναι σε σωστή σειρά
    IncomeBook(){
        lastID=0;
        //Εννοείται με το που φτιαχτεί το αντικείμενο αυτής της κλάσεις, θα διαβάζει  τα οχήματα που πρέπει να μπουν στο TableView 
        getVehiclesFromFile("SavedCars.txt",false);
    }

    public TableView createTable(){
        
        table.setEditable(true);
        table.setPrefHeight(Integer.MAX_VALUE);
        TableColumn idCol = new TableColumn<Vehicle,Integer>("ID");
        TableColumn dateCol = new TableColumn<Vehicle,String>("Ημερομηνία");
        TableColumn timeCol = new TableColumn<Vehicle,String>("Ώρα");
        TableColumn typeCol = new TableColumn<Vehicle,String>("Όχημα");
        TableColumn carCol = new TableColumn<Vehicle,String>("Αριθμός Πινακίδας");
        TableColumn costCol = new TableColumn<Vehicle,Integer>("Κόστος");
  
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));
        carCol.setCellValueFactory(new PropertyValueFactory<>("vehicle_number"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        
        typeCol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
  
        //Λειτουργικότητα των κουμπίων παρακάτω
        TableColumn<Vehicle, Vehicle> b2Col = new TableColumn<>("Ακύρωση");
        TableColumn<Vehicle,Vehicle> b1Col = new TableColumn<>("Πληρωμή");

        b2Col.setCellValueFactory(
            param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        b1Col.setCellValueFactory(
            param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        b2Col.setCellFactory(param -> new TableCell<Vehicle,Vehicle>() {
            Button deleteButton = new Button();
            Image deleteImg = new Image(Server.class.getResourceAsStream("img/deleteIconBlack.png"));
            ImageView deleteIcon = new ImageView(deleteImg);
            
         
            @Override
            protected void updateItem(Vehicle car, boolean empty) {
                super.updateItem(car, empty);
        
                if (car == null) {
                    setGraphic(null);
                    return;
                }
                deleteButton.setGraphic(deleteIcon);
                setGraphic(deleteButton);
                deleteButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;");
                deleteIcon.setFitHeight(20);
                deleteIcon.setFitWidth(20);
                deleteButton.setOnMousePressed(event -> deleteButton.setStyle("-fx-background-color: transparent; -fx-padding: 3 1 1 3;"));
                 deleteButton.setOnMouseReleased(event -> deleteButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;"));
                deleteButton.setOnAction((e)->{
                    //ενημερώνουμε ότι θα διαγραφεί τελείως το αμάξι 
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("WARNING");
                    alert.setContentText("ΠΡΟΕΙΔΟΠΟΙΣΗ! Αν συνεχίσετε τότε το όχημα θα διαγραφεί από το σύστημα.");
                    alert.setHeaderText("ΠΛΗΡΟΦΟΡΊΕΣ ΟΧΉΜΑΤΟΣ:"+"\n"+"\nΟΧΗΜΑ "+car.getVehicleType()+"\nΠΙΝΑΚΙΔΑ "+car.getVehicle_number());
        
                    alert.initModality(Modality.WINDOW_MODAL);
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                   
                    Optional<ButtonType> buttonT = alert.showAndWait();
                    //αν επιλέξει οκ τότε θα μπούμε στην if
                    if(buttonT.get()==ButtonType.OK){
                        try {
                            deleteVehicle(car.getId());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                     
                });
            }
        });


        b1Col.setCellFactory(param -> new TableCell<Vehicle,Vehicle>() {
            Button acceptButton = new Button();
            Image img = new Image(Server.class.getResourceAsStream("img/payIconReceiptBlack.png"));
            ImageView imgView = new ImageView(img);
            @Override
            protected void updateItem(Vehicle vehicle, boolean empty) {
                super.updateItem(vehicle, empty);
                
                if (vehicle == null) {
                    setGraphic(null);
                    return;
                }
        
                acceptButton.setGraphic(imgView);
                setGraphic(acceptButton);
                
                acceptButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;");
                imgView.setFitHeight(20);
                imgView.setFitWidth(20);
                acceptButton.setOnMousePressed(event -> acceptButton.setStyle("-fx-background-color: transparent; -fx-padding: 3 1 1 3;"));
                acceptButton.setOnMouseReleased(event -> acceptButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;"));
                acceptButton.setOnAction((e)->{
                    //Κλάση για την εμφάνιση της δεύτερης οθόνης (απόδειξης)
                    ReceiptUI receiptUI = new ReceiptUI(vehicle);
                    
                    var scene2 = new Scene(receiptUI.makeReceiptUI(),500,600);
                    Stage stage2 = new Stage();
                    stage2.setScene(scene2);
                    stage2.setMinHeight(600);
                    stage2.setMinWidth(500);
                    stage2.setMaxHeight(700);
                    stage2.setMaxWidth(600);
                    stage2.show();  
                        
                    receiptUI.getPayButton().setOnMousePressed(event -> receiptUI.getPayButton().setStyle("-fx-padding: 3 1 1 3;"));
                    receiptUI.getPayButton().setOnMouseReleased(event -> receiptUI.getPayButton().setStyle("-fx-padding: 2, 2, 2, 2;"));
                    receiptUI.getPayButton().setOnAction((b1)->{
                    File file = new File("IncomeBook.txt");
                    try {
                        //Μόλις πατήσει πληρωμή τότε θα γραφτεί το όχημα στο IncomeBook.txt μαζί με την νέα ημερομηνία και ώρα αποχώρησης
                        FileWriter fileWriter = new FileWriter(file, true);       
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(vehicle.getVehicleType()+","+vehicle.getVehicle_number()+","+vehicle.getServices()+","+vehicle.getDate()+","+vehicle.getArrival_time()+","+receiptUI.getDepDate()+","+receiptUI.getDepTime()+System.getProperty("line.separator"));
                        bufferedWriter.close();
                        //εννοείται θα διαγραφεί από το το TableView
                        deleteVehicle(vehicle.getId());
                        stage2.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    
                });
                receiptUI.getCancelButton().setOnMousePressed(event -> receiptUI.getCancelButton().setStyle("-fx-padding: 3 1 1 3;"));
                receiptUI.getCancelButton().setOnMouseReleased(event -> receiptUI.getCancelButton().setStyle("-fx-padding: 2, 2, 2, 2;"));
                receiptUI.getCancelButton().setOnAction((b2)->{
                    stage2.close();
        
                });
            });
        }
    });
  
      
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

    public void getVehiclesFromFile(String filename,boolean delete){
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
              String[] data= myReader.nextLine().split(",");
              String car_number=data[0];
              String date=data[4];
              String time=data[5];
              String type=data[1];
              int cost=Integer.parseInt(data[3]);
              
              Vehicle vehicle = new Vehicle(lastID++,date,time,car_number,cost,type,data[2]);
              addVehicleToList(vehicle);
            }

            myReader.close();
            if(delete){
                //διαγραφή περιεχομένου του αρχείου CarWash.txt (θα μπορούσε οποιοδήποτε αρχείο,αρκεί το delete να είναι true)
                PrintWriter writer = new PrintWriter(myObj);
                writer.print("");
                writer.close();
            }
            
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    public ObservableList<Vehicle> getVehicles(){
        return vehicles;
    }
    
    public void addVehicleToList(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    public void deleteVehicle(int id) throws IOException{
        File inputFile = new File("SavedCars.txt");
        File tempFile = new File("TempSaved.txt");
        //Stage 1 - write in new file all cars except the cat we want to delete 
        deleteVehicleFromFile(inputFile, tempFile, id, false);
        //Stage 2 - Write back the info drom TempSaved to SavedCars
        deleteVehicleFromFile(tempFile,inputFile,id,true);
      //  cars.remove(findCar(id)); <-παρόλο που μπορώ να το σβήσω έτσι απλά, εγώ επέλεξα να σβήσω όλα τα αμάξια από την λίστα και να τα ξαναγράφω έτσι ώστε τα id να πάνε στην σωστή σειρά 
      //και όχι 0,3,4,5,6
        vehicles.clear();
        lastID=0;
        getVehiclesFromFile("SavedCars.txt", false);
        
    }
    private void deleteVehicleFromFile(File inputFile, File tempFile, int id, boolean delete) throws IOException{
        
        BufferedReader reader;
        BufferedWriter writer;
        //H ιδέα είναι ότι μεταφέρω τις πληροφορίες σε ένα νέο αρχείο ΧΩΡΙΣ την γραμμή που θέλω να "σβήσω" αν το delete είναι false
        //αν το delete είναι true τότε απλά μεταφέρω τα στοιχεία από το νέο αρχείο στο προηγούμενο (έτσι κι αλλιώς παρακάτω
        //η if θα είναι πάντα false αφού στο νέο αρχείο νγάλαμε την γραμμή που θέλαμε να σβήσουμε)
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(tempFile));
        
            String lineToRemove = getVehicleNumber(id);
            String currentLine;
            
            while((currentLine = reader.readLine()) != null) {
                String data[] = currentLine.split(",");          
                if(data[0].equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close(); 
            reader.close(); 
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        //εδώ σβήνω το νέο αρχείο δλδ το TempSaved.txt 
        if(delete){
            inputFile.delete();
        }
        
    }

    public String getVehicleNumber(int id){
        return vehicles.get(id).getVehicle_number();
    }
  
}
