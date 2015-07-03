package application;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class Utils {
	
	private Utils(){}
	
	public static Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && 
	        		GridPane.getRowIndex(node) == row) {
	            return node;
	        }
	    }
	    return null;
	}
}
