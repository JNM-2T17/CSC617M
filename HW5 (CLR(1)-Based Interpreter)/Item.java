import java.util.ArrayList;

public class Item {
	private Production production;
	private int position;
	private String[] lookahead;

	public Item(Production production,String[] lookahead) {
		this.production = production;
		position = 0;
		ArrayList<String> temp = new ArrayList<String>();
		for(String s: lookahead) {
			if( s.length() > 0 ) {
				temp.add(s);
			}
		}
		this.lookahead = temp.toArray(new String[0]);
	}

	public Item(Production production, String[] lookahead, int position) {
		this.production = production;
		this.position = position;	
		ArrayList<String> temp = new ArrayList<String>();
		for(String s: lookahead) {
			if( s.length() > 0 ) {
				temp.add(s);
			}
		}
		this.lookahead = temp.toArray(new String[0]);
	}

	public int size() {
		return production.size();
	}

	public String[] prodParts() {
		return production.prodParts();
	}

	public String production() {
		return production.production();
	}

	public Production productionObject() {
		return production;
	}

	public String prodString() {
		return production.toString();
	}

	public String variable() {
		return production.variable();
	}

	public String[] lookahead() {
		return lookahead;
	}

	public int prodId() {
		return production.id();
	}

	public boolean isReduction() {
		return position == size();
	}

	public int position() {
		return position;
	}

	public String currPart() {
		return position == size() ? null : production.part(position);
	}

	public String remains() {
		return production.remains(position + 1);
	}

	public Item moveForward() {
		return new Item(production,lookahead,position + 1);
	}

	public boolean lr0equals(Item i) {
		if(i.prodId() == prodId()) {
			return i.position() == position();
		} else {
			return false;
		}
	}

	public boolean equals(Item i) {
		if(lr0equals(i)) {
			String[] iLook = i.lookahead();
			if(iLook.length != lookahead.length ) {
				return false;
			}
			int count = 0;
			for(String s: iLook) {
				for(String s1: lookahead) {
					if( s1.equals(s)) {
						count++;
						break;
					}
				}
			}
			return ( count == iLook.length && count == lookahead.length);
		} else {
			return false;
		}
	}

	public String toString() {
		String[] prods = production.prodParts();
		String ret = production.variable() + " -> ";
		for(int i = 0; i <= prods.length; i++ ) {
			if( i == position) {
				ret += ".";
			}
			if( i < prods.length ) {
				ret += prods[i] + " ";
			}
		}
		ret = ret.trim() + ",";
		for(int i = 0; i < lookahead.length; i++) {
			if( i > 0 ) {
				ret += "/";
			}
			ret += lookahead[i].length() == 0 ? "e" : lookahead[i];
		}
		return ret;
	}
}