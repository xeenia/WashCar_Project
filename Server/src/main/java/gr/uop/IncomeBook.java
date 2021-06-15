package gr.uop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IncomeBook {
    private ObservableList<Car> cars = FXCollections.observableArrayList();      

    public ObservableList<Car> getCars(){
        return cars;
    }
    public void createCars(){

    }
    
    public void addCar(Car car){
        cars.add(car);
    }

    public void deleteCar(int id){
        
        cars.remove(findCar(id));
    }

    public void updateCar(int id, int cost, String departute_time){
        cars.get(findCar(id)).setCost(cost);
        cars.get(findCar(id)).setDeparture_time(departute_time);
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
}
