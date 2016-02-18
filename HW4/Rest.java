public class Rest extends NonTerminal implements Playable {
	private double duration;

	public Rest(String pattern) {
		super("REST",pattern);
	}

	public Rest(double duration) {
		super("REST","rest ( TIME )");
		this.duration = duration;
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

	public double getDuration(){
		return duration;
	}

	public void play() {
		MusicPlayer.instance().rest(duration);
	}

	public void changePitch(int semitones) {
	}

	public void changeTime(double factor) {
		duration *= factor;
	}

	public Playable multiply(int times) {
		return new Rest(duration * times);
	}
}