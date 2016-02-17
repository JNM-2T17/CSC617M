public class Seq extends NonTerminal {
	public Seq(String pattern){
		super("SEQ", pattern);
	}
	
	public void interpret() throws Exception{
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Subbody sb = (Subbody) getComponent("SUBBODY");
			sb.interpret();	
		}
	}
}