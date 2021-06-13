package gr.uop;

public class Car {
    private String car_number;
    private int id;
    private int cost;
    private int date;
    private int arrival_time;
    private int departute_time;

    Car(String car_number, int id, int date, int arrival_time){
        this.car_number=car_number;
        this.id=id;
        this.date=date;
        this.arrival_time=arrival_time;
    }

    public void setCost(int cost){
        this.cost=cost;
    }

    public void setDepartureTime(int time){
        departute_time=time;
    }

    public String getCarNumber(){
        return car_number;
    }
    public int getID(){
        return id;
    }

    public int getDate(){
        return date;
    }

    public int getArrivalTime(){
        return arrival_time;
    }

    public int getDepartureTime(){
        return departute_time;
    }

    public int getCost(){
        return cost;
    }
}
