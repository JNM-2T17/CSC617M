public interface NonTerminalFactory {
	/**
	 *		Create cases for each nonterminal, returning a new subclass of 
	 *		NonTerminal
 	 *
	 *		i.e.
	 *		for the grammar
	 *		S' -> S
	 *		S -> 0S1
	 *		S -> 0A1
	 *		A -> 1A 
	 *		A ->  *		
	 *		this body would look like
	 *		switch(type) {
	 *			case "S'":
	 *				return new SPrime(pattern);
	 *			case "S":
	 *				return new S(pattern);
	 *			case "A":
	 *				return new A(pattern);
	 *			default:
	 *				return null;
	 *		}
	 */
	public NonTerminal getNonTerminal(String type, String pattern);
}