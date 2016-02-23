public class Time extends NonTerminal {
	private double timeVal;
	private int dots;
	
	public Time(String pattern){
		super("TIME", pattern);
		dots = 0;
	}
	
	public void setNext(ParseObject po) throws Exception {
		if( ((Token)po).type().equals(".") ) {
			dots++;
		}
		super.setNext(po);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			String prod = getProdString();
			if(prod.startsWith("num")){
				Token t = (Token) getComponent("num");
				t.interpret();
				timeVal = Double.parseDouble("" + t.intValue());
			} else {
				Token t = (Token) getComponent("time");
				t.interpret();
				timeVal = Double.parseDouble("" + t.time());
			}
			double origTime = timeVal;
			for(int i = 0; i < dots; i++,origTime /= 2){
				timeVal += (origTime / 2);
			}
		}
	}

	public void execute() {}
	
	public double getTime(){
		return timeVal;
	}
}