import java.util.HashMap;

/**
 * This class represents a non-terminal in the grammar.
 * @author Austin Fernandez
 */
public abstract class NonTerminal implements ParseObject {
	private HashMap<String,ParseObject> components;
	protected String type;
	private String[] production;
	private int setCtr;

	/**
	 * Basic constructor
	 * @param type left hand side of the production represented by this object
	 * @param prod the right hand side of this object's production
	 */
	public NonTerminal(String type,String prod) {
		components = new HashMap<String,ParseObject>();
		this.type = type;
		this.production = prod.equals("e") ? new String[0] : prod.split(" ");
		// System.out.println(type + " ->" + getProdString() + ": " 
		// 						+ this.production.length + " parts");
		setCtr = 0;
	}

	public String type() {
		return type;
	}

	public abstract void execute();

	/** 
	 * returns the component associated with the given key
	 * @param key key of component
	 * @return value mapped to key
	 */
	protected ParseObject getComponent(String key) {
		return components.get(key);
	}

	/**
	 * returns the split up production of this object
	 * @return production of this object
	 */
	protected String[] getProduction() {
		return production;
	}

	/**
	 * returns production as one string
	 * @return production as one string
	 */
	public String getProdString() {
		String s = "";

		if( production.length == 0 ) {
			s = "e";
		} else {
			for(String s1 : production) {
				s += s1 + " ";
			}
		}
		return s.trim();
	}

	/**
	 * set the given parse object as the next expected component of this object
	 * @param po object to set
	 * @throws Exception if all the components of this object are already set
	 */
	protected void setNext(ParseObject po) throws Exception {
		if( !isSet() ) {
			components.put(production[setCtr],po);
			setCtr++;
		} else {
			throw new Exception("This object is already set");
		}
	} 

	/**
	 * checks whether all the components of this object are set.
	 * @return whether this object is fully set or not
	 */
	public boolean isSet() {
		// System.out.println(setCtr + "==" + production.length);
		return setCtr == production.length;
	}
}