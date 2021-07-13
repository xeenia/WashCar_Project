package gr.uop;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Car {
    private int id;
    private String arrival_time;
    private String car_number;
    private int cost;
    private String date;
    private ImageView accept;
    private ImageView cancel;
    private Button acceptButton;
    private Button cancelButton;
    private boolean appear;
    private String carType;

    public String getCarType() {
        return this.carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Button getAcceptButton() {
        return this.acceptButton;
    }

    public void setAcceptButton(Button acceptButton) {
        this.acceptButton = acceptButton;
    }

    public Button getCancelButton() {
        return this.cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArrival_time() {
        return this.arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getCar_number() {
        return this.car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    Car(){

    }
    Car(int id, String date, String arrival_time, String car_number,int cost,String carType){
        this.car_number=car_number;
        this.id=id;
        this.date=date;
        this.arrival_time=arrival_time;
        this.cost=cost;
        appear=true;
        this.carType=carType;

        Image acceptIcon = new Image(Server.class.getResourceAsStream("img/entry2.png"));
        accept=new ImageView(acceptIcon); 
        accept.setFitHeight(20);
        accept.setFitWidth(20);
        acceptButton = new Button();
        acceptButton.setGraphic(accept);
        Image cancelIcon = new Image(Server.class.getResourceAsStream("img/x2.png"));
        cancel=new ImageView(cancelIcon); 
        cancel.setFitHeight(20);
        cancel.setFitWidth(20);
        cancelButton = new Button();
        cancelButton.setGraphic(cancel);

        acceptButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;");
        acceptButton.setOnMousePressed(event -> acceptButton.setStyle("-fx-background-color: transparent; -fx-padding: 3 1 1 3;"));
        acceptButton.setOnMouseReleased(event -> acceptButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;"));

        cancelButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;");
        cancelButton.setOnMousePressed(event -> cancelButton.setStyle("-fx-background-color: transparent; -fx-padding: 3 1 1 3;"));
        cancelButton.setOnMouseReleased(event -> cancelButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;"));
    }

   
}
