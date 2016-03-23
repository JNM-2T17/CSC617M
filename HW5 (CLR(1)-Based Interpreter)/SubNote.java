public class SubNote extends NonTerminal {
	private int octave;
	private double time;

	public SubNote(String pattern) {
		super("SUBNOTE",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			if(getComponent("num") != null){
				Token oct = (Token)getComponent("num");
				oct.interpret();
				octave = oct.intValue();
			}
			else{
				Octave oct = (Octave)getComponent("OCTAVE");
				oct.interpret();
				octave = oct.getOctave();
			}
			Time tm = (Time)getComponent("TIME");
			tm.interpret();
			time = tm.getTime();
		}
	}

	public void execute() {}

	public int getOctave(){
		return octave;
	}

	public double getTime(){
		return time;
	}
}