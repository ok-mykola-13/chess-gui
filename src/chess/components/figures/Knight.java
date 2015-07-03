package chess.components.figures;

import java.util.ArrayList;
import java.util.List;

import application.GameManager;
import javafx.scene.image.Image;
import chess.components.Move;

public class Knight extends Figure{

	public Knight(String color) {
		super(color);
		
		if (color.equals("white")) {
			this.setImage(new Image(getClass().getResourceAsStream(
					"/images/knight-white.png")));
		} else {
			this.setImage(new Image(getClass().getResourceAsStream(
					"/images/knight-black.png")));
		}
	}

	@Override
	public List<Move> nextMoves(int current_l, int current_n) {
		List<Move> moves = new ArrayList<Move>();
		
		if((current_n > 1 && current_l > 0 &&
				GameManager.getInstance().isCellFree(current_l - 1, current_n - 2))
			||
			(current_n > 1 && current_l > 0 &&
				GameManager.getInstance().canCellBeCaptured(current_l - 1, current_n - 2, this.color))){
			Move m = new Move(current_l, current_n, current_l - 1, current_n - 2);
			moves.add(m);
		}
		if((current_n > 0 && current_l > 1 &&
				GameManager.getInstance().isCellFree(current_l - 2, current_n - 1))
			||
			(current_n > 0 && current_l > 1 &&
				GameManager.getInstance().canCellBeCaptured(current_l - 2, current_n - 1, this.color))){
			Move m = new Move(current_l, current_n, current_l - 2, current_n - 1);
			moves.add(m);
		}
		
		if((current_n < 7 && current_l > 1 &&
				GameManager.getInstance().isCellFree(current_l - 2, current_n + 1))
			||
			(current_n < 7 && current_l > 1 &&
				GameManager.getInstance().canCellBeCaptured(current_l - 2, current_n + 1, this.color))){
			Move m = new Move(current_l, current_n, current_l - 2, current_n + 1);
			moves.add(m);
		}
		if((current_n < 6 && current_l > 1 &&
				GameManager.getInstance().isCellFree(current_l - 1, current_n + 2))
			||
			(current_n < 6 && current_l > 1 &&
				GameManager.getInstance().canCellBeCaptured(current_l - 1, current_n + 2, this.color))){
			Move m = new Move(current_l, current_n, current_l - 1, current_n + 2);
			moves.add(m);
		}
		
		if((current_n < 6 && current_l < 7 &&
				GameManager.getInstance().isCellFree(current_l + 1, current_n + 2))
			||
			(current_n < 6 && current_l < 7 &&
				GameManager.getInstance().canCellBeCaptured(current_l + 1, current_n + 2, this.color))){
			Move m = new Move(current_l, current_n, current_l + 1, current_n + 2);
			moves.add(m);
		}
		if((current_n < 7 && current_l < 6 &&
				GameManager.getInstance().isCellFree(current_l + 2, current_n + 1))
			||
			(current_n < 7 && current_l < 6 &&
				GameManager.getInstance().canCellBeCaptured(current_l + 2, current_n + 1, this.color))){
			Move m = new Move(current_l, current_n, current_l + 2, current_n + 1);
			moves.add(m);
		}
		
		if((current_n > 0 && current_l < 6 &&
				GameManager.getInstance().isCellFree(current_l + 2, current_n - 1))
			||
			(current_n > 0 && current_l < 6 &&
				GameManager.getInstance().canCellBeCaptured(current_l + 2, current_n - 1, this.color))){
			Move m = new Move(current_l, current_n, current_l + 2, current_n - 1);
			moves.add(m);
		}
		if((current_n > 1 && current_l < 7 &&
				GameManager.getInstance().isCellFree(current_l + 1, current_n - 2))
			||
			(current_n > 1 && current_l < 7 &&
				GameManager.getInstance().canCellBeCaptured(current_l + 1, current_n - 2, this.color))){
			Move m = new Move(current_l, current_n, current_l + 1, current_n - 2);
			moves.add(m);
		}
		
		return moves;
	}

}
