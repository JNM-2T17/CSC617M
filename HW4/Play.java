import java.util.ArrayList;
import java.util.Iterator;

public class Play extends NonTerminal {
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
}