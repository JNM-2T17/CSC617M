public class Subbody extends NonTerminal {
	public Subbody(String pattern){
		super("SUBBODY", pattern);
	}
	
	public void interpret() throws Exception{
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Elems elems = (Elems) getComponent("ELEMS");
			elems.interpret();
		}
	}
}