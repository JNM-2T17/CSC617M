import java.util.ArrayList;
import java.util.Iterator;

public class Play extends NonTerminal implements Playable {
	private Playable[] playables;

	public Play(String pattern){
		super("PLAY", pattern);
	}
	
	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Subbody sb = (Subbody) getComponent("SUBBODY");
			sb.interpret();	
			ArrayList<Playable> elems = new ArrayList<Playable>();
			Iterator<Elem> itr = sb.getElems();
			while(itr.hasNext()) {
				elems.add(itr.next());
			}
			playables = elems.toArray(playables);
		}
	}

	public void play() {
		for(Playable p: playables) {
			p.play();
		}
	}

	public void changePitch(int semitones) {
		for(Playable p: playables) {
			p.changePitch(semitones);
		}
	}

	public void changeTime(double factor) {
		for(Playable p: playables) {
			p.changeTime(factor);
		}
	}

	public Playable multiply(int times) {
		return null;
	}

}