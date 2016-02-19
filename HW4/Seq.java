import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Seq extends NonTerminal implements Playable {
	private Playable[] playables;
	private List<NoteAction> stream;
	
	public Seq(String pattern){
		super("SEQ", pattern);
	}
	
	public Seq(Playable[] p){
		super("SEQ","seq SUBBODY");
		playables = p;
		buildStream();
	}
	
	private void buildStream() {
		ArrayList<NoteAction> nas = new ArrayList<NoteAction>();
		for(Playable p: playables) {
			List<NoteAction> stream = p.getStream();
			if( stream != null ) {
				for(NoteAction na: stream) {
					nas.add(na);
					// System.out.print(na + " ");
				}
			}
		}
		// System.out.println();
		stream = nas;
	}

	public String getType() {
		return "SEQ";
	}

	public void interpret() throws Exception{
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Subbody sb = (Subbody) getComponent("SUBBODY");
			sb.interpret();	
			Iterator<Elem> itr = sb.getElems();
			ArrayList<Playable> elems = new ArrayList<Playable>();
			int ctr = 0;
			while(itr.hasNext() ) {
				elems.add(itr.next());
				ctr++;
			}
			playables = new Playable[ctr];
			playables = elems.toArray(playables);
			buildStream();
		}
	}
	
	public Playable[] getPlayables(){
		return playables;
	}

	public void play() {
		for(Playable p: playables) {
			p.play();
		}
	}

	public Playable changePitch(int semitones) {
    	Playable[] newPlay = new Playable[playables.length];
        for(int i = 0; i < playables.length; i++) {
		  newPlay[i] = playables[i].changePitch(semitones);
        }
        return new Seq(newPlay);
	}

	public Playable changeTime(double factor) {
        Playable[] newPlay = new Playable[playables.length];
        for(int i = 0; i < playables.length; i++) {
		  newPlay[i] = playables[i].changeTime(factor);
        }
        return new Seq(newPlay);
	}

	public Playable multiply(int times) {
		Playable[] newSeq = new Playable[times];
		for(int i = 0; i < times; i++) {
			newSeq[i] = this;
		}
		return new Seq(newSeq);
	}

	public List<NoteAction> getStream() {
		return stream;
	}
}