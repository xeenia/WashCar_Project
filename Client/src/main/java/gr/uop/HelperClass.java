package gr.uop;


import java.util.Map;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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
                  Text text = new Text(letters[index]);   
                  text.setStyle("-fx-font-size:20;");          
                  Button letter_button = new Button();
                  letter_button.setGraphic(text);
                  letter_button.setPrefSize(70, 70);
                  lettersPane.add(letter_button,j,i);
                  buttons.put(letters[index], letter_button);
                }else if(index == 26){
                break;
                }
                // change line when we find "P" and "L" in order to simulate a real keyboard
                if((index!=0) && (!letters[index-1].equals("P") || !letters[index-1].equals("L"))){
                  Text text = new Text(letters[index]);   
                  text.setStyle(" -fx-font-size:20;");          
                  Button letter_button = new Button();
                  letter_button.setGraphic(text);  
                    letter_button.setPrefSize(70, 70);
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
                    Text text = new Text(numbers[index]);   
                    text.setStyle("-fx-font-size:20;");          
                    Button number_button = new Button();
                    number_button.setGraphic(text);
                  
                    number_button.setPrefSize(70, 70);
                    numbersPane.add(number_button,j,i);
                    buttons.put(numbers[index], number_button);
                }else if(index == 10){
                    break;
                }
                Text text = new Text(numbers[index]);   
                text.setStyle("-fx-font-size:20;");          
                Button number_button = new Button();
                number_button.setGraphic(text);
                number_button.setPrefSize(70, 70);
                numbersPane.add(number_button,j,i);
                buttons.put(numbers[index], number_button);
                index++;
                }
            }
        Text text = new Text("Backspace");   
        text.setStyle("-fx-font-size:20;");  
        Button backspace_button = new Button ();
        backspace_button.setGraphic(text);
        backspace_button.setPrefSize((70*3)+10, 70);
        Text text2 = new Text(" ");   
        text2.setStyle("-fx-font-size:20;");
        Button space_button = new Button ();
        space_button.setGraphic(text2);
        space_button.setPrefSize((70*7)+25, 70);
        Text text3 = new Text("0");   
        text3.setStyle("-fx-font-size:20;");
        Button zero_button = new Button ();
        zero_button.setGraphic(text3);
        zero_button.setPrefSize((70*3), 70);
      
        buttons.put("Backspace", backspace_button);
        buttons.put(" ", space_button);
        buttons.put("0", zero_button);
        buttons.put("Enter", enter_button);
        HBox spaceAndBackspace = new HBox(space_button,backspace_button);
        spaceAndBackspace.setSpacing(5);
        big_keysPane.getChildren().addAll(spaceAndBackspace,zero_button);
        big_keysPane.setSpacing(25);
        
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
                    
                    if(radiobuttons.get(1).isSelected()||radiobuttons.get(4).isSelected()){
                      radiobuttons.get(3).setDisable(false);
                    }else {
                      radiobuttons.get(2).setDisable(false);
                      radiobuttons.get(3).setDisable(false);
                      radiobuttons.get(5).setDisable(false);
                    }
              
                    }
                  
                  //add service price to total
                  if(b.isSelected()){
                    price+=7;
                  //if not selected subtract service price from total
                  }else{
                    price-=7;
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
                    
                    if(radiobuttons.get(0).isSelected()||radiobuttons.get(3).isSelected()){
                      radiobuttons.get(4).setDisable(false);
                    }else {
                      radiobuttons.get(2).setDisable(false);
                      radiobuttons.get(4).setDisable(false);
                      radiobuttons.get(5).setDisable(false);
                    }
              
                    }
                  //add service price to total
                  if(b.isSelected()){
                    price+=6;
                  //if not selected subtract service price from total
                  }else{
                    price-=6;
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
                  //if not selected subtract service price from total
                  }else{
                    price-=12;
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
                    
                    if(radiobuttons.get(1).isSelected()||radiobuttons.get(4).isSelected()){
                      radiobuttons.get(0).setDisable(false);
                    }else {
                      radiobuttons.get(0).setDisable(false);
                      radiobuttons.get(2).setDisable(false);
                      radiobuttons.get(5).setDisable(false);
                    }
              
                    }
                  //add service price to total
                  if(b.isSelected()){
                    price+=9;
                  //if not selected subtract service price from total
                  }else{
                    price-=9;
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
                    
                    if(radiobuttons.get(0).isSelected()||radiobuttons.get(3).isSelected()){
                      radiobuttons.get(1).setDisable(false);
                    }else {
                      radiobuttons.get(1).setDisable(false);
                      radiobuttons.get(2).setDisable(false);
                      radiobuttons.get(5).setDisable(false);
                    }
              
                    }
                  //add service price to total
                  if(b.isSelected()){
                    price+=8;
                  //if not selected subtract service price from total
                  }else{
                    price-=8;
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
                  //if not selected subtract service price from total
                  }else{
                    price-=15;
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                // duplicate cases 7 and 8
                case "7":
                case "8":
                  //add service price to total
                  if(b.isSelected()){
                    price+=80;
                  //if not selected subtract service price from total
                  }else{
                    price-=80;
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                case "9": 
                  //add service price to total
                  if(b.isSelected()){
                    price+=20;
                  //if not selected subtract service price from total
                  }else{
                    price-=20;
                  }
                  carpricefield.setText(String.valueOf(price));
                  break;
                case "10":
                  //add service price to total
                  if(b.isSelected()){
                    price+=3;
                  //if not selected subtract service price from total
                  }else{
                    price-=3;
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
                    if(radiobuttons.get(1).isSelected()||radiobuttons.get(4).isSelected()){
                      radiobuttons.get(3).setDisable(false);
                    }else {
                      radiobuttons.get(2).setDisable(false);
                      radiobuttons.get(3).setDisable(false);
                      radiobuttons.get(5).setDisable(false);
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=8;
                  //if not selected subtract service price from total
                  }else{
                    price-=8;
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
                    if(radiobuttons.get(0).isSelected()||radiobuttons.get(3).isSelected()){
                      radiobuttons.get(4).setDisable(false);
                    }else {
                      radiobuttons.get(2).setDisable(false);
                      radiobuttons.get(4).setDisable(false);
                      radiobuttons.get(5).setDisable(false);
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=7;
                  //if not selected subtract service price from total
                  }else{
                    price-=7;
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
                  //if not selected subtract service price from total
                  }else{
                    price-=14;
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
                    if(radiobuttons.get(1).isSelected()||radiobuttons.get(4).isSelected()){
                      radiobuttons.get(0).setDisable(false);
                    }else {
                      radiobuttons.get(0).setDisable(false);
                      radiobuttons.get(2).setDisable(false);
                      radiobuttons.get(5).setDisable(false);
                    }
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=10;
                  //if not selected subtract service price from total
                  }else{
                    price-=10;
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
                    if(radiobuttons.get(0).isSelected()||radiobuttons.get(3).isSelected()){
                      radiobuttons.get(1).setDisable(false);
                    }else {
                      radiobuttons.get(1).setDisable(false);
                      radiobuttons.get(2).setDisable(false);
                      radiobuttons.get(5).setDisable(false);
                    }
              
                  }
                  //add service price to total
                  if(b.isSelected()){
                    price+=9;
                  //if not selected subtract service price from total
                  }else{
                    price-=9;
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
                  //if not selected subtract service price from total
                  }else{
                    price-=17;
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "7":
                  //add service price to total
                  if(b.isSelected()){
                    price+=80;
                  //if not selected subtract service price from total
                  }else{
                    price-=80;
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "8":
                  //add service price to total
                  if(b.isSelected()){
                    price+=90;
                  //if not selected subtract service price from total
                  }else{
                    price-=90;
                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "9":
                  //add service price to total
                  if(b.isSelected()){
                    price+=20;
                     
                  //if not selected subtract service price from total
                  }else{
                    price-=20;

                  }
                  jeeppricefield.setText(String.valueOf(price));
                  break;
                case "10":
                  //add service price to total
                  if(b.isSelected()){
                    price+=3;
                     
                  //if not selected subtract service price from total
                  }else{
                    price-=3;
 
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
                       
                    //if not selected subtract service price from total
                    }else{
                      price-=6;
   
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
                       
                    //if not selected subtract service price from total
                    }else{
                      price-=8;
   
                    }
                    motopricefield.setText(String.valueOf(price));
                    break;
                  case "8":
                    //add service price to total
                    if(b.isSelected()){
                      price+=40;
                       
                    //if not selected subtract service price from total
                    }else{
                      price-=40;
   
                    }
                    motopricefield.setText(String.valueOf(price));
                    break;
                  case "9":
                    //add service price to total
                    if(b.isSelected()){
                      price+=10;
                       
                    //if not selected subtract service price from total
                    }else{
                      price-=10;
   
                    }
                    motopricefield.setText(String.valueOf(price));
                    break;
                }
              });
            }
          }
        return price;
    }
}