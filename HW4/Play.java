
public class Play extends NonTerminal {
	
	public Play(String pattern){
		super("PLAY", pattern);
	}
	
	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Subbody sb = (Subbody) getComponent("SUBBODY");
			sb.interpret();	
		}
	}
}