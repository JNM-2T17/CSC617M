public class NL extends NonTerminal {
	public NL(String pattern){
		super("NL", pattern);
	}
	
	public void interpret() throws Exception{
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} 
	}
}