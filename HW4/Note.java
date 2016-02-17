public class Note extends NonTerminal implements Playable {
	private int note;
	private float duration;

	public Note(String pattern) {
		super("NOTE",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Token pitch = (Token)getComponent("pitch");
			pitch.interpret();
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
	}
}