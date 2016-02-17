public class Seq extends NonTerminal {
	private Playable[] playables;
	
	public Seq(String pattern){
		super("SEQ", pattern);
	}
	
	public Seq(Playable[] p){
		playables = p;
	}
	
	public void interpret() throws Exception{
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Subbody sb = (Subbody) getComponent("SUBBODY");
			sb.interpret();	
		}
	}
	
	public Playable[] getPlayables(){
		return playables;
	}
}