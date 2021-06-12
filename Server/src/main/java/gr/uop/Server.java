package gr.uop;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        var label = new Label("Income Book");
        label.setTextFill(Color.web("#FFFFFF"));
        label.setFont(Font.font("Arial",FontWeight.BOLD,30));
        StackPane st = new StackPane(label);
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
        HBox hb = new HBox();
        Image im = new Image(Server.class.getResourceAsStream("logo.png"));
        ImageView imageView = new ImageView(im);
        imageView.setFitHeight(150);
        imageView.setFitWidth(200);
        hb.getChildren().addAll(imageView,st);
        VBox vb = new VBox();   
        vb.setStyle("-fx-background-color:#abdbe3;");
        vb.setSpacing(15);
        vb.setPadding(new Insets(20,20,20,20));
        vb.getChildren().addAll(hb, table);
        var scene = new Scene(vb, 1024, 768);
      
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
