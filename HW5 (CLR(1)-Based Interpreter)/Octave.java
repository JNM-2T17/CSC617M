public class Octave extends NonTerminal {
	private int octave;

	public Octave(String pattern) {
		super("OCTAVE",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Token minus = (Token)getComponent("-");

			if(minus != null){
				Token t = (Token)getComponent("num");
				t.interpret();
				octave = -1 * t.intValue();
			}

			else{
				Token t = (Token)getComponent("octave");
				t.interpret();
				octave = t.intValue();
			}
		}
	}

	public void execute() {}

	public int getOctave(){
		return octave;
	}
}