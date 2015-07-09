package engineadapter;

public class EngineAdapter {	
	native public String start(String enginePath);
	native public boolean stop();
	native public boolean write(String s);
	native public String read();

}
