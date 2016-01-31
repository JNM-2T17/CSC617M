public class Token {
	private String token;
	private String type;
	private int lineNo;

	public Token(String token, String type, int lineNo) {
		this.token = token;
		this.type = type;
		this.lineNo = lineNo;
	}

	public String token() {
		return token;
	}

	public String type() {
		return type;
	}

	public int lineNo() {
		return lineNo;
	}

	public String toString() {
		return (token.equals("\n") ? "\\n" : token) + " - " + type + " at line " 
				+ lineNo;
	}
}