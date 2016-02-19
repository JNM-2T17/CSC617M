public class NoteAction {
	private int type;
	private int note;
	private double duration;
	private int internalIndex;
	public static final int ON = 0;
	public static final int OFF = 1;
	public static final int SLEEP = 2;
	
	public NoteAction(int type,int note) {
		this.type = type;
		this.note = note;
		internalIndex = 0;
	}

	public NoteAction(int type,int note,int index) {
		this.type = type;
		this.note = note;
		internalIndex = index;
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
				return typ + ":" + note + ":" + internalIndex;
			case SLEEP:
				typ = "SLEEP";
				return typ + ":" + duration;
			default:
				return "";
		}
	}
}