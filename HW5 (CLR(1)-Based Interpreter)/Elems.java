import java.util.ArrayList;
import java.util.Iterator;

public class Elems extends NonTerminal {
	private ArrayList<Elem> elems;

	public Elems(String pattern) {
		super("ELEMS",pattern);
		elems = new ArrayList<Elem>();
	}

	public Iterator<Elem> getElems() {
		return elems.iterator();
	}

	public void execute() {}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			switch(getProdString()) {
				case "ELEM newline ELEMS":
					Elem e = (Elem)getComponent("ELEM");
					e.interpret();
					elems.add(e);
					Elems es = (Elems)getComponent("ELEMS");
					es.interpret();
					Iterator<Elem> elemList = es.getElems();
					while(elemList.hasNext()) {
						elems.add(elemList.next());
					}
					break;
				default:
					e = (Elem)getComponent("ELEM");
					e.interpret();
					elems.add(e);
			}
		}
	}
}