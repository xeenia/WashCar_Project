package gr.uop;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Server extends Application {

    @Override
    public void start(Stage stage) {
        var lb_incBook = new Label("Income Book");
        lb_incBook.setTextFill(Color.web("#FFFFFF"));
        lb_incBook.setFont(Font.font("Arial",FontWeight.BOLD,30));
        StackPane p_st_lbIncBook = new StackPane(lb_incBook);

        Image im_logo = new Image(Server.class.getResourceAsStream("logo.png"));
        ImageView iv_logo = new ImageView(im_logo);
        iv_logo.setFitHeight(150);
        iv_logo.setFitWidth(200);

        Button refreshButton = new Button();
        refreshButton.setPrefSize(60, 60);
        Image im_refresh = new Image(Server.class.getResourceAsStream("re2.png"));
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

        TextField tf_search = new TextField();
        tf_search.setPromptText("Search for time, date or Car Number");
        tf_search.setPrefWidth(210);
        BorderPane p_bp_search = new BorderPane();
        p_bp_search.setLeft(tf_search);
        
        TableView table = new TableView();
        table.setEditable(true);
        table.setPrefHeight(Integer.MAX_VALUE);
        TableColumn idCol = new TableColumn("ID");
        TableColumn dateCol = new TableColumn("Date");
        TableColumn timeCol = new TableColumn("Time");
        TableColumn carCol = new TableColumn("Car Number");
        TableColumn costCol = new TableColumn("Cost");
        TableColumn b1Col = new TableColumn("Acceptance");
        TableColumn b2Col = new TableColumn("Annulment");

        table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        
        idCol.minWidthProperty().bind(table.widthProperty().multiply(0.05));
        dateCol.minWidthProperty().bind(table.widthProperty().multiply(.1));
        timeCol.minWidthProperty().bind(table.widthProperty().multiply(.1));
        carCol.minWidthProperty().bind(table.widthProperty().multiply(.25));
        costCol.minWidthProperty().bind(table.widthProperty().multiply(.25));
        b1Col.minWidthProperty().bind(table.widthProperty().multiply(.05));
        b2Col.minWidthProperty().bind(table.widthProperty().multiply(.05));
        table.getColumns().addAll(idCol,dateCol, timeCol, carCol, costCol,b1Col,b2Col);
        
        HBox p_hb_logo = new HBox(); 
        p_hb_logo.getChildren().addAll(iv_logo,p_st_lbIncBook);
        VBox p_vb_mainPage = new VBox(); 
        VBox p_vb_center = new VBox();
        p_vb_center.getChildren().addAll(p_bp_search,table);
        p_vb_mainPage.setStyle("-fx-background-color:#abdbe3;");
        p_vb_mainPage.setSpacing(15);
        p_vb_mainPage.setPadding(new Insets(20,20,20,20));
        p_vb_mainPage.getChildren().addAll(p_hb_refreshButton,p_hb_logo,p_vb_center);
        p_vb_mainPage.setSpacing(0);
        var scene = new Scene(p_vb_mainPage, 1024, 768);
        
        stage.setScene(scene);
        stage.setMinHeight(768);
        stage.setMinWidth(1024);
        stage.setTitle("CASH DESK");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
