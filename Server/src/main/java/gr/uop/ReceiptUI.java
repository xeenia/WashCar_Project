package gr.uop;

import java.io.File;
import java.time.format.DateTimeFormatter;

import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
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
public class ReceiptUI {
    protected String s_Labels[] = {
        "Πλύσιμο εξωτερικό","Πλύσιμο εσωτερικό",
        "Πλύσιμο εξωτερικό & εσωτερικό", "Πλύσιμο εξωτερικό σπέσιαλ",
        "Πλύσιμο εσωτερικό σπέσιαλ","Πλύσιμο εξωτερικό & εσωτερικό σπέσιαλ",
        "Βιολογικός καθαρισμός εσωτερικό","Κέρωμα‐Γυάλισμα","Καθαρισμός κινητήρα",
        "Πλύσιμο σασί"
    };
    protected String s_Car_Labels[] ={"7","6","12","9","8","15","80","80","20","3"};
    protected String s_Jeep_Labels[]={"8","7","14","10","9","17","80","90","20","3"};
    protected String s_Motor_Labels[]={"6","0","0","8","0","0","0","40","10","0"};
    protected Vehicle vehicle;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    protected String depTime = java.time.LocalTime.now().format(dtf);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
    protected String depDate = java.time.LocalDate.now().format(formatter);
    protected Button b_payButton;
    protected Button b_cancelButton;
   
   public String getDepTime(){
    return depTime;
   }
   public String getDepDate(){
    return depDate;
   }
   public Button getPayButton(){
    return b_payButton;
   }
   public Button getCancelButton(){
    return b_cancelButton;
   }
    private Pane makeLogo(){
        Pane p_logo = new Pane();
        p_logo.setStyle("-fx-background-color:#abdbe3;");
        Image im_logo = new Image(Server.class.getResourceAsStream("img/logo.png"));
        ImageView iv_logo = new ImageView(im_logo);
        iv_logo.setFitHeight(100);
        iv_logo.setFitWidth(150);
        p_logo.getChildren().add(iv_logo);
        return p_logo;
    }

    private BorderPane makeVehicleAndReceiptInfo(){
        BorderPane p_hb_info = new BorderPane();
        Text t_carInfo = new Text("Όχημα: "+vehicle.getVehicleType()+"\nΠινακίδα: "+ 
        vehicle.getVehicle_number()+"\nΑναχώρηση:\n"+vehicle.getDate()+"\n" +vehicle.getArrival_time());
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
        return p_hb_info;
    }

    private BorderPane makeServicesInfo(){
        BorderPane p_hb_receiptInfo = new BorderPane();
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

        String services[] = vehicle.getServices().split("-");
        int size = services.length;
        Text v1 = new Text();
        Text v2 = new Text();
        Text v3 = new Text();
        String s1="",s2="",s3="";

        for(int i =0; i<size; i++){
            String n = ((Integer.parseInt(services[i]))!=10)?"0":"";
            s1=s1.concat(n+services[i]+((i==size-1)?"":"\n"));
            s2=s2.concat(s_Labels[Integer.parseInt(services[i])-1]+((i==size-1)?"":"\n"));
            if(vehicle.getVehicleType().contains("Car")){
            s3= s3.concat(s_Car_Labels[Integer.parseInt(services[i])-1]+" €"+((i==size-1)?"":"\n"));
            }else if(vehicle.getVehicleType().contains("Jeep")){
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
        return p_hb_receiptInfo;
    }

    private BorderPane makeAmount(){
        BorderPane p_hb_amount = new BorderPane();
        Pane p_color = new Pane();
        p_color.setStyle("-fx-background-color:#abdbe3;");
        VBox p_cost = new VBox();
        p_cost.setPadding(new Insets(5,10,5,10));
        Text t_cost = new Text(String.valueOf(vehicle.getCost())+" €");
        
        t_cost.setStyle("-fx-font-size: 40;");
        t_cost.setFill(Color.WHITE);
        Text l_amount = new Text("Συνολικά:");
        l_amount.setStyle("-fx-font-weight: bold; -fx-font-size: 13;");
        l_amount.setFill(Color.WHITE);
        p_cost.getChildren().addAll(l_amount,t_cost);
        p_cost.setAlignment(Pos.CENTER_RIGHT);
        p_cost.setStyle("-fx-background-color:#79c5d4;");   
        p_hb_amount.setCenter(p_color);
        p_hb_amount.setRight(p_cost);
        return p_hb_amount;
    }

    public BorderPane makeReceiptUI(){
        SplitPane sp = new SplitPane();
                    
        BorderPane p_footer = new BorderPane();
        p_footer.setStyle("-fx-background-color:#ffffff;");
        b_payButton = new Button("Πληρωμή");
        File f = new File("IncomeBook.css");
        
        b_payButton.getStylesheets().clear();
        b_payButton.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        b_cancelButton = new Button ("Ακύρωση");
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

        p_vb_mainTop.getChildren().addAll(makeLogo() ,makeVehicleAndReceiptInfo(),sp);
        p_vb_mainBottom.getChildren().addAll(makeAmount(),p_footer);
        p_bp_main.setTop(p_vb_mainTop);
        p_bp_main.setCenter(makeServicesInfo());
        p_bp_main.setBottom(p_vb_mainBottom);
        return p_bp_main;
    }
    ReceiptUI(Vehicle vehicle){
        this.vehicle=vehicle;
        
    }
}
