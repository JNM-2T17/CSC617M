public class NonTerminalFactory {
	private static NonTerminalFactory instance = null;

	private NonTerminalFactory() {}

	public static NonTerminalFactory instance() {
		if(instance == null ) {
			instance = new NonTerminalFactory();
		}
	}

	public NonTerminal getNonTerminal(String type, String pattern) {
		switch(type) {
			case "PROG":
				return new Prog(pattern);
				break;
			case "NL":
				return new NL(pattern);
				break;
			case "ELEM":
				return new Elem(pattern);
				break;
			case "SUBELEM":
				return new SubElem(pattern);
				break;
			case "VAR":
				return new Var(pattern);
				break;
			case "SUBVAR":
				return new SubVar(pattern);
				break;
			case "SUB2VAR":
				return new SubVar2(pattern);
				break;
			case "ELEMS":
				return new Elems(pattern);
				break;
			case "SUBELEMS":
				return new SubElems(pattern);
				break;
			case "SUBELEMS2":
				return new SubElems2(pattern);
				break;
			case "SEQ":
				return new Seq(pattern);
				break;
			case "SYNC":
				return new Sync(pattern);
				break;
			case "PLAY":
				return new Play(pattern);
				break;
			case "SUBBODY":
				return new Subbody(pattern);
				break;
			case "NOTE":
				return new Note(pattern);
				break;
			case "SUBNOTE":
				return new SubNote(pattern);
				break;
			case "OCTAVE":
				return new Octave(pattern);
				break;
			case "REST":
				return new Rest(pattern);
				break;
			case "TIME":
				return new Time(pattern);
				break;
			case "SUBTIME":
				return new SubTime(pattern);
				break;
			case "SUB2TIME":
				return new Sub2Time(pattern);
				break;
			case "SUB3TIME":
				return new Sub3Time(pattern);
				break;
			default:
				return null;
		}
	}
}