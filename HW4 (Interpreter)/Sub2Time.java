public class Sub2Time extends NonTerminal {
	private int nDots = 0;
	
	public Sub2Time(String pattern){
		super("SUB2TIME", pattern);
	}
	
	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			switch(getProdString()){
				case ". SUB3TIME":
					nDots++;
					Sub3Time s3 = (Sub3Time) getComponent("SUB3TIME");
					s3.interpret();
					nDots+=s3.getDotCount();
					break;
				default:
			}
		}
	}
	
	public int getDotCount(){
		return nDots;
	}
}