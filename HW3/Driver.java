import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class Driver {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
								new FileReader(
									new File(args[0])));
		String code = "";
		String s;
		do {
			s = br.readLine();
			if( s != null ) {
				code += s + "\n";
			}
		} while(s != null);

		br.close();

		if( code.endsWith("\n") ) {
			code = code.substring(0,code.length() - 1);
		}
		Tokenizer t = new Tokenizer(code);
		t.tokenize();
		List<Token> tokens = t.getTokens();

		PrintWriter pw = new PrintWriter(
							new BufferedWriter(
								new FileWriter(
									new File(args[1]))));

		for(Token token: tokens) {
			pw.println(token);
		}

		pw.close();

		Parser p = new Parser(tokens);
		System.out.println(p.parse() ? "Valid" : "Invalid");
	}
}