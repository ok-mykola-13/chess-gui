package chess.components.figures;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import application.GameManager;
import chess.components.Move;

public class Pawn extends Figure {

	public Pawn(String color) {
		super(color);

		if (color.equals("white")) {
			this.setImage(new Image(getClass().getResourceAsStream(
					"/images/pawn-white.png")));
		} else {
			this.setImage(new Image(getClass().getResourceAsStream(
					"/images/pawn-black.png")));
		}
	}

	@Override
	public List<Move> nextMoves(int current_l, int current_n) {

		//TODO: check "borders" case.
		
		List<Move> moves = new ArrayList<>();
		if (this.color == "white") {
			// this is a white one pawn
			
			// two steps forward(only on start position)
			if (this.startPos && GameManager.getInstance().isCellFree(current_l, current_n +1)
					&& GameManager.getInstance().isCellFree(current_l, current_n + 2)) {
				Move m = new Move(current_l, current_n, current_l, current_n + 2);
				moves.add(m);
			}

			// simply one step forward
			if (current_n < 7 &&
					GameManager.getInstance().isCellFree(current_l, current_n +1)) {
				Move m = new Move(current_l, current_n, current_l,current_n + 1);
				moves.add(m);
			}

			// check if this pawn can capture something (diagonals)
			if (current_n < 7 && current_l < 7 &&
					GameManager.getInstance().canCellBeCaptured(current_l + 1,current_n + 1, this.color)) {
				Move m = new Move(current_l, current_n, current_l + 1,current_n + 1);
				moves.add(m);
			}

			if (current_n < 7 && current_l > 0 &&
					GameManager.getInstance().canCellBeCaptured(current_l - 1,current_n + 1, this.color)) {
				Move m = new Move(current_l, current_n, current_l - 1,current_n + 1);
				moves.add(m);
			}
		} else {
			// Black one
			// two steps forward (only on start position)
			if (this.startPos && GameManager.getInstance().isCellFree(current_l, current_n -1)
					&& GameManager.getInstance().isCellFree(current_l,current_n -2)) {
				Move m = new Move(current_l, current_n, current_l,current_n - 2);
				moves.add(m);
			}

			// simply one step forward
			if (current_n > 0 &&
					GameManager.getInstance().isCellFree(current_l, current_n -1)) {
				Move m = new Move(current_l, current_n, current_l,current_n - 1);
				moves.add(m);
			}
			// check if this pawn can capture something (diagonals)
			if (current_n > 0 && current_l < 7 &&
					GameManager.getInstance().canCellBeCaptured(current_l + 1,current_n - 1, this.color)) {
				Move m = new Move(current_l, current_n, current_l + 1,current_n - 1);
				moves.add(m);
			}

			if (current_n > 0 && current_l > 0 &&
					GameManager.getInstance().canCellBeCaptured(current_l - 1,current_n - 1, this.color)) {
				Move m = new Move(current_l, current_n, current_l - 1,current_n - 1);
				moves.add(m);
			}
		}

		return moves;
	}

}
