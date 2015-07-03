package chess.components;

import application.GameManager;
import application.Main;
import chess.components.figures.Figure;
import chess.components.figures.Pawn;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class Cell extends StackPane{
	
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
					
			// if this is a Pawn, change startPos flag
			if((f instanceof Pawn) && (f.isStartPos()))
				((Pawn)f).setStartPos(false);
			
			c.makeNotAvailable();
			c.getChildren().clear();
			
			// delete prev figure from cell if it exists
			if(this.getChildren().size() > 0)
				this.getChildren().clear();
			
			this.getChildren().add(f);
			
			f.diselect();
		}
	}
}
