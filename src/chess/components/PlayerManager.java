package chess.components;

import application.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class PlayerManager implements IObserver{

    public static final int TIME_FOR_MOVE = 30;

    private int time;
    private static PlayerManager instance = null;
    private Timeline tenSecondsWonder;
    private Label timeLabel;

    private String currentPlayer;
    private boolean timeStarted;

    public static PlayerManager getInstance(){
        if(instance == null)
            instance = new PlayerManager();

        return instance;
    }

    public PlayerManager(){
        this.currentPlayer = "white";
        this.time = 0;

        //initialize timer for fast chess
        tenSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1),
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                time++;
                if(time >= TIME_FOR_MOVE) {
                    if (GameManager.getInstance().getSelectedFigure() != null)
                        GameManager.getInstance().getSelectedFigure().diselect();
                    changePlayer();
                }
                showCurrentTime();
            }
        }));
        tenSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        timeStarted = false;
    }

    public String getCurrentPlayerColor() {
        return currentPlayer;
    }

    @Override
    public void update(String message) {
        if(message.contains("Mate"))
            stopTime();
        changePlayer();
    }

    public void changePlayer(){
        this.currentPlayer = this.currentPlayer.equals("white") ? "black" : "white";
        time = 0;
        showCurrentTime();
    }

    public void showCurrentTime(){
        String timeString = String.format(currentPlayer + " 00:%02d", 30 - time);
        timeLabel.setText(timeString);
    }

    public void setTimeLabel(Label timeLabel){
        this.timeLabel = timeLabel;
    }

    public void startTime(){
        timeStarted = true;
        tenSecondsWonder.play();
    }

    public void stopTime(){
        timeStarted = false;
        tenSecondsWonder.stop();
    }

    public boolean isTimeStarted() {
        return timeStarted;
    }
}
