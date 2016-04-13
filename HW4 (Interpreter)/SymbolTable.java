import java.util.HashMap;

public class SymbolTable {
	private HashMap<String,Playable> variables;
	private static SymbolTable instance = null;

	private SymbolTable() {
		variables = new HashMap<String,Playable>();
	}

	public static SymbolTable instance() {
		if( instance == null ) {
			instance = new SymbolTable();
		}
		return instance;
	}

	public void put(String varname, Playable playable) {
		variables.put(varname,playable);
	}

	public Playable get(String varname) {
		return variables.get(varname);
	}
}