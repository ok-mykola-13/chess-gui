package chess.components.figures;

import java.util.ArrayList;
import java.util.List;

import application.GameManager;
import chess.components.Cell;
import javafx.scene.image.Image;
import chess.components.Move;
import javafx.scene.layout.GridPane;

public class King extends Figure{

    private boolean isUnderAttack;

	public King(String color) {
		super(color);
        isUnderAttack = false;
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

        //if it is`t at start position or is under attack then castling is impossible
		if(this.isStartPos() && !this.isUnderAttack()) {
			if (GameManager.getInstance().isCellFree(current_l - 1, current_n)
					&& GameManager.getInstance().isCellFree(current_l - 2, current_n)
					&& GameManager.getInstance().isLeftRookStand(current_l, current_n)) {
				//System.out.println("Left castling");
				Move m = new Move(current_l, current_n, current_l - 2, current_n);
				moves.add(m);
			}
			if (GameManager.getInstance().isCellFree(current_l + 1, current_n)
					&& GameManager.getInstance().isCellFree(current_l + 2, current_n)
					&& GameManager.getInstance().isCellFree(current_l + 3, current_n)
					&& GameManager.getInstance().isRightRookStand(current_l, current_n)) {
				//System.out.println("Right castling");
				Move m = new Move(current_l, current_n, current_l + 3, current_n);
				moves.add(m);
			}
		}
		return moves;
	}

    public boolean isUnderAttack() {
        return isUnderAttack;
    }

    public void setIsUnderAttack(boolean isUnderAttack) {
        this.isUnderAttack = isUnderAttack;
    }

	public boolean hasSafeMove(){
		Cell kingParent = (Cell) this.getParent();
		List<Move> moves = this.nextMoves(GridPane.getColumnIndex(kingParent),
				GridPane.getRowIndex(kingParent));

		for(Move move : moves){
			if(!GameManager.getInstance().isKingUnderAttack(color, kingParent,
					GameManager.getInstance().getCell(move.getEnd_l(), move.getEnd_n()), this)){
				return true;
			}
		}
		return false;
	}
}
