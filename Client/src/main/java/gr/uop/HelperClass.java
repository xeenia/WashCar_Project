package gr.uop;


import java.util.Map;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class HelperClass {
int price = 0; 
    // Creates Keyboard layout
    public static void keyboardLayout(GridPane lettersPane, GridPane numbersPane, HBox big_keysPane, Map<String, Button> buttons, Button enter_button){

        //in order to get the keyboard layout 
        String[] letters = new String[]{"Q","W","E","R","T","Y","U","I","O","P",
                                            "A","S","D","F","G","H","J","K","L",
                                            "Z","X","C","V","B","N","M"};

        String[] numbers = new String[]{"7","8","9",
                                        "4","5","6",
                                        "1","2","3"};

        // put letter keys on keyboard pane and in TreeMap
        var index=0;
        for(int i=0; i< 3; i++){
            for(int j=0; j< 10; j++){
                if(index == 0 ){
                    Button letter_button = new Button(letters[index]);
                    letter_button.setPrefSize(40, 40);
                    lettersPane.add(letter_button,j,i);
                    buttons.put(letters[index], letter_button);
                }else if(index == 26){
                break;
                }
                // change line when we find "P" and "L" in order to simulate a real keyboard
                if((index!=0) && (!letters[index-1].equals("P") || !letters[index-1].equals("L"))){
                    Button letter_button = new Button(letters[index]);
                    letter_button.setPrefSize(40, 40);
                    lettersPane.add(letter_button,j,i);
                    buttons.put(letters[index], letter_button);
                    if(letters[index].equals("L")){
                        j=j+2;
                        index++;
                        continue;
                    }
                }
                index++;
            }
        }
        // we put number keys in keyboard pane and in TreeMap
        index=0;
        for(int i=0; i< 3; i++){
            for(int j=0; j< 3; j++){
                if(index == 0 ){
                    Button number_button = new Button(numbers[index]);
                    number_button.setPrefSize(40, 40);
                    numbersPane.add(number_button,j,i);
                    buttons.put(numbers[index], number_button);
                }else if(index == 10){
                    break;
                }
                Button number_button = new Button(numbers[index]);
                number_button.setPrefSize(40, 40);
                numbersPane.add(number_button,j,i);
                buttons.put(numbers[index], number_button);
                index++;
                }
            }
        Button backspace_button = new Button ("Backspace");
        backspace_button.setPrefSize(95, 40);
        Button space_button = new Button ();
        space_button.setPrefSize(340, 40);
        Button zero_button = new Button ("0");
        zero_button.setPrefSize(130, 40);
      
        buttons.put("Backspace", backspace_button);
        buttons.put(" ", space_button);
        buttons.put("0", zero_button);
        buttons.put("Enter", enter_button);

        big_keysPane.getChildren().addAll(space_button,backspace_button,zero_button);
        big_keysPane.setSpacing(20);
        big_keysPane.setAlignment(Pos.CENTER);
    }

    public void carRadiobuttons(ObservableList<RadioButton> radiobuttons, TextField carpricefield){
      price=0;
        for(RadioButton  b: radiobuttons){
            b.setOnAction((k)->{
              switch (b.getText()) {
                case "1":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                  //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=7;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=7;
                    System.out.println("Sub price: "+price);
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                case "2":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("3") || rb.getText().equals("5") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                  //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("3") || rb.getText().equals("5") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=6;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=6;
                    System.out.println("Sub price: "+price);
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                case "3":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("4") || rb.getText().equals("5") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                  //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("4") || rb.getText().equals("5") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=12;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=12;
                    System.out.println("Sub price: "+price);
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                case "4":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("3") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                  //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("1") || rb.getText().equals("3") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=9;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=9;
                    System.out.println("Sub price: "+price);
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                case "5":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                  //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=8;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=8;
                    System.out.println("Sub price: "+price);
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                case "6":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("5")){
                      rb.setDisable(true);
                    }
                  }
                  //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("5")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=15;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=15;
                    System.out.println("Sub price: "+price);
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                // duplicate cases 7 and 8
                case "7":
                case "8":
                  //add service price to total
                  if(b.isSelected()){
                    price+=80;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=80;
                    System.out.println("Sub price: "+price);
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                case "9": 
                  //add service price to total
                  if(b.isSelected()){
                    price+=20;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=20;
                    System.out.println("Sub price: "+price);
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                case "10":
                  //add service price to total
                  if(b.isSelected()){
                    price+=3;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=3;
                    System.out.println("Sub price: "+price);
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
              }
            });
          }
    }
    public int jeepRadiobuttons(ObservableList<RadioButton> radiobuttons, TextField jeeppricefield){
      price=0;
        for(RadioButton b  : radiobuttons){
            b.setOnAction((k)->{
              switch (b.getText()) {
                case "1":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                  //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=8;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=8;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "2":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("3") || rb.getText().equals("5") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                   //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("3") || rb.getText().equals("5") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=7;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=7;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "3":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("4") || rb.getText().equals("5") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                   //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("4") || rb.getText().equals("5") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=14;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=14;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "4":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("3") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                   //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("1") || rb.getText().equals("3") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=10;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=10;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "5":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("6")){
                      rb.setDisable(true);
                    }
                  }
                   //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("6")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=9;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=9;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "6":
                //disables radiobuttons
                  for(RadioButton rb  : radiobuttons){
                    if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("5")){
                      rb.setDisable(true);
                    }
                  }
                   //reactivates disabled radiobuttons
                  if(!b.isSelected()){
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("1") || rb.getText().equals("2") || rb.getText().equals("3") || rb.getText().equals("4") || rb.getText().equals("5")){
                        rb.setDisable(false);
                      }
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=17;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=17;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "7":
                  //add service price to total
                  if(b.isSelected()){
                    price+=80;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=80;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "8":
                  //add service price to total
                  if(b.isSelected()){
                    price+=90;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=90;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "9":
                  //add service price to total
                  if(b.isSelected()){
                    price+=20;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=20;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "10":
                  //add service price to total
                  if(b.isSelected()){
                    price+=3;
                    System.out.println("Add price: "+price);
                  //if not selected subtract service price from total
                  }else{
                    price-=3;
                    System.out.println("Sub price: "+price);
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
              }
            });
          }
        return price;
    }
    public int motoRadiobuttons(ObservableList<RadioButton> radiobuttons, TextField motopricefield){
      price=0;
        for(RadioButton b  : radiobuttons){
            if(b.getText().equals("2") || b.getText().equals("3") || b.getText().equals("5") || b.getText().equals("6") || b.getText().equals("7") || b.getText().equals("10")){
              b.setDisable(true);
            }else{
              b.setOnAction((k)->{
                switch (b.getText()) {
                  case "1":
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("4")){
                        rb.setDisable(true);
                      }
                    }
                    if(!b.isSelected()){
                      for(RadioButton rb  : radiobuttons){
                        if(rb.getText().equals("4")){
                          rb.setDisable(false);
                        }
                      }
                    }
                    //add service price to total
                    if(b.isSelected()){
                      price+=6;
                      System.out.println("Add price: "+price);
                    //if not selected subtract service price from total
                    }else{
                      price-=6;
                      System.out.println("Sub price: "+price);
                    }
                    motopricefield.setText(String.valueOf(price));
                    break;
                  case "4":
                    for(RadioButton rb  : radiobuttons){
                      if(rb.getText().equals("1")){
                        rb.setDisable(true);
                      }
                    }
                    if(!b.isSelected()){
                      for(RadioButton rb  : radiobuttons){
                        if(rb.getText().equals("1")){
                          rb.setDisable(false);
                        }
                      }
                    }
                    //add service price to total
                    if(b.isSelected()){
                      price+=8;
                      System.out.println("Add price: "+price);
                    //if not selected subtract service price from total
                    }else{
                      price-=8;
                      System.out.println("Sub price: "+price);
                    }
                    motopricefield.setText(String.valueOf(price));
                    break;
                  case "8":
                    //add service price to total
                    if(b.isSelected()){
                      price+=40;
                      System.out.println("Add price: "+price);
                    //if not selected subtract service price from total
                    }else{
                      price-=40;
                      System.out.println("Sub price: "+price);
                    }
                    motopricefield.setText(String.valueOf(price));
                    break;
                  case "9":
                    //add service price to total
                    if(b.isSelected()){
                      price+=10;
                      System.out.println("Add price: "+price);
                    //if not selected subtract service price from total
                    }else{
                      price-=10;
                      System.out.println("Sub price: "+price);
                    }
                    motopricefield.setText(String.valueOf(price));
                    break;
                }
              });
            }
          }
        return price;
    }
    public void radioButtonRestrictions(RadioButton primaryButton, RadioButton secondaryButton){

      switch(primaryButton.getText()){
        case "1":
          if (secondaryButton.getText().equals("2")) {
            
          } else if(secondaryButton.getText().equals("3")) {
            
          } else if(secondaryButton.getText().equals("4")){

          }else if(secondaryButton.getText().equals("5")){

          }else if (secondaryButton.getText().equals("6"))
        break;
      }

    }
}
