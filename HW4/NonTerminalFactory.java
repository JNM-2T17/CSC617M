public class NonTerminalFactory {
	private static NonTerminalFactory instance = null;

	private NonTerminalFactory() {}

	public static NonTerminalFactory instance() {
		if(instance == null ) {
			instance = new NonTerminalFactory();
		}
        return instance;
	}

	public NonTerminal getNonTerminal(String type, String pattern) {
		switch(type) {
			case "PROG":
				return new Prog(pattern);
			case "NL":
				return new NL(pattern);
			case "ELEM":
				return new Elem(pattern);
			case "SUBELEM":
				return new SubElem(pattern);
			case "VAR":
				return new Var(pattern);
			case "SUBVAR":
				return new SubVar(pattern);
			case "SUB2VAR":
				return new SubVar2(pattern);
			case "ELEMS":
				return new Elems(pattern);
			case "SUBELEMS":
				return new SubElems(pattern);
			case "SUBELEMS2":
				return new SubElems2(pattern);
			case "SEQ":
				return new Seq(pattern);
			case "SYNC":
				return new Sync(pattern);
			case "PLAY":
				return new Play(pattern);
			case "SUBBODY":
				return new Subbody(pattern);
			case "NOTE":
				return new Note(pattern);
			case "SUBNOTE":
				return new SubNote(pattern);
			case "OCTAVE":
				return new Octave(pattern);
			case "REST":
				return new Rest(pattern);
			case "TIME":
				return new Time(pattern);
			case "SUBTIME":
				return new SubTime(pattern);
			case "SUB2TIME":
				return new Sub2Time(pattern);
			case "SUB3TIME":
				return new Sub3Time(pattern);
			default:
				return null;
		}
	}
}