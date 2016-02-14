public interface Playable extends ParseObject {
	public void play();
	public void changePitch(int semitones);
	public void changeTime(double factor);
	public Playable multiply(int times);
}