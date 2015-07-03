package chess.components.figures;

import java.util.ArrayList;
import java.util.List;

import application.GameManager;
import javafx.scene.image.Image;
import chess.components.Move;

public class King extends Figure{

	public King(String color) {
		super(color);
		if (color.equals("white")) {
			this.setImage(new Image(getClass().getResourceAsStream(
					"/images/king-white.png")));
		} else {
			this.setImage(new Image(getClass().getResourceAsStream(
					"/images/king-black.png")));
		}
	}

	@Override
	public List<Move> nextMoves(int current_l, int current_n) {
		List<Move> moves = new ArrayList<Move>();
		
		//TODO: implement casting
		
		//TODO: check if king can go there
		
		
		//simple one step moves
		if ((current_n < 7 &&
				GameManager.getInstance().isCellFree(current_l, current_n + 1))
			||
			(current_n < 7 &&
					GameManager.getInstance().canCellBeCaptured(current_l, current_n + 1, this.color))) {
			Move m = new Move(current_l, current_n, current_l,current_n + 1);
			moves.add(m);
		}
		if ((current_n > 0 &&
				GameManager.getInstance().isCellFree(current_l, current_n - 1))
			||
			(current_n > 0 &&
					GameManager.getInstance().canCellBeCaptured(current_l, current_n - 1, this.color))) {
			Move m = new Move(current_l, current_n, current_l,current_n - 1);
			moves.add(m);
		}
		if ((current_l < 7 &&
				GameManager.getInstance().isCellFree(current_l + 1, current_n))
			||
			(current_l < 7 &&
					GameManager.getInstance().canCellBeCaptured(current_l + 1, current_n, this.color))) {
			Move m = new Move(current_l, current_n, current_l + 1,current_n);
			moves.add(m);
		}
		if ((current_l > 0 &&
				GameManager.getInstance().isCellFree(current_l - 1, current_n))
			||
			(current_l > 0 &&
					GameManager.getInstance().canCellBeCaptured(current_l - 1, current_n, this.color))) {
			Move m = new Move(current_l, current_n, current_l - 1,current_n);
			moves.add(m);
		}
		if ((current_n > 0 && current_l > 0 &&
				GameManager.getInstance().isCellFree(current_l - 1, current_n - 1))
			||
			(current_n > 0 && current_l > 0 &&
				GameManager.getInstance().canCellBeCaptured(current_l - 1, current_n - 1, this.color))) {
			Move m = new Move(current_l, current_n, current_l - 1,current_n - 1);
			moves.add(m);
		}
		if ((current_n < 7 && current_l < 7 &&
				GameManager.getInstance().isCellFree(current_l + 1, current_n + 1))
			||
			(current_n < 7 && current_l < 7 &&
				GameManager.getInstance().canCellBeCaptured(current_l + 1, current_n + 1, this.color))) {
			Move m = new Move(current_l, current_n, current_l + 1,current_n + 1);
			moves.add(m);
		}
		if ((current_n > 0 && current_l < 7 &&
				GameManager.getInstance().isCellFree(current_l + 1, current_n - 1))
			||
			(current_n > 0 && current_l < 7 &&
				GameManager.getInstance().canCellBeCaptured(current_l + 1, current_n - 1, this.color))) {
			Move m = new Move(current_l, current_n, current_l + 1,current_n - 1);
			moves.add(m);
		}
		if ((current_n < 7 && current_l > 0 &&
				GameManager.getInstance().isCellFree(current_l - 1, current_n + 1))
			||
			(current_n < 7 && current_l > 0 &&
			GameManager.getInstance().canCellBeCaptured(current_l - 1, current_n + 1, this.color))) {
			Move m = new Move(current_l, current_n, current_l - 1,current_n + 1);
			moves.add(m);
		}
		
		return moves;
	}

}
