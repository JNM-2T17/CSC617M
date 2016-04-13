public interface ParseObject {
	public static final String NOT_SET_MESSAGE = "NonTerminal is not set yet.";

	/**
	 * this method must construct the intended object given the set components
	 * of the production. For tokens, it must extract the value of the saved 
	 * string.
	 * @throws Exception if not all elements are set
	 */
	public void interpret() throws Exception;
}