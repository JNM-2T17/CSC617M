public class Rest extends NonTerminal implements Playable {
	private double duration;

	public Rest(String pattern) {
		super("REST",pattern);
	}

	public Rest(double duration) {
		super("REST","rest ( TIME )");
		this.duration = duration;
	}
	
	private Rest(double duration){
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

	public Playable changePitch(int semitones) {
		return new Rest(this.duration);
	}

	public Playable changeTime(double factor) {
		return new Rest(this.duration*factor);
	}

	public Playable multiply(int times) {
		return new Rest(duration * times);
	}
}