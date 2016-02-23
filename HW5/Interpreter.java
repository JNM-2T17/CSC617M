public class Interpreter {
	private Tokenizer tokenizer;
	private Parser parser;
	private TableGenerator tableGen;
	private NonTerminalFactory ntf;
	private State[] parseTable;
	private String code;

	public Interpreter(Tokenizer tokenizer,NonTerminalFactory ntf, String code
						,String cfgFile) throws Exception {
		this.tokenizer = tokenizer;
		this.code = code;
		this.ntf = ntf;
		try {
			tableGen = new TableGenerator(cfgFile);
			parseTable = tableGen.generateParseTable();
			parser = new Parser(ntf,parseTable);
			if( parseTable == null ) {
				throw new Exception("Cannot interpret due to conflicts in " 
									+ "grammar");
			}
		} catch(Exception e) {
			throw e;
		}
	}

	public void interpret() throws Exception {
		tokenizer.setCode(code);
		tokenizer.tokenize();
		parser.setTokens(tokenizer.getTokens());
		NonTerminal start = parser.parse();
		if( start != null ) {
			start.execute();
		} else {
			System.out.println("Program terminated without execution.");
		}
	}
}