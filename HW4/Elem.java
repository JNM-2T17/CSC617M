public class Elem extends NonTerminal implements Playable {
	private Playable play;

	public Elem(String pattern) {
		super("ELEM",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			String[] possible = new String[] {
				"NOTE",
				"REST",
				"VAR",
				"SYNC",
				"SEQ"
			};
			for(String s: possible) {
				play = (Playable)getComponent(s);

				if( play != null ) {
					break;
				}
			}
			SubElem subelem = (SubElem)getComponent("SUBELEM");
			play = subelem.value(play);
		}
	}

	public void play() {
		play.play();
	}

	public void changePitch(int semitones) {
		play.changePitch(semitones);
	}

	public void changeTime(double factor) {
		play.changeTime(factor);
	}

	public Playable multiply(int times) {
		return play.multiply(times);
	}
}