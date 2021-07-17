package gr.uop;

public class Vehicle {
    private int id;
    private String arrival_time;
    private String vehicle_number;
    private int cost;
    private String date;
    private String vehicleType;
    private String services;

    public String getServices() {
        return this.services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String carType) {
        this.vehicleType = carType;
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

    public String getVehicle_number() {
        return this.vehicle_number;
    }

    public void setVehicle_number(String car_number) {
        this.vehicle_number = car_number;
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

    Vehicle(int id, String date, String arrival_time, String vehicle_number,int cost,String vehicleType,String services){
        this.vehicle_number=vehicle_number;
        this.id=id;
        this.date=date;
        this.arrival_time=arrival_time;
        this.cost=cost;
        this.vehicleType=vehicleType;
        this.services=services;
    }

   
}
