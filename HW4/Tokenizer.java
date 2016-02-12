import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tokenizer {
	private ArrayList<Token> tokens;
	private String code;
	private int state;
	private String currToken;
	private int lineNo;
	private static String[] reserved;
	private static String[] operators;

	public Tokenizer(String code) {
		this.code = code;
		tokens = new ArrayList<Token>();
		state = 0;
		currToken = "";
		reserved = new String[] {
			"play", "rest", "seq", "sync"
		};
		operators = new String[] {
			"->", "+", "++", "-", "--", ">", ">>", "<", "<<", "~", "*", 
			"(", ")", "{", "}", ",","[","]","."
		};
	}

	public void tokenize() {
		currToken = "";
		lineNo = 1;
		for(int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);
			// System.out.println(state + " " + currToken + " " 
			// 					+ (c == '\n' ? "\\n" 
			// 						: (c == ' ' ? "space" : c)));
			switch(state) {
				//new token
				case 0:
					if(c == '/') {
						state = 1;
						currToken += c;
					} else if(c == '\t' || c == ' ') {
						continue;
					} else if(c >= 'A' && c <= 'Z'  || c >= 'a' && c <= 'z') {
						state = 4;
						currToken += c;
					} else if( c == '\n' ) {
						currToken += '\n';
						addToken();
						lineNo++;
					} else if (c >= '0' && c <= '9' ) {
						state = 5;
						currToken += c;
					} else {
						//check if start of operator
						for(String s: operators) {
							if( c == s.charAt(0)) {
								state = 3;
								currToken += c;
								break;
							}
						}
					}
					break;
				//slash read
				case 1:
					if( c == '/') {
						state = 2;
						currToken = "";
					} else {
						i--;
						state = 7;
					}
					break;
				//in comment
				case 2:
					if( c == '\n') {
						state = 0;
						currToken = "\n";
						addToken();
						lineNo++;
					}
					break;
				//operator check
				case 3:
					if(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' ) {
						state = 4;
						addToken();
						currToken += c;
					} else if(c >= '0' && c <= '9' ) {						
						state = 5;
						addToken();
						currToken += c;
					} else if( c == ' ' || c == '\t' ) {
						state = 0;
						addToken();
					} else if( c == '\n') {
						state = 0;
						addToken();
						currToken += c;
						addToken();
						lineNo++;
					} else {
						boolean foundStart = false;
						//check if start of operator
						for(String s: operators) {
							if( s.startsWith(currToken + c)) {
								currToken += c;
								foundStart = true;
								break;
							}
						}

						if( !foundStart) {
							addToken();
							currToken += c;
						}
					}
					break;
				//word check
				case 4:
					if( c == ' ' || c == '\t' ) {
						state = 0;
						addToken();
					} else if( c == '\n') {
						state = 0;
						addToken();
						currToken += c;
						addToken();
						lineNo++;
					} else if( c == '/') {
						state = 1;
						addToken();
						currToken += c;
					} else {
						//check if start of operator
						for(String s: operators) {
							if( c == s.charAt(0)) {
								state = 3;
								addToken();
								currToken += c;
								break;
							}
						}
						if( state != 3 ) {
							currToken += c;
						}
					}
					break;
				//num check
				case 5: 
					//if non-numerical
					if(c == '/') {
						state = 6;
						currToken += c;
						break;
					} 
					//fall-through
				//check denominator
				case 6:
					if(c >= 'A' && c <= 'Z'  || c >= 'a' 
									&& c <= 'z') {
						state = 7;
						currToken += c;
					} else if( c < '0' || c > '9' ) {
						addToken();
						if( c == ' ' || c == '\t') {
							state = 0;
						} else if( c == '\n') {
							state = 0;
							currToken += c;
							addToken();
							lineNo++;
						} else if( c == '/') {
							addToken();
							state = 1;
							currToken += c;
						} else {
							//check if start of operator
							for(String s: operators) {
								if( c == s.charAt(0)) {
									state = 3;
									currToken += c;
									break;
								}
							}
						}
					} else {
						currToken += c;
					}
					break;
				//gibberish checker
				case 7:
					if( c == ' ' || c == '\t') {
						addToken();
						state = 0;
					} else if( c == '\n') {
						addToken();
						state = 0;
						currToken += c;
						addToken();
						lineNo++;
					} else if( c == '/') {
						addToken();
						state = 1;
						currToken += c;
					} else {
						currToken += c;
					}
					break;
				default:
			}
		}
		addToken();
	}

	public void addToken() {
		if( currToken.length() > 0 ) {
			Token t = new Token(currToken,tokenType(),lineNo);
			if( tokens.size() == 0 || !t.type().equals("newline")
					|| !tokens.get(tokens.size() - 1).type().equals("newline")) {
				tokens.add(t);
			}
			// System.out.println("Added " + t);
		}
		currToken = "";
	}

	public String tokenType() {
		if( currToken.equals("\n") ) {
			return "newline";
		}

		for(String s: reserved) {
			if( s.equals(currToken)) {
				return s;
			}
		}

		for(String s: operators) {
			if(s.equals(currToken)) {
				return s;
			}
		}

		try {
			int i = Integer.parseInt(currToken);
			if( i > 0 ) {
				return "num";
			} else {
				return "Invalid number, must be positiive";
			}
		} catch(NumberFormatException nfe) {
			if( currToken.matches("^[1-9][0-9]*/[1-9][0-9]*+$")) {
				return "time";
			} else if(currToken.matches("^[A-G](#|##|b|bb)?$")) {
				return "pitch";
			} else if( currToken.matches("^([a-zH-Z][A-Za-z0-9_]*|" 
											+ "[A-G][A-Za-z0-9_]+)$") ) {
				return "varname";
			} else {
				return "other";
			}
		}
	}

	public List<Token> getTokens() {
		return tokens;
	}
}