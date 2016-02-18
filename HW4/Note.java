public class Note extends NonTerminal implements Playable {
	private int note;
	private double duration;

	public Note(String pattern) {
		super("NOTE",pattern);
	}
	
	private Note(int note, double duration){
		this.note = note;
		this.duration = duration;
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Token pitchToken = (Token)getComponent("pitch");
			pitchToken.interpret();
			note = pitchToken.intValue();

			SubNote subnote = (SubNote)getComponent("SUBNOTE");
			subnote.interpret();

			note += 12 * (subnote.getOctave() + 1);
			duration = subnote.getTime();
		}
	}

	public void play() {
		MusicPlayer.instance().play(note, duration);
	}

	public void changePitch(int semitones) {
		note += semitones;
	}

	public void changeTime(double factor) {
		duration *= factor;
	}

	public Playable multiply(int times) {
		// Returns a seq
		return null; //temporary
	}
	
	public Playable clone(){
		
	}
}