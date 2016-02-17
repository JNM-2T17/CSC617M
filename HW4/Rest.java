public class Rest extends NonTerminal {
	private float time;

	public Rest(String pattern) {
		super("REST",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Time tm = (Time)getComponent("TIME");
			tm.interpret();
			time = tm.getTime();
		}
	}

	public int getTime(){
		return time;
	}
}