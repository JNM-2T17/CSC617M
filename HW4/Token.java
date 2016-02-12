public class Token implements ParseObject {
	private String token;
	private String type;
	private int lineNo;
	private int value;
	private double time;

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

	public int intValue() {
		return value;
	}

	public double time() {
		return time;
	}

	public void interpret() throws Exception {
		switch(type) {
			case "pitch":
				switch(token.charAt(0)) {
					case 'C':
						value = 0;
						break;
					case 'D':
						value = 2;
						break;
					case 'E':
						value = 4;
						break;
					case 'F':
						value = 5;
						break;
					case 'G':
						value = 7;
						break;
					case 'A':
						value = 9;
						break;
					case 'B':
						value = 11;
						break;
					default:
						value = 0;
				}
				for(int i = 1; i < token.length(); i++ ) {
					switch(token.charAt(i)) {
						case '#':
							value++;
							break;
						case 'b':
							value--;
							break;
						default:
					}
				}
				break;
			case "num":
				value = Integer.parseInt(token);
				break;
			case "time":
				String[] parts = token.split("/");
				time = Double.parseDouble(parts[0]) * 1.0 
						/ Double.parseDouble(parts[1]);
				break;
			default:
		}
	}

	public String toString() {
		return (token.equals("\n") ? "\\n" : token) + " - " + type + " at line " 
				+ lineNo;
	}
}