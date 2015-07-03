package application;

import java.util.ArrayList;
import java.util.List;

import chess.components.Cell;
import chess.components.Move;
import chess.components.figures.Bishop;
import chess.components.figures.Figure;
import chess.components.figures.King;
import chess.components.figures.Knight;
import chess.components.figures.Pawn;
import chess.components.figures.Queen;
import chess.components.figures.Rook;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GameManager {
	
	private static GameManager instance = null;
	
	private GameManager(){}
	
	public static GameManager getInstance(){
		if(instance == null)
			instance = new GameManager();
		
		return instance;
	}
	
	private GridPane board;
	
	private Figure selectedFigure;
	private List<Figure> allFigures = null;
	
	public List<Figure> getAllFigures() {
		return allFigures;
	}

	public void setAllFigures(List<Figure> allFigures) {
		this.allFigures = allFigures;
	}

	public Figure getSelectedFigure() {
		return selectedFigure;
	}

	public void setSelectedFigure(Figure selectedFigure) {
		this.selectedFigure = selectedFigure;
	}

	public void setBoard(GridPane board){
		this.board = board;
	}
	
	public boolean isCellFree(int l, int n){
		Cell c = null;
		// find cell on the grid
		for (Node node : board.getChildren()) {
			
		    if (GridPane.getColumnIndex(node) == l && GridPane.getRowIndex(node) == n) {
		    	c = (Cell)node;
		    	break;
		    }
		}
		return !c.hasFigure();
	}
	public boolean canCellBeCaptured(int l, int n, String color){
		Cell c = null;
		// find cell on the grid
		for (Node node : board.getChildren()) {
			
		    if (GridPane.getColumnIndex(node) == l && GridPane.getRowIndex(node) == n) {
		    	c = (Cell)node;
		    	break;
		    }
		}

		if(c.hasFigure() && !(c.getFigure().getColor().equals(color)))
			return true;
		else 
			return false;
	}
	
	public void showAvailableMoves(List<Move> moves){
		for(Move m : moves){
			Cell c = (Cell)Utils.getNodeFromGridPane(board, m.getEnd_l(), m.getEnd_n());
			c.makeAvailable();
		}
	}
	public void hideAvailableMoves(List<Move> moves){
		for(Move m : moves){
			Cell c = (Cell)Utils.getNodeFromGridPane(board, m.getEnd_l(), m.getEnd_n());
			c.makeNotAvailable();
		}
	}
	
	// Game methods ------------------------------------------------------
	
	//TODO: analyze check, mate
	public void newGame(){
		
		clearBoard();
		
		this.allFigures = new ArrayList<>();
		
		Cell c;
		Figure f;
		//generate white set
		for(int i = 0; i < 8; i++){
			c = (Cell)Utils.getNodeFromGridPane(board, i, 1);
			f = new Pawn("white");
			this.allFigures.add(f);
			c.getChildren().add(f);
		}
		c = (Cell)Utils.getNodeFromGridPane(board, 0, 0);
		f = new Rook("white");
		this.allFigures.add(f);
		c.getChildren().add(f);
		c = (Cell)Utils.getNodeFromGridPane(board, 7, 0);
		f = new Rook("white");
		this.allFigures.add(f);
		c.getChildren().add(f);
		
		c = (Cell)Utils.getNodeFromGridPane(board, 1, 0);
		f = new Knight("white");
		this.allFigures.add(f);
		c.getChildren().add(f);
		c = (Cell)Utils.getNodeFromGridPane(board, 6, 0);
		f = new Knight("white");
		this.allFigures.add(f);
		c.getChildren().add(f);
		
		c = (Cell)Utils.getNodeFromGridPane(board, 2, 0);
		f = new Bishop("white");
		this.allFigures.add(f);
		c.getChildren().add(f);
		c = (Cell)Utils.getNodeFromGridPane(board, 5, 0);
		f = new Bishop("white");
		this.allFigures.add(f);
		c.getChildren().add(f);
		
		c = (Cell)Utils.getNodeFromGridPane(board, 3, 0);
		f = new King("white");
		this.allFigures.add(f);
		c.getChildren().add(f);
		c = (Cell)Utils.getNodeFromGridPane(board, 4, 0);
		f = new Queen("white");
		this.allFigures.add(f);
		c.getChildren().add(f);
		
		// generate black set
		for(int i = 0; i < 8; i++){
			c = (Cell)Utils.getNodeFromGridPane(board, i, 6);
			f = new Pawn("black");
			this.allFigures.add(f);
			c.getChildren().add(f);
		}
		c = (Cell)Utils.getNodeFromGridPane(board, 0, 7);
		f = new Rook("black");
		this.allFigures.add(f);
		c.getChildren().add(f);
		c = (Cell)Utils.getNodeFromGridPane(board, 7, 7);
		f = new Rook("black");
		this.allFigures.add(f);
		c.getChildren().add(f);
		
		c = (Cell)Utils.getNodeFromGridPane(board, 1, 7);
		f = new Knight("black");
		this.allFigures.add(f);
		c.getChildren().add(f);
		c = (Cell)Utils.getNodeFromGridPane(board, 6, 7);
		f = new Knight("black");
		this.allFigures.add(f);
		c.getChildren().add(f);
		
		c = (Cell)Utils.getNodeFromGridPane(board, 2, 7);
		f = new Bishop("black");
		this.allFigures.add(f);
		c.getChildren().add(f);
		c = (Cell)Utils.getNodeFromGridPane(board, 5, 7);
		f = new Bishop("black");
		this.allFigures.add(f);
		c.getChildren().add(f);
		
		c = (Cell)Utils.getNodeFromGridPane(board, 3, 7);
		f = new King("black");
		this.allFigures.add(f);
		c.getChildren().add(f);
		c = (Cell)Utils.getNodeFromGridPane(board, 4, 7);
		f = new Queen("black");
		this.allFigures.add(f);
		c.getChildren().add(f);
	}
	
	public void clearBoard(){
		for(Node n : board.getChildren()){
			Cell c = (Cell)n;
			c.getChildren().clear();
		}
			
	}
	
	public King getKing(String color){
		for(Figure f : this.allFigures){
			if(f instanceof King && f.getColor().equals(color))
				return (King)f;
		}
		return null;
	}
}
