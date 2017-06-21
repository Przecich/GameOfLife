package app.controller;

import app.MainApp;

import app.model.GameOfLife;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FlowController {
    private MainApp mainApp;
    private GraphicsContext gc;
    private GameOfLife gameOfLife;
    private double scaleX;
    private double scaleY;
    private Timeline timeline;
    private int iterationsCounter=0;


    @FXML
    private TextField widthField;
    @FXML
    private Slider speedBar;
    @FXML
    private Text textIterations;

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
        updateCounter(0);
    }

    @FXML
    private void canvasMouseClick(MouseEvent e) {
        double x = (int) ((e.getX() / scaleX)) * scaleX;
        double y = (int) ((e.getY() / scaleY)) * scaleY;

        gc.fillRect(x, y, scaleX, scaleY);
        gameOfLife.setField((int) Math.round(x / scaleX), (int) Math.round(y / scaleY), true);
    }
    @FXML
    private void onClose(){
        Platform.exit();
    }
    @FXML private void onClear(){
        gameOfLife.clearBoard();
        paintBoard();
    }



    private void startTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(500 / speedBar.getValue()), event -> {
            gameOfLife.updateBoard();
            paintBoard();
            updateCounter(1);
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
    private void updateCounter(int n){
        iterationsCounter=(iterationsCounter+n)*n;
        textIterations.setText(Integer.toString(iterationsCounter));
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
