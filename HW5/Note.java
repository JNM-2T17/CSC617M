import java.util.ArrayList;
import java.util.List;

public class Note extends NonTerminal implements Playable {
	private int note;
	private double duration;
	private List<NoteAction> stream;

	public Note(String pattern) {
		super("NOTE",pattern);
	}
	
	public Note(int note, double duration) {
		super("NOTE","pitch ( SUBNOTE");
		this.note = note;
		this.duration = duration;
		buildStream();
	}

	private void buildStream() {
		ArrayList<NoteAction> nas = new ArrayList<NoteAction>();
		nas.add(new NoteAction(NoteAction.ON,note));
		nas.add(new NoteAction(NoteAction.SLEEP,duration));
		nas.add(new NoteAction(NoteAction.OFF,note));
		stream = nas;
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Token pitchToken = (Token)getComponent("pitch");
			pitchToken.interpret();
			note = pitchToken.intValue();
			if( note > 127 ) {
				throw new Exception("Pitch too high");
			}

			SubNote subnote = (SubNote)getComponent("SUBNOTE");
			subnote.interpret();

			note += 12 * (subnote.getOctave() + 1);
			duration = subnote.getTime();
			buildStream();
		}
	}

	public String getType() {
		return "NOTE";
	}

	public void execute() {
		play();
	}

	public void play() {
		try {
			MusicPlayer.instance().play(note, duration);
		} catch(Exception e) {
			// if( e.getMessage() == null ) {
				e.printStackTrace();
			// } else {
				// System.out.println(e.getMessage());
			// }
		}
	}

	public Playable changePitch(int semitones) {
		int note = this.note + semitones;
		return new Note(note, duration);
	}

	public Playable changeTime(double factor) {
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

	public List<NoteAction> getStream() {
		return stream;
	}
}