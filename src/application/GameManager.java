package application;

import java.util.ArrayList;
import java.util.List;

import chess.components.Cell;
import chess.components.IObserver;
import chess.components.Move;
import chess.components.PlayerManager;
import chess.components.figures.Bishop;
import chess.components.figures.Figure;
import chess.components.figures.King;
import chess.components.figures.Knight;
import chess.components.figures.Pawn;
import chess.components.figures.Queen;
import chess.components.figures.Rook;
import engineadapter.EngineAdapter;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class GameManager {
	
	private static GameManager instance = null;
	
	private GameManager(){
		// load C++ library for JNI
		System.loadLibrary("ChessLib");
	}
	
	public static GameManager getInstance(){
		if(instance == null)
			instance = new GameManager();
		
		return instance;
	}
	
	private GridPane board;
	private TextArea log;
	
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
	public void setLog(TextArea t){
		this.log = t;
	}
	
	public boolean isCellFree(int l, int n){
		Cell c = getCell(l, n);
		return !c.hasFigure();
	}
	public boolean canCellBeCaptured(int l, int n, String color){
		Cell c = getCell(l, n);

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

	public void newGame(IObserver observer){
		
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

		PlayerManager.getInstance().setTimeLabel(((MainController)observer).getTimerLabel());

		//attach observer
		for(Node n : board.getChildren()){
			c = (Cell)n;
			
			c.clearObservers();
			
			c.attachObserver(observer);
			c.attachObserver(SoundManager.getInstance());
			c.attachObserver(PlayerManager.getInstance());
		}
	}


	public void endGame(){
		selectedFigure = null;
		allFigures.clear();
		clearBoard();
		PlayerManager.getInstance().resetGame();
	}
	
	public void clearBoard(){
		for(Node n : board.getChildren()){
			Cell c = (Cell)n;
			c.getChildren().clear();
		}
			
	}
	
	/*********************************************************************************
	* Engine communication section
	*/
	private boolean gameWithComputer = false;
	public void setGameWithComputer(){
		this.gameWithComputer = true;
	}
	public void unsetGameWithComputer(){
		this.gameWithComputer = false;
	}
	public boolean isGameWithComputer(){
		return this.gameWithComputer;
	}
	private EngineAdapter ea = null;
	
	public boolean engineNewGame(){
		boolean result = true;
		
		//TODO: stop prev game
		
		//TODO: check if engine is started
		
		ea = new EngineAdapter();
		String s;
		
		// TODO: pass string from GUI. Allow user to select location of an engine
		s = ea.start("stockfish");
		System.out.println(s);
		
		ea.write("uci\n");
		System.out.println(ea.read());
		
		ea.write("isready\n");
		System.out.println(ea.read());
		
		ea.write("ucinewgame\n");
		
		return result;
	}
	
	public Move engineNextMove(String allMoves){
		String s;
		String move;
		
		if(!allMoves.isEmpty()){
			ea.write("position startpos moves " + allMoves + "\n");
		}
		
		ea.write("go\n");
		s = "";
		while(!s.contains("bestmove")){
			s = ea.read();
			System.out.println(s);
		}
		
		// parse next move
		int start = s.indexOf("bestmove")+9;
		int end = s.indexOf(" ", start);
		move = s.substring(start, end);
		
		System.out.println(move);
		
		Move m = new Move();
		m.readAlgebraicNotation(move);
		return m;
	}
	
	public void engineMove(Move m){
		Cell c1 = (Cell)Utils.getNodeFromGridPane(board, m.getStart_l(), m.getStart_n());
		Cell c2 = (Cell)Utils.getNodeFromGridPane(board, m.getEnd_l(), m.getEnd_n());
		
		Figure f = c1.getFigure();
		f.select();
		c2.moveFigureToHere();
	}
	
	public String getAllMoves(){
		String s = log.getText();
		s = s.replace("\n", " ");
		s = s.replace(":", "");
		s = s.replace("white", "");
		s = s.replace("black", "");
		s = s.replace("Check!!!", "");
		System.out.println("All moves: " + s);
		return s;
	}
	
		
	/*********************************************************************************/

    //-------------------------------------------------------------
	//if left Rook change position then castling is impossible
	public boolean isLeftRookStand(int king_l, int king_n){
		Rook leftRook = getLeftRook(king_l, king_n);
		if(leftRook != null && leftRook.isStartPos())
			return true;
		else
			return false;
	}
	//if right Rook change position then castling is impossible
	public boolean isRightRookStand(int king_l, int king_n){
		Rook rightRook = getRightRook(king_l, king_n);
        if(rightRook != null && rightRook.isStartPos())
            return true;
        else
            return false;
	}

    public Rook getLeftRook(int king_l, int king_n){
        Cell cell = getCell(king_l - 3, king_n);
        if(cell.hasFigure() && cell.getFigure() instanceof Rook)
            return (Rook)cell.getFigure();
        else
            return null;
    }

	public Rook getRightRook(int king_l, int king_n){
		Cell cell = getCell(king_l + 4, king_n);
		if(cell.hasFigure() && cell.getFigure() instanceof Rook)
			return (Rook)cell.getFigure();
		else
			return null;
	}

    public Cell getCell(int l, int n){
        Cell cell = null;
        for (Node node : board.getChildren()) {
            if (GridPane.getColumnIndex(node) == l && GridPane.getRowIndex(node) == n) {
                cell = (Cell)node;
                break;
            }
        }
        return cell;
    }

    //---------------------------------------------------------
	
	public King getKing(String color){
		for(Figure f : this.allFigures){
			if(f instanceof King && f.getColor().equals(color))
				return (King)f;
		}
		return null;
	}

	public boolean isKingUnderAttack(String color){
        King king = getKing(color);
        Cell kingParent = (Cell)king.getParent();
        int king_l = GridPane.getColumnIndex(kingParent);
        int king_n = GridPane.getRowIndex(kingParent);
		boolean isKingUnderAttack = false;

        Cell enemyParent = null;
        int enemyCell_n = 0;
        int enemyCell_l = 0;
        List<Move> possibleEnemyMoves = null;

        for(Figure f : this.getAllFigures()){
            if(!f.getColor().equals(color)){
                enemyParent = (Cell)f.getParent();
                enemyCell_l = GridPane.getColumnIndex(enemyParent);
                enemyCell_n = GridPane.getRowIndex(enemyParent);
                possibleEnemyMoves = f.nextMoves(enemyCell_l, enemyCell_n);

                for(Move move: possibleEnemyMoves){
                    if(move.getEnd_l() == king_l && move.getEnd_n() == king_n){
						isKingUnderAttack = true;
                        break;
                    }
                }

            }
        }
		return isKingUnderAttack;
	}

    public boolean isKingUnderAttack(String color, Cell previousCell,Cell newCell, Figure f){
        if(GridPane.getColumnIndex(previousCell) == GridPane.getColumnIndex(newCell)
                && GridPane.getRowIndex(previousCell) == GridPane.getRowIndex(newCell)){
            return isKingUnderAttack(color);
        }else {
            Figure previousCellFigure = null;

            //make invisible move
            if (newCell.hasFigure()) {
                previousCellFigure = newCell.getFigure();
                newCell.getChildren().clear();
                allFigures.remove(previousCellFigure);
            }
            previousCell.getChildren().clear();
            newCell.getChildren().add(f);

            //check is our king under attack after invisible move

			boolean isKingUnderAttack = isKingUnderAttack(color);

            //roll back previous move
            previousCell.getChildren().clear();
            previousCell.getChildren().add(f);
            newCell.getChildren().clear();

            if (previousCellFigure != null) {
                newCell.getChildren().add(previousCellFigure);
                allFigures.add(previousCellFigure);
            }
            System.out.println("Is under Attack: " + isKingUnderAttack);
			return isKingUnderAttack;
        }
    }

    public boolean isCheckMate(String color){
		King king = getKing(color);

		Cell figureParent;
		int l;
		int n;
		List<Move> moves;
		List<Figure> allFiguresCopy = new ArrayList<>(allFigures);

		if(isKingUnderAttack(color)){
			if(king.hasSafeMove()){
				//if king have safe move then game go on
				return false;
			}else{
				for(Figure f : allFiguresCopy){
					if(!(f instanceof King) && f.getColor().equals(color)){
						figureParent = (Cell)f.getParent();
						l = GridPane.getColumnIndex(figureParent);
						n = GridPane.getRowIndex(figureParent);
						moves = f.nextMoves(l, n);
						for(Move move : moves){
							//if figure can protect king then game go on
							if(!isKingUnderAttack(color, getCell(l,n),
									getCell(move.getEnd_l(), move.getEnd_n()), f))
								return false;
						}
					}
				}
				return true;
			}
		}
        return false;
    }


}
