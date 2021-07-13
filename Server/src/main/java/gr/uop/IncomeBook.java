package gr.uop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IncomeBook {
    private ObservableList<Car> cars = FXCollections.observableArrayList();      
    int lastID;
    IncomeBook(){
        lastID=0;
        getCarsFromFile("SavedCars.txt");
    }
    public void getCarsFromFile(String filename){
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
            PrintWriter writer = new PrintWriter(myObj);
          writer.print("");
          // other operations
          writer.close();
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
        deleteCarFromFile(id);

    }
    private void deleteCarFromFile(int id){
       
    }
    /*public void updateCar(String id, int cost, String departute_time){
        cars.get(findCar(id)).setCost(cost);
        car
    }*/

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
