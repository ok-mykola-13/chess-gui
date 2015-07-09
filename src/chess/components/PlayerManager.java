package chess.components;

import application.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class PlayerManager implements IObserver{

    private static PlayerManager instance = null;
    private Timeline tenSecondsWonder;

    private boolean timeStarted;

    public static PlayerManager getInstance(){
        if(instance == null)
            instance = new PlayerManager();

        return instance;
    }

    private String currentPlayer;

    public PlayerManager(){
        this.currentPlayer = "white";

        //initialize timer for fast chess
        //time duration 20 seconds
        tenSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(20),
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(GameManager.getInstance().getSelectedFigure() != null)
                    GameManager.getInstance().getSelectedFigure().diselect();
                changePlayer();
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
        
        
        GameManager gm = GameManager.getInstance();
        if(this.currentPlayer.equals("white") && 
        		gm.isGameWithComputer()){
        	
        	gm.engineMove(gm.engineNextMove(gm.getAllMoves()));
        	changePlayer();
        }
        
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
