import java.util.List;
import java.util.Iterator;

public class SubElems extends NonTerminal {
	
	public SubElems(String pattern) {
		super("SUBELEMS",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		} 
	}

	public List<Elem> build(List<Elem> elems) {
		switch(getProdString()) {
			case "-> varname SUBELEMS2":
				SymbolTable.instance()
					.put(((Token)getComponent("varname")).token()
							,elems.get(elems.size() - 1));
				SubElems2 se2 = (SubElems2)getComponent("SUBELEMS2");
				elems = se2.build(elems);
				break;	
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