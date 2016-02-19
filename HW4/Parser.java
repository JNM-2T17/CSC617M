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
import java.util.Stack;

public class Parser {
	private List<Token> tokens;
	private HashMap<String,HashMap<String,String>> parseTable;
	private ArrayList<String> stack;
	private PrintWriter pw;
	private Stack<NonTerminal> nonTerminals;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
		parseTable = new HashMap<String,HashMap<String,String>>();
		stack = new ArrayList<String>();
		ArrayList<String> terminals = new ArrayList<String>();
		nonTerminals = new Stack<NonTerminal>();

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
							// System.out.println("Adding " + prods[0] + " -> " 
							// 					+ temp.get(terminals.get(i - 1)) 
							// 					+ " on " + terminals.get(i - 1));
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

	public NonTerminal parse() {
		try {
			pw = new PrintWriter(
								new BufferedWriter(
									new FileWriter(
										new File("logs.txt"))));

			push("PROG");
			int i = 0;
			boolean error = false;
			NonTerminal start = null;

			// printStack();
			while(stack.size() > 0) {
				String top = peek();
				Token currToken = i == tokens.size() ? new Token("EOF","EOF"
									,tokens.get(tokens.size() - 1).lineNo()) 
									: tokens.get(i);
				// System.out.println(currToken);
				if( currToken.type().equals("other") ) {
					System.out.println("Unrecognized token " + currToken.token() 
										+ " at line " + currToken.lineNo() );
					pw.println("Unrecognized token " + currToken.token() 
								+ " at line " + currToken.lineNo() );
				}

				if( isVariable(top) ) {
					String prod = parseTable.get(top).get(currToken.type());
					
					if( prod == null ) {
						System.out.println("Error: unexpected " + currToken);
						pw.println("Error: unexpected " + currToken);
						i++;
						error = true;
					} else if( prod.equals("SYNCH") ) {
						System.out.println("Error at token " + currToken);
						pop();
						error = true;
					} else {
						if( prod.equals("newline ELEMS")) {
							String next = i + 1 < tokens.size() 
											? tokens.get(i + 1).type() 
											: "EOF";
							String[] change = new String[] {
								"}","EOF","play"
							};
							boolean willChange = false;

							for(String s: change) {
								if( next.equals(s)) {
									willChange = true;
									break;
								} 
							}
							if( willChange ) {
								prod = "e";
							}
						}
						// System.out.println(top + " -> " + prod);
						// pw.println(top + " -> " + prod);
						updateStack(top,prod);
						start = rollup();
					}
				} else if( currToken.type().equals(top)) {
					i++;
					pop();
					start = rollup(currToken);
				} else {
					if( currToken.type().equals("newline")) {
						pop();
					} else {
						i++;
						System.out.println("Error at token " + currToken + " expected " + top);
						pw.println("Error at token " + currToken);
					}
					error = true;
				}
				// printStack();
			}
			pw.close();
			// System.out.println(tokens.size() + " tokens; index: " + i);
			// System.out.println("Error: " + error + "; stack size: " + stack.size());
			if(!error && stack.size() == 0 && i == tokens.size()) {
				try {
					start.interpret();
				} catch(Exception e) {
					e.printStackTrace();
				}
				return start;
			} else {
				return null;
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
			return null;
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

	private NonTerminal rollup() {
		NonTerminal curr = nonTerminals.peek();
		NonTerminal top = curr;
		while(top.isSet()) {
			curr = nonTerminals.pop();
			if( nonTerminals.empty()) {
				// System.out.println(curr.type() + " is last on stack");
				return curr;
			}
			top = nonTerminals.peek();
			try {
				// System.out.println("Setting " + curr.type() + " to " + top.type());
				top.setNext(curr);
				// System.out.println(top.type() + " is " + (top.isSet() ? "" : "not ") + "set");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private NonTerminal rollup(Token currToken) {
		NonTerminal curr = nonTerminals.peek();
		try {
			// System.out.println("Setting " + currToken + " to " + curr.type());
			curr.setNext(currToken);
			// System.out.println(curr.type() + " is " + (curr.isSet() ? "" : "not ") + "set");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return rollup();
	}

	private void updateStack(String type,String production) {
		String[] arr = production.split(" ");
		pop();
		// System.out.println("Pushing " + type + " on the stack");
		nonTerminals.push(NonTerminalFactory.instance()
							.getNonTerminal(type,production));
		for(int i = arr.length - 1; i >= 0; i--) {
			if( arr[i].length() > 0) {
				if(!arr[i].equals("e")) {
					push(arr[i]);
				}
			}
		}
	}
}