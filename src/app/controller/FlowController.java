package app.controller;

import app.MainApp;

import app.model.BoardObserver;
import app.model.GameOfLife;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.awt.*;
import java.nio.Buffer;

public class FlowController {
    private MainApp mainApp;
    private GraphicsContext gc;
    private GameOfLife gameOfLife;
    private double scaleX;
    private double scaleY;
    private Timeline timeline;


    @FXML
    private TextField widthField;
    @FXML
    private Slider speedBar;

    @FXML
    private void handleStartButton() {
        startTimeline();
    }
    @FXML
    private void handlePauseButton(){
        timeline.pause();
    }
    @FXML
    private void handleStopButton(){
        timeline.stop();
        gameOfLife.clearBoard();
        paintBoard();
    }

    @FXML
    private void canvasMouseClick(MouseEvent e) {
        double x = (int) ((e.getX() / scaleX)) * scaleX;
        double y = (int) ((e.getY() / scaleY)) * scaleY;

        gc.fillRect(x, y, scaleX, scaleY);
        gameOfLife.setField((int) Math.round(x / scaleX), (int) Math.round(y / scaleY), true);
    }



    private void startTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(500 / speedBar.getValue()), event -> {
            gameOfLife.updateBoard();
            paintBoard();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void paintBoard() {
        gc.clearRect(0, 0, mainApp.getCanvas().getWidth(), mainApp.getCanvas().getHeight());
        for (int i = 0; i < gameOfLife.board.size(); i++)
            if (gameOfLife.board.get(i))
                gc.fillRect((i % gameOfLife.width) * scaleX, (i / gameOfLife.height) * scaleY, scaleX, scaleY);



        gc.setFill(Color.RED);
    }

    public void calculateScale() {
        scaleX = mainApp.getCanvas().getWidth() / gameOfLife.width;
        scaleY = mainApp.getCanvas().getHeight() / gameOfLife.height;

    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        gc = mainApp.getCanvas().getGraphicsContext2D();
    }

    public void setModel(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }
}
