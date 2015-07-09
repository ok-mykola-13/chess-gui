package application;

import java.net.URL;
import java.util.ResourceBundle;

import chess.components.Cell;
import chess.components.IObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainController implements Initializable, IObserver{

	@FXML
	private MenuItem closeItem;

	@FXML
	private MenuItem newComputerGameItem;

	@FXML
	private TextArea outputTextAra;

	@FXML
	private GridPane gridBoard;

	@FXML
	private MenuItem newHumanGameItem;

	@FXML
	private Label timerLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// initialize singleton GameManager---------------------------------------
		GameManager.getInstance().setBoard(gridBoard);
		GameManager.getInstance().setLog(outputTextAra);


		//initialize menu item----------------------------------------------
		closeItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				((Stage) gridBoard.getScene().getWindow()).close();
			}
		});

		final IObserver iObserver = this;
		newHumanGameItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GameManager.getInstance().endGame();
				outputTextAra.setText("");
				GameManager.getInstance().newGame(iObserver);
				GameManager.getInstance().unsetGameWithComputer();
				
				//TODO: not fully cleared resources (multiple lines in output, incorrect turns)
			}
		});

		newComputerGameItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GameManager gm = GameManager.getInstance();

				gm.endGame();
				outputTextAra.setText("");
				gm.newGame(iObserver);
				
				gm.setGameWithComputer();
				gm.engineNewGame();
				
				gm.engineMove(gm.engineNextMove(""));
			}
		});
		
		// paint the board --------------------------------------------------
		for(int i = 0; i < 8; i++)
			if(i%2 != 1){
				//black
				Cell c = new Cell("white");
				gridBoard.add(c, 0, i);
				Cell c2 = new Cell("white");
				gridBoard.add(c2, 2, i);
				Cell c4 = new Cell("white");
				gridBoard.add(c4, 4, i);
				Cell c6 = new Cell("white");
				gridBoard.add(c6, 6, i);
				
				//white
				Cell c1 = new Cell("gray");
				gridBoard.add(c1, 1, i);
				Cell c3 = new Cell("gray");
				gridBoard.add(c3, 3, i);
				Cell c5 = new Cell("gray");
				gridBoard.add(c5, 5, i);
				Cell c7 = new Cell("gray");
				gridBoard.add(c7, 7, i);
			}else{
				//black
				Cell c1 = new Cell("white");
				gridBoard.add(c1, 1, i);
				Cell c3 = new Cell("white");
				gridBoard.add(c3, 3, i);
				Cell c5 = new Cell("white");
				gridBoard.add(c5, 5, i);
				Cell c7 = new Cell("white");
				gridBoard.add(c7, 7, i);
				
				//white
				Cell c = new Cell("gray");
				gridBoard.add(c, 0, i);
				Cell c2 = new Cell("gray");
				gridBoard.add(c2, 2, i);
				Cell c4 = new Cell("gray");
				gridBoard.add(c4, 4, i);
				Cell c6 = new Cell("gray");
				gridBoard.add(c6, 6, i);
			}
		
		// for test
		GameManager.getInstance().newGame(this);
	}

    @Override
    public void update(String message) {
        if(outputTextAra.getText().isEmpty())
            outputTextAra.setText(message);
        else
            outputTextAra.setText(outputTextAra.getText() + "\n" + message);
    }

	public Label getTimerLabel(){
		return timerLabel;
	}
}
