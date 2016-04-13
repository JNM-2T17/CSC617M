import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface Tokenizer {
	/**
	 * this method should populate a tokens array with Token objects
	 * use currToken to store the current Token being processed
	 * use addToken to add currToken to the list. addToken clears currToken.
	 * addToken only adds tokens of length > 0
	 * do not forget to implement tokenType
	 * tokenType should be as recognized by the CFG. the type MUST BE IDENTICAL 
	 * TO THE TERMINAL DESIGNATIONS DECLARED IN THE CFG.
	 * e.g
	 * for the production 
	 * IF -> if ( CONDITION ) STATEMENT
	 * for any token "if","(", and ")", token type must be "if","(", and 
	 * ")" respectively
	 */
	public void tokenize();

	/**
	 * sets the code to tokenize
	 */
	public void setCode(String code);

	public List<Token> getTokens();
}