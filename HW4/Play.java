import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
			int ctr = 0;
			while(itr.hasNext()) {
				elems.add(itr.next());
				ctr++;
			}
			playables = new Playable[ctr];
			playables = elems.toArray(playables);
		}
	}

	public void play() {
		for(Playable p: playables) {
			p.play();
		}
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

	public List<NoteAction> getStream() {
		return null;
	}
}