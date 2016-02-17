public class SubTime extends NonTerminal {
	private int nDots = 0;
	
	public SubTime(String pattern){
		super("SUBTIME", pattern);
	}
	
	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			switch(getProdString()){
				case ". SUB2TIME":
					nDots++;
					Sub2Time s2 = (Sub2Time) getComponent("SUB2TIME");
					s2.interpret();
					nDots+=s2.getDotCount();
					break;
				default:
			}
		}
	}
	
	public int getDotCount(){
		return nDots;
	}
}