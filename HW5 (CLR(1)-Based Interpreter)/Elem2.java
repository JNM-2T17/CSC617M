import java.util.List;

public class Elem2 extends NonTerminal implements Playable {
	private Playable play;

	public Elem2(String pattern) {
		super("ELEM2",pattern);
	}

	public void interpret() throws Exception {
		switch(getProdString()) {
			case "ELEM2 + num":
				play = (Playable)getComponent("ELEM2");
				play.interpret();
				Token t = (Token)getComponent("num");
				t.interpret();
				int num = t.intValue();
				play = play.changePitch(num);
				break;
			case "ELEM2 ++":
				play = (Playable)getComponent("ELEM2");
				play.interpret();
				play = play.changePitch(1);
				break;
			case "ELEM2 - num":
				play = (Playable)getComponent("ELEM2");
				play.interpret();
				t = (Token)getComponent("num");
				t.interpret();
				num = t.intValue();
				play = play.changePitch(-num);
				break;
			case "ELEM2 --":
				play = (Playable)getComponent("ELEM2");
				play.interpret();
				play = play.changePitch(-1);
				break;
			case "ELEM2 > num":
				play = (Playable)getComponent("ELEM2");
				play.interpret();
				t = (Token)getComponent("num");
				t.interpret();
				num = t.intValue();
				play = play.changeTime(num);
				break;
			case "ELEM2 >>":
				play = (Playable)getComponent("ELEM2");
				play.interpret();
				play = play.changeTime(2);
				break;
			case "ELEM2 < num":
				play = (Playable)getComponent("ELEM2");
				play.interpret();
				t = (Token)getComponent("num");
				t.interpret();
				num = t.intValue();
				play = play.changeTime(1.0/num);
				break;
			case "ELEM2 <<":
				play = (Playable)getComponent("ELEM2");
				play.interpret();
				t = (Token)getComponent("num");
				t.interpret();
				num = t.intValue();
				play = play.changeTime(1/2);
				break;
			case "SYNC":
				play = (Playable)getComponent("SYNC");
				play.interpret();
				break;
			case "SEQ":
				play = (Playable)getComponent("SEQ");
				play.interpret();
				break;
			case "NOTE":
				play = (Playable)getComponent("NOTE");
				play.interpret();
				break;
			case "REST":
				play = (Playable)getComponent("REST");
				play.interpret();
				break;
			case "VAR":
				play = (Playable)getComponent("VAR");
				play.interpret();
				break;
			case "CHORD":
				play = (Playable)getComponent("CHORD");
				play.interpret();
				break;
			default:
		}
	}

	public Playable[] getPlayables() {
		switch(getType()) {
			case "SYNC":
				return ((Sync)play).getPlayables();
			case "SEQ":
				return ((Seq)play).getPlayables();
			default:
				return null;
		}
	}

	public void execute() {
		play();
	}

	public void play() {
		play.play();
	}

	public Playable changePitch(int semitones) {
		return play.changePitch(semitones);
	}

	public Playable changeTime(double factor) {
		return play.changeTime(factor);
	}

	public Playable multiply(int times) {
		return play.multiply(times);
	}

	public List<NoteAction> getStream() {
		return play.getStream();
	}

	public String getType() {
		return play.getType();
	}
}