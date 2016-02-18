public class Note extends NonTerminal implements Playable {
	private int note;
	private double duration;

	public Note(String pattern) {
		super("NOTE",pattern);
	}

	public Note(int note, double duration) {
		super("NOTE","pitch ( SUBNOTE");
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
			if( note > 127 ) {
				throw Exception("Pitch too high");
			}

			SubNote subnote = (SubNote)getComponent("SUBNOTE");
			subnote.interpret();

			note += 12 * (subnote.getOctave() + 1);
			duration = subnote.getTime();
		}
	}

	public void play() {
		MusicPlayer.instance().play(note, duration);
	}

	public Playble changePitch(int semitones) {
		int note = this.note + semitones;
		return new Note(note, duration);
	}

	public Playble changeTime(double factor) {
		double duration = this.duration * factor;
		return new Note(note, duration);
	}

	public Playable multiply(int times) {
		Playable[] newSeq = new Playable[times];
		for(int i = 0; i < times; i++ ) {
			newSeq[i] = this;
		}
		return new Seq(newSeq);
	}
}