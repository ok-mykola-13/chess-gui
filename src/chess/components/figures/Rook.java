package chess.components.figures;

import java.util.ArrayList;
import java.util.List;

import application.GameManager;
import javafx.scene.image.Image;
import chess.components.Move;

public class Rook extends Figure{

	public Rook(String color) {
		super(color);
		
		if (color.equals("white")) {
			this.setImage(new Image(getClass().getResourceAsStream(
					"/images/rook-white.png")));
		} else {
			this.setImage(new Image(getClass().getResourceAsStream(
					"/images/rook-black.png")));
		}
	}

	@Override
	public List<Move> nextMoves(int current_l, int current_n) {
		List<Move> moves = new ArrayList<Move>();
		
		//moves down
		for(int i = 1; i < 8-current_n; i++){
			if (GameManager.getInstance().isCellFree(current_l, current_n + i)){
				Move m = new Move(current_l, current_n, current_l,current_n + i);
				moves.add(m);
			}else if(GameManager.getInstance().canCellBeCaptured(current_l, current_n + i, this.color)){
				Move m = new Move(current_l, current_n, current_l,current_n + i);
				moves.add(m);
				break;
			}else
				break;
		}
		
		// moves up
		for(int i = 1; i <= current_n; i++){
			if (GameManager.getInstance().isCellFree(current_l, current_n - i)){
				Move m = new Move(current_l, current_n, current_l,current_n - i);
				moves.add(m);
			}else if(GameManager.getInstance().canCellBeCaptured(current_l, current_n - i, this.color)){
				Move m = new Move(current_l, current_n, current_l,current_n - i);
				moves.add(m);
				break;
			}else
				break;
		}
		
		// moves left
		for(int i = 1; i <= current_l; i++){
			if (GameManager.getInstance().isCellFree(current_l - i, current_n)){
				Move m = new Move(current_l, current_n, current_l - i,current_n);
				moves.add(m);
			}else if(GameManager.getInstance().canCellBeCaptured(current_l - i, current_n, this.color)){
				Move m = new Move(current_l, current_n, current_l - i,current_n);
				moves.add(m);
				break;
			}else
				break;
		}
		
		// moves right
		for(int i = 1; i < 8-current_l; i++){
			if (GameManager.getInstance().isCellFree(current_l + i, current_n)){
				Move m = new Move(current_l, current_n, current_l + i,current_n);
				moves.add(m);
			}else if(GameManager.getInstance().canCellBeCaptured(current_l + i, current_n, this.color)){
				Move m = new Move(current_l, current_n, current_l + i,current_n);
				moves.add(m);
				break;
			}else
				break;
		}
		
		return moves;
	}

}
