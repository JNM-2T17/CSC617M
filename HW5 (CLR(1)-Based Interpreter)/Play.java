import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Play extends Seq {
	private Playable[] playables;

	public Play(String pattern){
		super(pattern);
		type = "PLAY";
	}

	public Playable changePitch(int semitones) {
		return null;
	}

	public Playable changeTime(double factor) {
		return null;
	}

	public Playable multiply(int times) {
		return null;
	}

	public void play() {
		MusicPlayer.instance().rest(0.125);
		super.play();
	}

	public void execute() {
		play();
	}

	public String getType() {
		return "PLAY";
	}
}