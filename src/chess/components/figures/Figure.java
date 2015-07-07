package chess.components.figures;

import java.util.List;

import application.GameManager;
import chess.components.Cell;
import chess.components.Move;
import chess.components.PlayerManager;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public abstract class Figure extends ImageView{
	
	protected String img;
	protected String color;
	protected boolean startPos = true;
	protected List<Move> tempMoves = null;
	protected boolean isSelected = false;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
		
		 // TODO: select image
	}

	public boolean isStartPos() {
		return startPos;
	}

	public void setStartPos(boolean startPos) {
		this.startPos = startPos;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public Figure(String color){
		this.setColor(color);
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
			    	 if(isSelected)
			        	 diselect();
			         else
			        	 if(GameManager.getInstance().getSelectedFigure() == null)
			        		 select();
		         
		         event.consume();
		     }
		});
	}
	
	public abstract List<Move> nextMoves(int current_l, int current_n);
	
	public void select(){
		if(!PlayerManager.getInstance().isTimeStarted())
			PlayerManager.getInstance().startTime();

		if(this.getColor().equals(PlayerManager.getInstance().getCurrentPlayerColor())) {
			Cell c = (Cell) this.getParent();
			tempMoves = this.nextMoves(GridPane.getColumnIndex(c),
					GridPane.getRowIndex(c));
			c.makeAvailable();
			GameManager.getInstance().showAvailableMoves(tempMoves);
			GameManager.getInstance().setSelectedFigure(this);
			isSelected = true;
		}
	}
	
	public void diselect(){
		Cell c = (Cell)this.getParent();
		
		c.makeNotAvailable();
		GameManager.getInstance().hideAvailableMoves(tempMoves);
		GameManager.getInstance().setSelectedFigure(null);
		this.tempMoves = null;
		isSelected = false;
	}

	public List<Move> getTempMoves() {
		return tempMoves;
	}
}
