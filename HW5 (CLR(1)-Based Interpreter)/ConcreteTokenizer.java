import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcreteTokenizer implements Tokenizer {
	private ArrayList<Token> tokens;
	private String code;
	private int state;
	private String currToken;
	private int lineNo;
	private static String[] reserved;
	private static String[] operators;
	private static String[] chords;

	public ConcreteTokenizer() {
		tokens = new ArrayList<Token>();
		state = 0;
		currToken = "";
		reserved = new String[] {
			"play", "rest", "seq", "sync","bpm","volume"
		};
		operators = new String[] {
			"->", "+", "++", "-", "--", ">", ">>", "<", "<<", "~", "*", 
			"(", ")", "{", "}", ",","[","]","."
		};
		chords = new String[] {
			"maj", "m", "aug", "dim", "dim7","min7","mmaj7","dom7","maj7",
			"aug7","dom9","dom11","dom13","add9","add11","sus2","sus4","octup"
		};
	}

	public void setCode(String code) {
		this.code = code;
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
					} else if(c == '*') {
						state = 8;
						currToken = "";
					} else {
						if( c == '\n') {
							lineNo++;
						}
						i--;
						state = 7;
					}
					break;
				//in multiline comment
				case 8:
					if( c == '*') {
						state = 9;
					} else if( c == '\n') {
						lineNo++;
					}
					break;
				case 9:
					if( c == '/') {
						state = 0;
					} else {
						if( c == '\n') {
							lineNo++;
						}
						state = 8;
					}
					break;
				//in single line comment
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
		int i = 0;
		while( i < tokens.size() && tokens.get(i).type().equals("newline")) {
			i++;
		}
		if( i > 0 ) {
			ArrayList<Token> temp = new ArrayList<Token>();
			while( i < tokens.size() ) {
				temp.add(tokens.get(i));
				i++;
			}
			tokens = temp;
		}
		i = tokens.size() - 1;
		while( i > 0 && tokens.size() > 0 
					&& tokens.get(i).type().equals("newline")) {
			tokens.remove(i);
			i--;
		}
		tokens.add(new Token("\n","newline",tokens.get(tokens.size() - 1)
								.lineNo()));
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

		for(String s: chords) {
			if(s.equals(currToken)) {
				return "chord";
			}
		}

		try {
			int i = Integer.parseInt(currToken);
			if( i > 0 ) {
				return "num";
			} else if( i == 0 ) {
				return "octave";
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