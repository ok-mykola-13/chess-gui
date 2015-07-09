package chess.components;

public class Move {
	
	private int start_n;
	private int start_l;
	
	private int end_n;
	private int end_l;
	
	public Move(){}
	
	public Move(int start_l,int start_n) {
		this.start_n = start_n;
		this.start_l = start_l;
	}

	public Move(int start_l, int start_n, int end_l, int end_n) {
		this.start_n = start_n;
		this.start_l = start_l;
		this.end_n = end_n;
		this.end_l = end_l;
	}

	public int getStart_n() {
		return start_n;
	}

	public void setStart_n(int start_n) {
		this.start_n = start_n;
	}

	public int getStart_l() {
		return start_l;
	}

	public void setStart_l(int start_l) {
		this.start_l = start_l;
	}

	public int getEnd_n() {
		return end_n;
	}

	public void setEnd_n(int end_n) {
		this.end_n = end_n;
	}

	public int getEnd_l() {
		return end_l;
	}

	public void setEnd_l(int end_l) {
		this.end_l = end_l;
	}
	
	public String generateAlgebraicNotation(){
		/*
		 * anci table
		 * 		a - 97
		 * 		A - 65
		 */
		char[] c1 = Character.toChars(97 + (7-start_l));
		char[] c2 = Character.toChars(97 + (7-end_l));
		
		String s = c1[0] + Integer.toString(start_n+1) +
					c2[0] + Integer.toString(end_n+1);
		
		return s;
	}
	
	public void readAlgebraicNotation(String s){
		/*
		 * anci table
		 * 		a - 97
		 * 		A - 65
		 */
		
		this.start_l = 7 - ((int)s.charAt(0)-97);
		this.start_n = Integer.parseInt(s.substring(1, 2)) - 1;
		this.end_l = 7 - ((int)s.charAt(2)-97);
		this.end_n = Integer.parseInt(s.substring(3, 4)) - 1;		
	}
}
