package app;

import java.io.IOException;

import app.controller.FlowController;
import app.model.GameOfLife;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Canvas canvas;
    private Paint startColor;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Game of Life");

        initRootLayout();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        System.out.println(gc.getFill());
        startColor=gc.getFill();
        gc.setFill( Color.RED );



    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/GameOfLifeGUI.fxml"));
            rootLayout = loader.load();

            SplitPane splitPane=(SplitPane) rootLayout.getCenter();
            AnchorPane anchorPane=(AnchorPane)splitPane.getItems().get(0);
            canvas=(Canvas)anchorPane.getChildren().get(0);
            // Show the scene containing the root layout.
            FlowController flowController=loader.getController();
            flowController.setMainApp(this);
            flowController.setModel(new GameOfLife());
            flowController.calculateScale();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public Paint getDefaultColor(){
        return startColor;
    }
}