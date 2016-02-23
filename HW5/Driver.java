import java.io.*;

public class Driver {
	public static void main(String[] args) {
		try {
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

			Interpreter interpreter 
				= new Interpreter(new ConcreteTokenizer()
									,ConcreteNonTerminalFactory.instance()
									,code,"maestro.txt");
			interpreter.interpret();
		} catch(Exception e) {
			// if( e.getMessage() != null) {
			// 	System.out.println(e.getMessage());
			// } else {
				e.printStackTrace();
			// }
		}
	}
}