import java.util.ArrayList;
import java.io.*;

public class Interpreter {
	private Tokenizer tokenizer;
	private Parser parser;
	private TableGenerator tableGen;
	private NonTerminalFactory ntf;
	private State[] parseTable;
	private String code;
	private boolean generate;

	public Interpreter(Tokenizer tokenizer,NonTerminalFactory ntf, String code
						,String cfgFile,boolean generate) throws Exception {
		this.tokenizer = tokenizer;
		this.code = code;
		this.ntf = ntf;
		this.generate = generate;
		try {
			// long time = System.currentTimeMillis();
			computeParseTable(cfgFile);
			// System.out.println((System.currentTimeMillis() - time) / 1000.0 + " secs" );
			
			parser = new Parser(ntf,parseTable);
			// parser = new Parser(ntf,null);
			if( parseTable == null ) {
				throw new Exception("Cannot interpret due to conflicts in " 
									+ "grammar");
			}
		} catch(Exception e) {
			throw e;
		}
	}

	private void computeParseTable(String cfgFile) throws Exception {
		tableGen = new TableGenerator(cfgFile);
		if( generate ) {
			parseTable = tableGen.generateParseTable();
		} else {
			ArrayList<State> states = new ArrayList<State>();
			ArrayList<String> terminals = new ArrayList<String>();
			ArrayList<String> variables = new ArrayList<String>();
			BufferedReader br = new BufferedReader(
									new FileReader(
										new File("CFG.csv")));
			String[] symbols = br.readLine().split(",");
			boolean term = true;
			for(int i = 1; i < symbols.length; i++) {
				if( term ) {
					terminals.add(symbols[i].replace("&com;",","));
					if(symbols[i].equals("EOF")) {
						term = false;
					} 
				} else {
					variables.add(symbols[i]);
				}
			}
			String curr;
			do {
				curr = br.readLine();
				if( curr != null && curr.length() > 0 ) {
					State currState = new State(tableGen);
					String[] stateActs = curr.split(",");
					int i;
					for(i = 0; i < terminals.size(); i++) {
						if( i + 1 >= stateActs.length) {
							break;
						}
						String currAct = stateActs[i + 1];
						// System.out.print(currAct.length() == 0 ? "" : (i + 1) + currAct + "\n");
						if( currAct.length() > 0) {
							if( currAct.startsWith("SHIFT")) {
								currState.putAction(terminals.get(i),Integer
											.parseInt(currAct.substring(6)));
							} else if( currAct.equals("ACCEPT") ) {
								currState.accept();
							} else {
								currState.putAction(terminals.get(i),
													tableGen
													.getProduction(Integer
														.parseInt(currAct
															.substring(7))));
							}
						}
					}
					// System.out.println((i + 1) + "");
					for(int j = 0; j < variables.size(); j++) {
						if( i + j + 1 >= stateActs.length ) {
							break;
						}
						String currGoto = stateActs[i + j + 1];
						if( currGoto.length() > 0 ) {
							currState.putAction(variables.get(j)
											,Integer.parseInt(currGoto));
						}
					}
					currState.validate();
					states.add(currState);
				}
			} while(curr != null && curr.length() > 0 );
			br.close();
			parseTable = states.toArray(new State[1]);
			for( State state: parseTable) {
				state.updateShifts(parseTable);
			}
		}
	}

	public void interpret() throws Exception {
		tokenizer.setCode(code);
		tokenizer.tokenize();
		// for(Token t : tokenizer.getTokens() ) {
		// 	System.out.println(t);
		// }
		parser.setTokens(tokenizer.getTokens());
		NonTerminal start = parser.parse();
		if( start != null ) {
			start.execute();
		} else {
			System.out.println("Program terminated without execution.");
		}
	}
}