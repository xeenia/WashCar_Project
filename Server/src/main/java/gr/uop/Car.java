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
    private String carType;
    private String services;

    public String getServices() {
        return this.services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getCarType() {
        return this.carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
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
    Car(int id, String date, String arrival_time, String car_number,int cost,String carType,String services){
        this.car_number=car_number;
        this.id=id;
        this.date=date;
        this.arrival_time=arrival_time;
        this.cost=cost;
        this.carType=carType;
        this.services=services;
    }

   
}
