package gr.uop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UI {
    


    UI(){
        
    }
    public HBox createLogo(){
        var lb_incBook = new Label("Income Book");
        lb_incBook.setTextFill(Color.web("#FFFFFF"));
        lb_incBook.setFont(Font.font("Arial",FontWeight.BOLD,30));
        StackPane p_st_lbIncBook = new StackPane(lb_incBook);
    
        Image im_logo = new Image(Server.class.getResourceAsStream("img/logo.png"));
        ImageView iv_logo = new ImageView(im_logo);
        iv_logo.setFitHeight(150);
        iv_logo.setFitWidth(200);
    
        HBox p_hb_logo = new HBox(); 
        p_hb_logo.getChildren().addAll(iv_logo,p_st_lbIncBook);
        return p_hb_logo;
      }
    
      public HBox createRefreshButton(Button refreshButton){
        refreshButton.setPrefSize(60, 60);
        Image im_refresh = new Image(Server.class.getResourceAsStream("img/re2.png"));
        ImageView refresh_icon = new ImageView(im_refresh);
        refresh_icon.setFitHeight(20);
        refresh_icon.setFitWidth(20);
        refreshButton.setGraphic(refresh_icon);
        refreshButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;");
        refreshButton.setOnMousePressed(event -> refreshButton.setStyle("-fx-background-color: transparent; -fx-padding: 3 1 1 3;"));
        refreshButton.setOnMouseReleased(event -> refreshButton.setStyle("-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;"));
        HBox p_hb_refreshButton = new HBox();
        p_hb_refreshButton.getChildren().add(refreshButton);
        p_hb_refreshButton.setAlignment(Pos.TOP_RIGHT);
        p_hb_refreshButton.setPadding(new Insets(0));
    
        return p_hb_refreshButton;
      }
    
      public TextField createSearchField(){
            TextField tf_search = new TextField();
            tf_search.setPromptText("Αναζητήστε ID, Αριθμό Πινακίδας ή Όχημα");
            tf_search.setPrefWidth(210);
            BorderPane p_bp_search = new BorderPane();
            p_bp_search.setLeft(tf_search);    
                 
            return tf_search;
      }
}
