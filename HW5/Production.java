import java.util.ArrayList;

public class Production {
	private static int PROD_ID = -1;
	private int id;
	private String variable;
	private String production;
	private String[] parts;

	public Production(String var,String production) {
		this.id = PROD_ID;
		PROD_ID++;
		variable = var;
		this.production = production.trim();
		parts = production.split(" ");
		ArrayList<String> temp = new ArrayList<String>();
		for(String s : parts) {
			if( s != null && s.length() > 0 ) {
				temp.add(s);
			}
		}
		parts = temp.toArray(new String[0]);
	}

	public String variable() {
		return variable;
	}

	public String production() {
		return production;
	}

	public int id() {
		return id;
	}

	public String[] prodParts() {
		return parts;
	}

	public String part(int index) {
		return parts[index];
	}

	public String remains(int index) {
		String ret = "";
		for(int i = index; i < size(); i++) {
			if( i > index ) {
				ret += " ";
			}
			ret += part(i);
		}
		return ret;
	}

	public int size() {
		return parts.length;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public String toString() {
		return "Prod#" + id + ": " + variable + " -> " 
				+ (production.length() == 0  ? "e" : production);
	}
}