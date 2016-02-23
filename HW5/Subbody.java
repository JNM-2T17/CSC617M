import java.util.Iterator;

public class Subbody extends NonTerminal {
	private Iterator<Elem> elems;

	public Subbody(String pattern){
		super("SUBBODY", pattern);
	}
	
	public void interpret() throws Exception{
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			Elems elems = (Elems) getComponent("ELEMS");
			elems.interpret();
			this.elems = elems.getElems();
		}
	}

	public void execute() {}

	public Iterator<Elem> getElems() {
		return elems;
	}
}