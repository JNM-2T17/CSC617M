import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Parser {
	private List<Token> tokens;
	private HashMap<String,HashMap<String,String>> parseTable;
	private ArrayList<String> stack;
	private PrintWriter pw;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
		parseTable = new HashMap<String,HashMap<String,String>>();
		stack = new ArrayList<String>();
		ArrayList<String> terminals = new ArrayList<String>();


		try {
			BufferedReader br = new BufferedReader(
									new FileReader(
										new File("CFG.csv")));
			String terms = br.readLine();
			String[] termList = terms.split(",");
			for(int i = 1; i < termList.length; i++) {
				terminals.add(termList[i]);
			}

			String s = "";

			while( true ) {
				s = br.readLine();
				if( s != null ) {
					String[] prods = s.split(",");
					parseTable.put(prods[0],new HashMap<String,String>());
					HashMap<String,String> temp = parseTable.get(prods[0]);
					for(int i = 1; i < prods.length; i++) {
						if( prods[i].length() > 0 ) {
							temp.put(terminals.get(i - 1),prods[i].replaceAll("\\|",","));
							System.out.println("Adding " + prods[0] + " -> " 
												+ temp.get(terminals.get(i - 1)) 
												+ " on " + terminals.get(i - 1));
						}
					}
				} else {
					break;
				}
			}

			br.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public boolean parse() {
		try {
			pw = new PrintWriter(
								new BufferedWriter(
									new FileWriter(
										new File("logs.txt"))));

			push("PROG");
			int i = 0;
			printStack();
			while(i <= tokens.size() && stack.size() > 0) {
				String top = peek();
				Token currToken = i == tokens.size() ? new Token("EOF","EOF"
									,tokens.get(tokens.size() - 1).lineNo()) 
									: tokens.get(i);

				if( isVariable(top) ) {
					String prod = parseTable.get(top).get(currToken.type());
					
					if( prod == null ) {
						System.out.println("Error at token " + currToken);
						pw.println("Error at token " + currToken);
						i++;
					} else if( prod.equals("SYNCH") ) {
						pop();
					} else {
						if( prod.equals("newline ELEMS")) {
							String next = i + 1 < tokens.size() 
											? tokens.get(i + 1).type() 
											: "EOF";
							String[] noChange = new String[] {
								"sync", "seq", "pitch", "rest", "varname"
							};
							boolean change = true;

							for(String s: noChange) {
								if( next.equals(s)) {
									change = false;
								} 
							}
							if( change ) {
								prod = "";
							}
						}
						updateStack(prod);
					}
				} else if( currToken.type().equals(top)) {
					i++;
					pop();
				} else {
					pop();
					System.out.println("Error at token " + currToken);
					pw.println("Error at token " + currToken);

				}
				printStack();
			}
			pw.close();
			// System.out.println(tokens.size() + " tokens; index: " + i);
			return stack.size() == 0 && i == tokens.size();
		} catch(IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	public void printStack() {
		if( stack.size() == 0) {
			System.out.println("Stack empty");
			pw.println("Stack empty");
		} else {
			for(int i = stack.size() - 1; i >= 0; i-- ) {
				System.out.print(stack.get(i) + " ");
				pw.print(stack.get(i) + " ");

			}
			System.out.println();
			pw.println();
		}
	}

	private String peek() {
		if( stack.size() > 0 ) {
			return stack.get(stack.size() - 1);
		} else {
			return "Nothing to display";
		}
	}

	private String pop() {
		if( stack.size() > 0 ) {
			String top = stack.get(stack.size() - 1);
			stack.remove(stack.size() - 1);	
			return top;
		} else {
			return "Nothing to display";
		}
	}

	private void push(String s) {
		stack.add(s);
	}

	private boolean isVariable(String s) {
		return s.charAt(0) >= 'A' && s.charAt(0) <= 'Z';
	}

	private void updateStack(String production) {
		String[] arr = production.split(" ");
		pop();
		for(int i = arr.length - 1; i >= 0; i--) {
			if( arr[i].length() > 0) {
				if(!arr[i].equals("e")) {
					push(arr[i]);
				}
			}
		}
	}


	private class StackItem {
		private String value;
		private int type;
		private StackItem[] production;
		public static final int VARIABLE = 0;
		public static final int TERMINAL = 1;

		public StackItem(String value, int type) {
			this.value = value;
			this.type = type;
		}
		
		public StackItem(String value, int type,String production) {
			this.value = value;
			this.type = type;
			String[] arr = production.split(" ");
			this.production = new StackItem[arr.length];
			for(int i = 0; i < arr.length; i++) {
				char c = arr[i].charAt(0);
				this.production[i] = new StackItem(arr[i],c >= 'A' && c <= 'Z' 
													? VARIABLE : TERMINAL);
			}
		}

		public StackItem[] production() {
			return production;
		}

		public int type() {
			return type;
		}

		public String toString() {
			return value;
		}
	}
}