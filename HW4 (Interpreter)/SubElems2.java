import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class SubElems2 extends NonTerminal {
	private Iterator<Elem> itr;

	public SubElems2(String pattern) {
		super("SUBELEMS2",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} else {
			switch(getProdString()) {
				case "newline ELEMS":
					try {
						Elems e = (Elems)getComponent("ELEMS");
						e.interpret();
						itr = e.getElems();
					} catch(Exception e) {
						e.printStackTrace();
					}
					break;
				default:
					itr = (new ArrayList<Elem>()).iterator();
			}
		}
	}

	public Iterator<Elem> getElems() {
		return itr;
	}

	public List<Elem> build(List<Elem> elems) {
		switch(getProdString()) {
			case "newline ELEMS":
				try {
					Elems e = (Elems)getComponent("ELEMS");
					e.interpret();
					Iterator<Elem> itr = e.getElems();
					while(itr.hasNext()) {
						elems.add(itr.next());
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;	
			default:
		}
		return elems;
	}
}