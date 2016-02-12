public interface ParseObject {
	/**
	 * this method must construct the intended object given the set components
	 * of the production. For tokens, it must extract the value of the saved 
	 * string.
	 * @throws Exception if not all elements are set
	 */
	public void interpret() throws Exception;
}