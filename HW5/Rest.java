import java.util.ArrayList;
import java.util.List;

public class Rest extends NonTerminal implements Playable {
	private double duration;
	private List<NoteAction> stream;

	public Rest(String pattern) {
		super("REST",pattern);
	}

	public Rest(double duration) {
		super("REST","rest ( TIME )");
		this.duration = duration;
		buildStream();
	}

	private void buildStream() {
		ArrayList<NoteAction> nas = new ArrayList<NoteAction>();
		nas.add(new NoteAction(NoteAction.SLEEP,duration));
		stream = nas;
	}
	
	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Time tm = (Time)getComponent("TIME");
			tm.interpret();
			duration = tm.getTime();
			buildStream();
		}
	}

	public double getDuration(){
		return duration;
	}

	public String getType() {
		return "REST";
	}

	public void execute() {
		play();
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

	public List<NoteAction> getStream() {
		return stream;
	}
}