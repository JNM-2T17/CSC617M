public class Sub3Time extends NonTerminal {
	private int nDots = 0;
	
	public Sub3Time(String pattern){
		super("SUB2TIME", pattern);
	}
	
	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			switch(getProdString()){
				case ".":
					nDots++;
					break;
				default:
			}
		}
	}
	
	public int getDotCount(){
		return nDots;
	}
}