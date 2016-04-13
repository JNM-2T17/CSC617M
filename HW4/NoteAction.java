public class NoteAction {
	private int type;
	private int note;
	private int tempo;
	private int volume;
	private double duration;
	private int internalIndex;
	public static final int ON = 0;
	public static final int OFF = 1;
	public static final int SLEEP = 2;
	public static final int TEMPO = 3;
	
	public NoteAction(int type,int value) {
		this.type = type;
		switch(type) {
			case ON:
			case OFF:
				note = value;
				internalIndex = 0;
				this.volume = MusicPlayer.DEFAULT_VOLUME;
				break;
			case TEMPO:
				tempo = value;
				break;
			default:
		}
	}

	public NoteAction(int type,int note,int index) {
		this.type = type;
		this.note = note;
		internalIndex = index;
		this.volume = MusicPlayer.DEFAULT_VOLUME;
	}

	public NoteAction(int type, int note, int index, int volume) {
		this.type = type;
		this.note = note;
		internalIndex = index;
		this.volume = volume;	
	}

	public int tempo() {
		return tempo;
	}

	public int volume() {
		return volume;
	}

	public NoteAction(int type,double duration) {
		this.type = type;
		this.duration = duration;
	}

	public int type() {
		return type;
	}

	public int note() {
		return note;
	}

	public double duration() {
		return duration;
	}

	public int index() {
		return internalIndex;
	}

	public void setIndex(int index) {
		internalIndex = index;
	}

	public void reduceDuration(double reduction) {
		duration -= reduction;
	}

	public String toString() {
		String typ = "OFF";
		switch(type) {
			case ON:
				typ = "ON";
			case OFF:
				return typ + " " + note + " " + internalIndex + " " + volume;
			case TEMPO:
				return tempo + " " + tempo;
			case SLEEP:
				typ = "SLEEP";
				return typ + " " + duration;
			default:
				return "";
		}
	}
}