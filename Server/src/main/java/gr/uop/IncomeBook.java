package gr.uop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IncomeBook {
    String s_Labels[] = {
        "Πλύσιμο εξωτερικό","Πλύσιμο εσωτερικό",
        "Πλύσιμο εξωτερικό & εσωτερικό", "Πλύσιμο εξωτερικό σπέσιαλ",
        "Πλύσιμο εσωτερικό σπέσιαλ","Πλύσιμο εξωτερικό & εσωτερικό σπέσιαλ",
        "Βιολογικός καθαρισμός εσωτερικό","Κέρωμα‐Γυάλισμα","Καθαρισμός κινητήρα",
        "Πλύσιμο σασί"
    };
    String s_Car_Labels[] ={"7","6","12","9","8","15","80","80","20","3"};
    String s_Jeep_Labels[]={"8","7","14","10","9","17","80","90","20","3"};
    String s_Motor_Labels[]={"6","0","0","8","0","0","0","40","10","0"};
    private ObservableList<Vehicle> cars = FXCollections.observableArrayList();     
    TableView table = new TableView<>(); 
    int inc;
    int lastID;
    IncomeBook(){
        lastID=0;
        getCarsFromFile("SavedCars.txt",false);
        inc=1;
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
        carCol.setCellValueFactory(new PropertyValueFactory<>("car_number"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        
        typeCol.setCellValueFactory(new PropertyValueFactory<>("carType"));
  
        //delete button functionality
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
            Image im_refresh = new Image(Server.class.getResourceAsStream("img/deleteIconBlack.png"));
            ImageView refresh_icon = new ImageView(im_refresh);
            
         
            @Override
            protected void updateItem(Vehicle car, boolean empty) {
                super.updateItem(car, empty);
        
                if (car == null) {
                    setGraphic(null);
                    return;
                }
                deleteButton.setGraphic(refresh_icon);
                setGraphic(deleteButton);
                deleteButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;");
                refresh_icon.setFitHeight(20);
                refresh_icon.setFitWidth(20);
                deleteButton.setOnMousePressed(event -> deleteButton.setStyle("-fx-background-color: transparent; -fx-padding: 3 1 1 3;"));
                 deleteButton.setOnMouseReleased(event -> deleteButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;"));
                deleteButton.setOnAction((e)->{
                    //ενημερώνουμε ότι θα διαγραφεί τελείως το αμάξι 
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("WARNING");
                    alert.setContentText("ΠΡΟΕΙΔΟΠΟΙΣΗ! Αν συνεχίσετε τότε το όχημα θα διαγραφεί από το σύστημα.");
                    alert.setHeaderText("ΠΛΗΡΟΦΟΡΊΕΣ ΟΧΉΜΑΤΟΣ:"+"\n"+"\nΟΧΗΜΑ "+car.getCarType()+"\nΠΙΝΑΚΙΔΑ "+car.getCar_number());
        
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


        b1Col.setCellFactory(param -> new TableCell<Vehicle,Vehicle>() {
            Button acceptButton = new Button();
            Image im_refresh = new Image(Server.class.getResourceAsStream("img/payIconReceiptBlack.png"));
            ImageView refresh_icon = new ImageView(im_refresh);
            @Override
            protected void updateItem(Vehicle car, boolean empty) {
                super.updateItem(car, empty);
                
                if (car == null) {
                    setGraphic(null);
                    return;
                }
        
                acceptButton.setGraphic(refresh_icon);
                setGraphic(acceptButton);
                
                acceptButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;");
                refresh_icon.setFitHeight(20);
                refresh_icon.setFitWidth(20);
                acceptButton.setOnMousePressed(event -> acceptButton.setStyle("-fx-background-color: transparent; -fx-padding: 3 1 1 3;"));
                acceptButton.setOnMouseReleased(event -> acceptButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;"));
                acceptButton.setOnAction((e)->{

                    //LOGO
                    Pane p_logo = new Pane();
                    p_logo.setStyle("-fx-background-color:#abdbe3;");
                    Image im_logo = new Image(Server.class.getResourceAsStream("img/logo.png"));
                    ImageView iv_logo = new ImageView(im_logo);
                    iv_logo.setFitHeight(100);
                    iv_logo.setFitWidth(150);
                    p_logo.getChildren().add(iv_logo);

                    //ΠΛΗΡΟΦΟΡΙΕΣ ΟΧΗΜΑΤΟΣ ΚΑΙ ΑΠΟΔΕΙΞΗΣ
                    BorderPane p_hb_info = new BorderPane();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                    String depTime = java.time.LocalTime.now().format(dtf);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
                    String depDate = java.time.LocalDate.now().format(formatter);
                    Text t_carInfo = new Text("Όχημα: "+car.getCarType()+"\nΠινακίδα: "+ 
                    car.getCar_number()+"\nΑναχώρηση:\n"+car.getDate()+"\n" +car.getArrival_time());
                    Font font = Font.font("Verdana", FontWeight.BOLD,10);
                    t_carInfo.setFont(font);
                    Random rand = new Random(); 
                    int upperbound = 100;
                    int int_random = rand.nextInt(upperbound);  
                    Text t_receiptInfo = new Text("INVOICE #"+ int_random+ 
                    "\nΑποχώριση:\n" 
                    +depDate +"\n"+ depTime);
                    t_receiptInfo.setFont(font);
                    p_hb_info.setLeft(t_carInfo);
                    p_hb_info.setRight(t_receiptInfo);
                    p_hb_info.setPadding(new Insets(10));
                    p_hb_info.setStyle("-fx-background-color:#ffffff;");


                    SplitPane sp = new SplitPane();


                    //ΠΛΗΡΟΦΟΡΙΕΣ ΥΠΗΡΕΣΙΩΝ
                    BorderPane p_hb_receiptInfo = new BorderPane();
                    p_hb_receiptInfo.setStyle("-fx-background-color:#ffffff;");
                    BorderPane p_hb_amount = new BorderPane();
                    Pane p_color = new Pane();
                    p_color.setStyle("-fx-background-color:#abdbe3;");
                    VBox p_cost = new VBox();
                    p_cost.setPadding(new Insets(5,10,5,10));
                    Text t_cost = new Text(String.valueOf(car.getCost())+" €");
                    
                    t_cost.setStyle("-fx-font-size: 40;");
                    t_cost.setFill(Color.WHITE);
                    Text l_amount = new Text("Συνολικά:");
                    Font font2 = Font.font("Verdana", FontWeight.BOLD,25);
                    l_amount.setStyle("-fx-font-weight: bold; -fx-font-size: 13;");
                    l_amount.setFill(Color.WHITE);
                    p_cost.getChildren().addAll(l_amount,t_cost);
                    p_cost.setAlignment(Pos.CENTER_RIGHT);
                    p_cost.setStyle("-fx-background-color:#79c5d4;");
                    p_hb_amount.setCenter(p_color);
                    p_hb_amount.setRight(p_cost);

                    //plhrofories services
                    Text t_service = new Text("Υπηρεσία");
                    t_service.setStyle("-fx-font-weight: bold; -fx-font-size:13;");
                    Text t_serviceInfo = new Text("Πληροφορίες");
                    t_serviceInfo.setStyle("-fx-font-weight: bold; -fx-font-size:13;");
                    Text t_price = new Text("Τιμή");
                    t_price.setStyle("-fx-font-weight: bold; -fx-font-size:13;");

                    VBox p_vb_service = new VBox();
                    p_vb_service.getChildren().add(t_service);
                    p_vb_service.setAlignment(Pos.CENTER);
                    p_vb_service.setSpacing(5);

                    VBox p_vb_servInfo = new VBox();
                    p_vb_servInfo.getChildren().add(t_serviceInfo);
                    p_vb_servInfo.setAlignment(Pos.CENTER);
                    p_vb_servInfo.setSpacing(5);


                    VBox p_vb_price = new VBox();
                    p_vb_price.getChildren().add(t_price);
                    p_vb_price.setAlignment(Pos.CENTER);
                    p_vb_price.setSpacing(5);

                    String services[] = car.getServices().split("-");
                    int size = services.length;
                    Text v1 = new Text();
                    Text v2 = new Text();
                    Text v3 = new Text();
                    String s1="",s2="",s3="";

                    for(int i =0; i<size; i++){
                        String n = ((Integer.parseInt(services[i]))!=10)?"0":"";
                        s1=s1.concat(n+services[i]+((i==size-1)?"":"\n"));
                        s2=s2.concat(s_Labels[Integer.parseInt(services[i])-1]+((i==size-1)?"":"\n"));
                        if(car.getCarType().contains("Car")){
                        s3= s3.concat(s_Car_Labels[Integer.parseInt(services[i])-1]+" €"+((i==size-1)?"":"\n"));
                        }else if(car.getCarType().contains("Jeep")){
                            s3=s3.concat(s_Jeep_Labels[Integer.parseInt(services[i])-1]+" €"+((i==size-1)?"":"\n"));
                        }else{
                        s3= s3.concat(s_Motor_Labels[Integer.parseInt(services[i])-1]+" €"+((i==size-1)?"":"\n"));
                        }
                    }
                    v1.setText(s1);
                    v2.setText(s2);
                    v3.setText(s3);
                    p_vb_service.getChildren().add(v1);
                    p_vb_service.setAlignment(Pos.TOP_CENTER);
                    p_vb_servInfo.getChildren().add(v2);
                    p_vb_servInfo.setAlignment(Pos.TOP_CENTER);
                    p_vb_price.getChildren().add(v3);
                    HBox p_hb_servAndInfo = new HBox(p_vb_service,p_vb_servInfo);
                    p_vb_price.setAlignment(Pos.TOP_CENTER);
                    p_hb_servAndInfo.setSpacing(30);
                    p_hb_servAndInfo.setAlignment(Pos.CENTER);
                    p_hb_receiptInfo.setLeft(p_hb_servAndInfo);
                    p_hb_receiptInfo.setRight(p_vb_price);
                    p_hb_receiptInfo.setPadding(new Insets(10,30,10,30));


                    //ΚΟΥΜΠΙΑ - FOOTER               
                    BorderPane p_footer = new BorderPane();
                    p_footer.setStyle("-fx-background-color:#ffffff;");
                    Button b_payButton = new Button("Πληρωμή");
                    File f = new File("IncomeBook.css");
                    
                    b_payButton.getStylesheets().clear();
                    b_payButton.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
                    Button b_cancelButton = new Button ("Ακύρωση");
                    b_cancelButton.getStylesheets().clear();
                    b_cancelButton.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
                    b_payButton.setMaxHeight(Double.MAX_VALUE);
                    b_cancelButton.setMaxHeight(Double.MAX_VALUE);
                    b_payButton.setMaxWidth(Double.MAX_VALUE);
                    b_cancelButton.setMaxWidth(Double.MAX_VALUE);
                    
                    HBox p_hb_buttons = new HBox(b_payButton,b_cancelButton);
                    HBox.setHgrow(b_cancelButton, Priority.ALWAYS);
                    HBox.setHgrow(b_payButton, Priority.ALWAYS);
                    p_hb_buttons.setAlignment(Pos.CENTER);
                    p_hb_buttons.setSpacing(10);
                    p_footer.setCenter(p_hb_buttons);
                    p_footer.setPadding(new Insets(5));
                        
                    //Εδώ τα ενώνουμε όλα
                    BorderPane p_bp_main = new BorderPane();
                    VBox p_vb_mainTop = new VBox();
                    VBox p_vb_mainBottom = new VBox();

                    p_vb_mainTop.getChildren().addAll(p_logo ,p_hb_info,sp);
                    p_vb_mainBottom.getChildren().addAll(p_hb_amount,p_footer);
                    p_bp_main.setTop(p_vb_mainTop);
                    p_bp_main.setCenter(p_hb_receiptInfo);
                    p_bp_main.setBottom(p_vb_mainBottom);
                    var scene2 = new Scene(p_bp_main,500,600);
                    Stage stage2 = new Stage();
                    stage2.setScene(scene2);
                    stage2.setMinHeight(600);
                    stage2.setMinWidth(500);
                    stage2.setMaxHeight(700);
                    stage2.setMaxWidth(600);
                    stage2.show();      
                    b_payButton.setOnMousePressed(event -> b_payButton.setStyle("-fx-padding: 3 1 1 3;"));
                    b_payButton.setOnMouseReleased(event -> b_payButton.setStyle("-fx-padding: 2, 2, 2, 2;"));
                    b_payButton.setOnAction((b1)->{
                        File file = new File("IncomeBook.txt");
                        char c='|';
                        char t='\t';
                        try {
                            
                            FileWriter fileWriter = new FileWriter(file, true);
                           
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(car.getCarType()+","+car.getCar_number()+","+car.getServices()+","+car.getDate()+","+car.getArrival_time()+","+depDate+","+depTime+System.getProperty("line.separator"));
                            bufferedWriter.close();
                            deleteCar(car.getId());
                            stage2.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                       
                    });
                    b_cancelButton.setOnMousePressed(event -> b_cancelButton.setStyle("-fx-padding: 3 1 1 3;"));
                    b_cancelButton.setOnMouseReleased(event -> b_cancelButton.setStyle("-fx-padding: 2, 2, 2, 2;"));
                    b_cancelButton.setOnAction((b2)->{
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
              
              Vehicle car = new Vehicle(lastID++,date,time,car_number,cost,type,data[2]);
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
    public ObservableList<Vehicle> getCars(){
        return cars;
    }
    
    public void addCar(Vehicle car){
        cars.add(car);
    }

    public void deleteCar(int id) throws IOException{
        File inputFile = new File("SavedCars.txt");
        File tempFile = new File("TempSaved.txt");
        //Stage 1 - write in new file all cars except the cat we want to delete 
        deleteCarFromFile(inputFile, tempFile, id, false);
        //Stage 2 - Write back the info drom TempSaved to SavedCars
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
        for(Vehicle car:cars){
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
