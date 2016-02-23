import java.util.List;

public interface Playable extends ParseObject {
	public void play();
	public Playable changePitch(int semitones);
	public Playable changeTime(double factor);
	public Playable multiply(int times);
	public List<NoteAction> getStream();
	public String getType();
}