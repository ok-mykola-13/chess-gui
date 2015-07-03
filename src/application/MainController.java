package application;

import java.net.URL;
import java.util.ResourceBundle;

import chess.components.Cell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class MainController implements Initializable{
	
	@FXML
	private GridPane gridBoard;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// initialize singleton GameManager---------------------------------------
		GameManager.getInstance().setBoard(gridBoard);
		
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
		GameManager.getInstance().newGame();
	}
}
