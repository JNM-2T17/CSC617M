import java.util.ArrayList;
import java.util.Iterator;

public class Prog extends NonTerminal {
	private Play play;

	public Prog(String pattern) {
		super("PROG",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Elems e = (Elems)getComponent("ELEMS");
            if( e != null ) {
            	e.interpret();
            }
            e = (Elems)getComponent("ELEMSR");
            if( e != null ) {
            	e.interpret();
            }
			play = (Play)getComponent("PLAY");
            play.interpret();
		}
	}

	private Play getPlay() {
		return play;
	}

	public void execute() {
		play.execute();
	}
}

