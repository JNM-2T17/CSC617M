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
			String[] possible = new String[] {
				"NOTE",
				"REST",
				"VAR",
				"SYNC",
				"SEQ",
				"CHORD",
				"bpm",
				"volume"
			};
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
				for(String s: possible) {
					play = (Playable)getComponent(s);

					if( play != null ) {
						elemType = s;
						break;
					}
				}
				try {
					play.interpret(); 
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
				SubElem subelem = (SubElem)getComponent("SUBELEM");
				play = subelem.value(play);
				// System.out.println(play.getType());
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
		switch(getType()) {
			case "SYNC":
				return ((Sync)play).getPlayables();
			case "SEQ":
				return ((Seq)play).getPlayables();
			default:
				return null;
		}
	}

	public void play() {
		if( bpm == 0 ) {
			play.play();	
		} else {
			MusicPlayer.instance().setTempo(bpm);
		}
		
	}

	public Playable changePitch(int semitones) {
		return bpm == 0 && volume == -1 ? play.changePitch(semitones) : null;
	}

	public Playable changeTime(double factor) {
		return bpm == 0 && volume == -1 ? play.changeTime(factor) : null;
	}

	public Playable multiply(int times) {
		return bpm == 0 && volume == -1 ? play.multiply(times) : null;
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
    	return play.getType();
    }
}