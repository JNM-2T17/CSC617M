import java.util.ArrayList;
import java.util.List;

public class Elem extends NonTerminal implements Playable {
	private Playable play;
	private String elemType;
	private int bpm;
	private int volume;

	public Elem(String pattern) {
		super("ELEM",pattern);
		bpm = 0;
		volume = -1;
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			if( getComponent("bpm") != null) {
				Token t = (Token)getComponent("num");
				t.interpret();
				bpm = t.intValue();
				if( bpm <= 0 ) {
					throw new Exception("Error at line " + t.lineNo() 
											+ ": Tempo must be positive.");
				}
			} else if(getComponent("volume") != null ) {
				Token t = (Token)getComponent("num");
				t.interpret();
				volume = t.intValue();
				if( volume > 127 ) {
					volume = 127;
				}
			} else {
				play = (Playable)getComponent("ELEM2");
				if( play == null ) {
					play = (Playable)getComponent("ELEM");
					play.interpret();
					if( getComponent("*") != null ) {
						Token t = (Token)getComponent("num");
						t.interpret();
						play = play.multiply(t.intValue());
					} else if( getComponent("->") != null ) {
						Token t = (Token)getComponent("varname");
						SymbolTable.instance().put(t.token(),play);
					}
				} else {
					play.interpret();
				}
			}
		}
	}

	public String getType() {
		if( volume != -1 ) {
			return "VOLUME";
		} else if(bpm != 0 ) {
			return "TEMPO";
		} else {
			return play.getType();
		}
	}
	
	public Playable[] getPlayables() {
		if( play instanceof Elem2 ) {
			return ((Elem2)play).getPlayables();
		} else if( play instanceof Sync ) {
			return ((Sync)play).getPlayables();
		} else if( play instanceof Seq) {
			return ((Seq)play).getPlayables();
		} else {
			return null;
		}
	}

	public void play() {
		if( play != null ) {
			play.play();	
		} else {
			MusicPlayer.instance().setTempo(bpm);
		}	
	}

	public void execute() {
		play();
	}

	public Playable changePitch(int semitones) {
		return play != null ? play.changePitch(semitones) : null;
	}

	public Playable changeTime(double factor) {
		return play != null ? play.changeTime(factor) : null;
	}

	public Playable multiply(int times) {
		return play != null ? play.multiply(times) : null;
	}

	public int volume() {
		return volume;
	}

	public List<NoteAction> getStream() {
		if( bpm != 0 ) {
			ArrayList<NoteAction> stream = new ArrayList<NoteAction>();
			stream.add(new NoteAction(NoteAction.TEMPO,bpm));
			return stream;
		} else if( volume != -1 ) {
			return null;
		}
        return bpm == 0 ? play.getStream() : null;
    }

    public String toString() {
    	if( bpm != 0 ) {
    		return "TEMPO " + bpm;
    	} else if( volume != -1 ) {
    		return "VOLUME " + volume;
    	} else {
    		return play.getType();
    	}
    }
}