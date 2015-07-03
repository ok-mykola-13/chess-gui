package chess.components.figures;

import java.util.List;

import application.GameManager;
import chess.components.Cell;
import chess.components.Move;
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
		Cell c = (Cell)this.getParent();
		tempMoves = this.nextMoves(GridPane.getColumnIndex(c), 
									GridPane.getRowIndex(c));
		/*****************************************************************/
		// protect their own king 
		
		King k = GameManager.getInstance().getKing(this.color);
		Cell k_cell = (Cell)k.getParent();
		
		int k_l = GridPane.getColumnIndex(k_cell);
		int k_n = GridPane.getRowIndex(k_cell);
		
		for(Figure f : GameManager.getInstance().getAllFigures()){
			if(!f.getColor().equals(this.color)
					&& (f instanceof Rook
							|| f instanceof Bishop
							|| f instanceof Queen)){
				
			}
		}
		
		
		/*****************************************************************/
		c.makeAvailable();
		GameManager.getInstance().showAvailableMoves(tempMoves);
		GameManager.getInstance().setSelectedFigure(this);
		isSelected = true;
	}
	
	public void diselect(){
		Cell c = (Cell)this.getParent();
		
		c.makeNotAvailable();
		GameManager.getInstance().hideAvailableMoves(tempMoves);
		GameManager.getInstance().setSelectedFigure(null);
		this.tempMoves = null;
		isSelected = false;
	}
}
