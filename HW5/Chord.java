import java.util.ArrayList;
import java.util.List;

public class Chord extends NonTerminal implements Playable {
	private int note;
	private double duration;
	private Playable chord;
	private String type;
	public static final String MAJOR = "maj";
	public static final String MINOR = "m";
	public static final String AUG = "aug";
	public static final String DIM = "dim";
	public static final String DIM7 = "dim7";
	public static final String MIN7 = "min7";
	public static final String MMAJ7 = "mmaj7";
	public static final String DOM7 = "dom7";
	public static final String MAJ7 = "maj7";
	public static final String AUG7 = "aug7";
	public static final String DOM9 = "dom9";
	public static final String DOM11 = "dom11";
	public static final String DOM13 = "dom13";
	public static final String ADD9 = "add9";
	public static final String ADD11 = "add11";
	public static final String SUS2 = "sus2";
	public static final String SUS4 = "sus4";
	public static final String OCTUP = "octup";
	
	public Chord(String pattern) {
		super("CHORD",pattern);
	}
	
	public Chord(int note, double duration,String type) {
		super("CHORD","pitch ( SUBNOTE");
		this.note = note;
		this.duration = duration;
		buildChord();
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Token chordToken = (Token)getComponent("chord");
			type = chordToken.token();

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
			buildChord();
		}
	}

	public String getType() {
		return "CHORD";
	}

	private void buildChord() {
		ArrayList<Playable> notes = new ArrayList<Playable>();
		Note n = new Note(note,duration);
		notes.add(n);
		switch(type) {
			case MAJOR:
				notes.add(n.changePitch(4));
				notes.add(n.changePitch(7));
				break;
			case MINOR:
				notes.add(n.changePitch(3));
				notes.add(n.changePitch(7));
				break;
			case AUG:
				notes.add(n.changePitch(4));
				notes.add(n.changePitch(8));
				break;
			case DIM:
				notes.add(n.changePitch(3));
				notes.add(n.changePitch(6));
				break;
			case DIM7:
				notes.add(n.changePitch(3));
				notes.add(n.changePitch(6));
				notes.add(n.changePitch(9));
				break;
			case MIN7:
				notes.add(n.changePitch(3));
				notes.add(n.changePitch(7));
				notes.add(n.changePitch(10));
				break;
			case MMAJ7:
				notes.add(n.changePitch(3));
				notes.add(n.changePitch(7));
				notes.add(n.changePitch(11));
				break;
			case DOM7:
				notes.add(n.changePitch(4));
				notes.add(n.changePitch(7));
				notes.add(n.changePitch(10));
				break;
			case MAJ7:
				notes.add(n.changePitch(4));
				notes.add(n.changePitch(7));
				notes.add(n.changePitch(11));
				break;
			case AUG7:
				notes.add(n.changePitch(4));
				notes.add(n.changePitch(8));
				notes.add(n.changePitch(10));
				break;
			case DOM13:
				notes.add(n.changePitch(21));
				//fall-through
			case DOM11:
				notes.add(n.changePitch(17));
				//fall-through
			case DOM9:
				notes.add(n.changePitch(4));
				notes.add(n.changePitch(7));
				notes.add(n.changePitch(10));
				notes.add(n.changePitch(14));
				break;
			case ADD9:
				notes.add(n.changePitch(4));
				notes.add(n.changePitch(7));
				notes.add(n.changePitch(14));
				break;
			case ADD11:
				notes.add(n.changePitch(4));
				notes.add(n.changePitch(7));
				notes.add(n.changePitch(17));
				break;
			case SUS2:
				notes.add(n.changePitch(2));
				notes.add(n.changePitch(7));
				break;
			case SUS4:
				notes.add(n.changePitch(5));
				notes.add(n.changePitch(7));
				break;
			case OCTUP:
				notes.add(n.changePitch(12));
				break;
			default:
		}
		chord = new Sync(notes.toArray(new Playable[1]));
	}

	public void execute() {
		play();
	}

	public void play() {
		chord.play();
	}

	public Playable changePitch(int semitones) {
		return chord.changePitch(semitones);
	}

	public Playable changeTime(double factor) {
		return chord.changeTime(factor);
	}

	public Playable multiply(int times) {
		return chord.multiply(times);
	}

	public List<NoteAction> getStream() {
		return chord.getStream();
	}
}