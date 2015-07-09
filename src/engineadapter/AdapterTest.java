package engineadapter;

public class AdapterTest {
	
	public static void main(String[] args) {
		System.loadLibrary("ChessLib");
		
		System.out.println("****************************************************");
		
		EngineAdapter ea = new EngineAdapter();
		
		String s;
		
		s = ea.start("../../stockfish/Linux/stockfish_6_x64");
		System.out.println(s);
		
		ea.write("uci\n");
		System.out.println(ea.read());
		
		ea.write("isready\n");
		System.out.println(ea.read());
		
		ea.write("ucinewgame\n");
		
		ea.write("position startpos moves e2e4\n");

		ea.write("go\n");
		
		s = "";
		while(!s.contains("bestmove")){
			s = ea.read();
			System.out.println(s);
		}
		
	}
}
