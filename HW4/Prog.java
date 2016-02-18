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
			switch(getProdString()) {
				case "ELEMS nl PLAY SUBELEMS2 NL":
					Elems e = (Elems)getComponent("ELEM");
                    e.interpret();
					SubElems2 se2_1 = (SubElems2)getComponent("SUBELEMS2");
					se2_1.interpret();
                    Play play1 = (Play)getComponent("PLAY");
                    play1.interpret();
                    play = play1;
                    break;
                case "PLAY SUBELEMS2 NL":
					SubElems2 se2_2 = (SubElems2)getComponent("SUBELEMS2");
                    se2_2.interpret();
                    Play play2 = (Play)getComponent("PLAY");
                    play2.interpret();
                    play = play2;
                    break;
                case "nl PROG NL":
                    Prog pr = (Prog)getComponent("PROG");
                    pr.interpret();
                    play = pr.getPlay();
                    break;
				default:
			}
		}
	}

	private Play getPlay() {
		return play;
	}

	public void execute() {
		play.play();
	}
}

