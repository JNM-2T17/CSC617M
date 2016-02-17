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
			try {
				Elems e = (Elems)getComponent("ELEMS");
				e.interpret();
				itr = e.getElems();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Iterator<Elem> getElems() {
		return itr;
	}

	public void build(List<Elem> elems) {
		switch(getProdString()) {
			case "nl ELEMS":
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
	}
}