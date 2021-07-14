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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

public class IncomeBook {
    private ObservableList<Car> cars = FXCollections.observableArrayList();     
    TableView table = new TableView<>(); 
    int lastID;
    IncomeBook(){
        lastID=0;
        getCarsFromFile("SavedCars.txt",false);
    }

    public TableView createTable(){
        
        table.setEditable(true);
        table.setPrefHeight(Integer.MAX_VALUE);
        TableColumn idCol = new TableColumn<Car,Integer>("ID");
        TableColumn dateCol = new TableColumn<Car,String>("Date");
        TableColumn timeCol = new TableColumn<Car,String>("Time");
        TableColumn typeCol = new TableColumn<Car,String>("Type");
        TableColumn carCol = new TableColumn<Car,String>("Car Number");
        TableColumn costCol = new TableColumn<Car,Integer>("Cost");
        TableColumn b1Col = new TableColumn<Car,Button>("Acceptance");
        //TableColumn b2Col = new TableColumn<>("Annulment");
  
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));
        carCol.setCellValueFactory(new PropertyValueFactory<>("car_number"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        b1Col.setCellValueFactory(new PropertyValueFactory<>("acceptButton"));
        
        typeCol.setCellValueFactory(new PropertyValueFactory<>("carType"));
  
        //delete button functionality
        TableColumn<Car, Car> b2Col = new TableColumn<>("Annulment");
        b2Col.setCellValueFactory(
            param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        b2Col.setCellFactory(param -> new TableCell<Car,Car>() {
            Button deleteButton = new Button("Delete");
            
            @Override
            protected void updateItem(Car car, boolean empty) {
                super.updateItem(car, empty);
        
                if (car == null) {
                    setGraphic(null);
                    return;
                }
        
                setGraphic(deleteButton);
                deleteButton.setOnAction((e)->{
                    //ενημερώνουμε ότι θα διαγραφεί τελείως το αμάξι 
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("WARNING");
                    alert.setContentText("WARNING! If you proceed then this car will completely be deleted.");
                    alert.setHeaderText("CAR INFO:"+"\n"+"\nTYPE: "+car.getCarType()+"\nNUMBER: "+car.getCar_number()+"\nCOST: "+car.getCost());
        
                    alert.initModality(Modality.WINDOW_MODAL);
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                   
                    Optional<ButtonType> buttonT = alert.showAndWait();
                    //αν επιλέξει οκ τότε θα μπούμε στην if
                    if(buttonT.get()==ButtonType.OK){
                        try {
                            deleteCar(car.getId());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                  
                  
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

    public void getCarsFromFile(String filename,boolean delete){
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
              System.out.println(data[0]+" "+data[1]+ " "+data[2]+ " "+data[3]+ " "+data[4]+" "+data[5]);
              
              Car car = new Car(lastID++,date,time,car_number,cost,type);
              addCar(car);
            }

            myReader.close();
            if(delete){
                PrintWriter writer = new PrintWriter(myObj);
                writer.print("");
                // other operations
                writer.close();
            }
            
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    public ObservableList<Car> getCars(){
        return cars;
    }
    public void createCars(){

    }
    
    public void addCar(Car car){
        cars.add(car);
    }

    public void deleteCar(int id) throws IOException{
        File inputFile = new File("SavedCars.txt");
        File tempFile = new File("TempSaved.txt");
        //Stage 1 - write in new file all cars except the cat we want to delete 
        deleteCarFromFile(inputFile, tempFile, id, false);
        //Stage 2 - Write back the infos drom TempSaved to SavedCars
        deleteCarFromFile(tempFile,inputFile,id,true);
      //  cars.remove(findCar(id)); <-παρόλο που μπορώ να το σβήσω έτσι απλά, εγώ επέλεξα να σβήσω όλα τα αμάξια από την λίστα και να τα ξαναγράφω έτσι ώστε τα id να πάνε στην σωστή σειρά 
      //και όχι 0,3,4,5,6
        cars.clear();
        lastID=0;
        getCarsFromFile("SavedCars.txt", false);
        
    }
    private void deleteCarFromFile(File inputFile, File tempFile, int id, boolean delete) throws IOException{
        
        BufferedReader reader;
        BufferedWriter writer;
        //H ιδέα είναι ότι μεταφέρω τις πληροφορίες σε ένα νέο αρχείο ΧΩΡΙΣ την γραμμή που θέλω να "σβήσω" αν το delete είναι false
        //αν το delete είναι true τότε απλά μεταφέρω τα στοιχεία από το νέο αρχείο στο προηγούμενο (έτσι κι αλλιώς παρακάτω
        //η if θα είναι πάντα false αφού στο νέο αρχείο νγάλαμε την γραμμή που θέλαμε να σβήσουμε)
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(tempFile));
        
            String lineToRemove = getCar(id);
            String currentLine;
            
            while((currentLine = reader.readLine()) != null) {
                String data[] = currentLine.split(",");
                String trimmedLine = data[0].trim();
                if(trimmedLine.equals(lineToRemove)) continue;
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

    public String getCar(int id){
        return cars.get(findCar(id)).getCar_number();
    }
    private int findCar(int id){
        int i=0;
        for(Car car:cars){
            if(car.getId()==id){
                break;
            }
            i++;
        }
        return i;
    }
    public void searchCar(int id){

    }
}
