public class Token {
	private String token;
	private String type;

	public Token(String token, String type) {
		this.token = token;
		this.type = type;
	}

	public String token() {
		return token;
	}

	public String type() {
		return type;
	}

	public String toString() {
		return (token.equals("\n") ? "\\n" : token) + " - " + type;
	}
}