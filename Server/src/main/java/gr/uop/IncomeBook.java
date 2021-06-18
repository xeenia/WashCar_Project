package gr.uop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IncomeBook {
    private ObservableList<Car> cars = FXCollections.observableArrayList();      

    IncomeBook(){
        try {
            File myObj = new File("Server/src/main/java/gr/uop/txtFiles/info.txt");
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
              String[] data= myReader.nextLine().split(",");
            int id=Integer.parseInt(data[0]);
              String car_number=data[3];
              String date=data[1];
              String time=data[2];
              int cost=Integer.parseInt(data[4]);
              System.out.println(data[0]+" "+data[1]+ " "+data[2]+ " "+data[3]+ " "+data[4]);
              Car car = new Car(id,date,time,car_number,cost);
              addCar(car);
            }
            myReader.close();
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
