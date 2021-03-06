public class Time extends NonTerminal {
	private double timeVal;
	
	public Time(String pattern){
		super("TIME", pattern);
	}
	
	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			switch(getProdString()){
				case "num SUBTIME":
					Token t = (Token) getComponent("num");
					t.interpret();
					timeVal = Double.parseDouble("" + t.intValue());
					break;
				case "time SUBTIME":
					t = (Token) getComponent("time");
					t.interpret();
					timeVal = Double.parseDouble("" + t.time());
					break;
				default:
			}
			SubTime subTime = (SubTime)getComponent("SUBTIME");
			subTime.interpret();
			int nDots = subTime.getDotCount();
			double origTime = timeVal;
			for(int i=0; i<nDots; i++,origTime /= 2){
				timeVal += (origTime/2);
			}
		}
	}
	
	public double getTime(){
		return timeVal;
	}
}