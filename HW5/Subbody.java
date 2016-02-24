import java.util.ArrayList;
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
			if( elems != null ) {
				elems.interpret();
				this.elems = elems.getElems();
			} else {
				this.elems = (new ArrayList<Elem>()).iterator();
			}
		}
	}

	public void execute() {}

	public Iterator<Elem> getElems() {
		return elems;
	}
}