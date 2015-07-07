package chess.components;

import application.GameManager;
import application.Main;
import chess.components.figures.Figure;
import chess.components.figures.King;
import chess.components.figures.Rook;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class Cell extends StackPane implements ISubject{
	private List<IObserver> observers;
	
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
		this.setStyle("-fx-background-color: " + this.color + ";");
	}

	public Cell(String color) {
		setColor(color);
		observers = new ArrayList<>();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 moveFigureToHere();		         
		         event.consume();
		     }
		});
	}
	
	public void setFigure(Figure f){
		this.removeFigure();		
		this.getChildren().add(f);
	}
	
	public boolean hasFigure(){
		if(this.getChildren().size() > 0)
			return true;
		else
			return false;
	}
	
	public Figure getFigure(){
		return (Figure)this.getChildren().get(0);
	}
	
	public void removeFigure(){
		if(this.hasFigure())
			this.getChildren().clear();
	}
	
	public void makeAvailable(){		
		String image = Main.class.getResource("../images/move.png").toExternalForm();
		this.setStyle("-fx-background-color: " + this.color + "; "
				+ "-fx-background-image: url('"+ image +"');"
						+ "-fx-background-position: center center; "
						+ "-fx-background-repeat: no-repeat;");
	}
	public void makeNotAvailable(){
		this.setStyle("-fx-background-color: " + this.color + ";");
	}
	
	public void moveFigureToHere(){
		if(GameManager.getInstance().getSelectedFigure() != null
				&& this.getStyle().contains("image")){
			Figure f = GameManager.getInstance().getSelectedFigure();
			Cell c = (Cell)f.getParent();


            Move currentMove = new Move(GridPane.getColumnIndex(c),
                    GridPane.getRowIndex(c),
                    GridPane.getColumnIndex(this),
                    GridPane.getRowIndex(this));


            //Check current move and if king under attack cancel that move
            if((currentMove.getStart_l() == currentMove.getEnd_l() &&
                    currentMove.getStart_n() == currentMove.getEnd_n()) ||
                    GameManager.getInstance().isKingUnderAttack(f.getColor(), c, this, f)){
                f.diselect();
                return;
            }
            //-------------------------------------------------
					
			// if this is a Pawn or King, change startPos flag
			if(f.isStartPos())
				f.setStartPos(false); //if it is not start position then castling is impossible
			
			c.makeNotAvailable();
			c.getChildren().clear();
			
			// delete prev figure from cell if it exists
			if(this.getChildren().size() > 0) {
				//when figure was deleted from board we don`t need it anymore
				//so we need delete it from allFigure list
				GameManager.getInstance().getAllFigures().remove(this.getFigure());
				System.out.println("figure was deleted");
				this.getChildren().clear();
			}
			
			this.getChildren().add(f);

            //Castling-------------------------------------
            if(f instanceof King){
                if(Math.abs(currentMove.getEnd_l() - currentMove.getStart_l()) > 1) {
                    Cell cell = null;
                    Rook rook = null;

                    if (currentMove.getEnd_l() - currentMove.getStart_l() > 1)
                        rook = GameManager.getInstance().getRightRook(currentMove.getStart_l(),
                                currentMove.getStart_n());
                    else
                        rook = GameManager.getInstance().getLeftRook(currentMove.getStart_l(),
                                currentMove.getStart_n());

                    cell = (Cell) rook.getParent();
                    rook.setStartPos(false);
                    cell.makeNotAvailable();
                    cell.getChildren().clear();
                    if (currentMove.getEnd_l() - currentMove.getStart_l() > 1)
                        cell = GameManager.getInstance().getCell(currentMove.getEnd_l() - 1,
                                currentMove.getEnd_n());
                    else
                        cell = GameManager.getInstance().getCell(currentMove.getEnd_l() + 1,
                                currentMove.getEnd_n());
                    cell.getChildren().add(rook);
                }
            }
            //-----------------------------------------------

            //Text Area notification;
            String message = currentMove.generateAlgebraicNotation();

            String enemyColor = f.getColor().equals("white") ? "black" : "white";

            if(GameManager.getInstance().isKingUnderAttack(enemyColor)) {
                GameManager.getInstance().getKing(enemyColor).setIsUnderAttack(true);
                message = message + " : " + enemyColor + "Check!!!";
            }else{
                GameManager.getInstance().getKing(enemyColor).setIsUnderAttack(false);
            }
            if(GameManager.getInstance().isCheckMate(enemyColor))
                message = message + " : " + "Mate!!!";
            notifyObservers(message);

            //------------------------------------------------------
			f.diselect();
		}
	}

	@Override
	public void attachObserver(IObserver iObserver) {
		observers.add(iObserver);
	}

	@Override
	public void detachObserver(IObserver iObserver) {
        observers.remove(iObserver);
	}

	@Override
	public void notifyObservers(String message) {
        for (IObserver iObserver : observers){
            iObserver.update(message);
        }
	}
}
