public class Rest extends NonTerminal implements Playable {
	private float duration;

	public Rest(String pattern) {
		super("REST",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Time tm = (Time)getComponent("TIME");
			tm.interpret();
			duration = tm.getTime();
		}
	}

	public int getDuration(){
		return duration;
	}

	public void play() {
		// Can this be in the music player too?
	}

	public void changePitch(int semitones) {
	}

	public void changeTime(double factor) {
		duration *= factor;
	}

	public Playable multiply(int times) {
		duration *= times
	}
}