public class ConcreteNonTerminalFactory implements NonTerminalFactory {
	private static NonTerminalFactory instance = null;

	private ConcreteNonTerminalFactory() {}

	public static NonTerminalFactory instance() {
		if(instance == null ) {
			instance = new ConcreteNonTerminalFactory();
		}
        return instance;
	}

	public NonTerminal getNonTerminal(String type, String pattern) {
		switch(type) {
			case "PROG":
				return new Prog(pattern);
			case "ELEMSR":
				return new ElemsR(pattern);
			case "ELEM":
				return new Elem(pattern);
			case "ELEM2":
				return new Elem2(pattern);
			case "VAR":
				return new Var(pattern);
			case "NUM2":
				return new Num2(pattern);
			case "ELEMS":
				return new Elems(pattern);
			case "SEQ":
				return new Seq(pattern);
			case "CHORD":
				return new Chord(pattern);
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
			default:
				return null;
		}
	}
}