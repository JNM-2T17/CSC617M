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
	private Stack<Fuck> stack2;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
		stack = new ArrayList<String>();
		stack2 = new Stack<Fuck>();
		parseTable = new HashMap<String,HashMap<String,String>>();
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

	public boolean parse() {
		try {
			pw = new PrintWriter(
								new BufferedWriter(
									new FileWriter(
										new File("logs.txt"))));

			push("S");
			int i = 0;
			boolean error = false;
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
					// System.out.println("Var: " + top);
					String prod = parseTable.get(top).get(currToken.type());
					
					if( prod == null ) {
						System.out.println("Error: unexpected " + currToken);
						pw.println("Error: unexpected " + currToken);
						i++;
						error = true;
					} else if( prod.equals("SYNCH") ) {
						// System.out.println("Error at token " + currToken);
						pop();
						error = true;
					} else {
						// System.out.println(top + " -> " + prod);
						// pw.println(top + " -> " + prod);
						updateStack(top,prod);
						if(prod.equals("e")) {
							rollUpStack();
						}
					}
				} else if( currToken.type().equals(top)) {
					i++;
					pop();
					Fuck f = stack2.peek();
					f.setNext(currToken.token());
					if(f.isSet()) {
						rollUpStack();
					}	
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
			System.out.println(stack2.peek().value());
			return !error && stack.size() == 0 && i == tokens.size();
		} catch(IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	public void rollUpStack() {
		Fuck f = stack2.pop();
						
		Fuck f2 = stack2.peek();
		do{
			f2.setNext(f);

			if( f2.isSet() && !f2.type().equals("S")) {
				f = stack2.pop();
				f2 = stack2.peek();
			} else {
				break;
			}
		} while(true);
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

	private void updateStack(String left,String production) {
		String[] arr = production.split(" ");
		pop();
		stack2.push(new Fuck(left,production));
		for(int i = arr.length - 1; i >= 0; i--) {
			if( arr[i].length() > 0) {
				if(!arr[i].equals("e")) {
					push(arr[i]);
				}
			}
		}
	}
}

class Fuck {
	private String type;
	private Fuck fuck1;
	private Fuck fuck2;
	private int value;
	private int sets;
	private int objSets;
	private int operation;
	private String shite;
	public static final int PLUS = 0;
	public static final int MULTIPLY = 1;
	public static final int E = 2;

	public Fuck(String type, String shite) {
		this.type = type;
		this.shite = shite;
		if( shite.equals("e")) {
			sets = 0;
			operation = E;
		} else {
			sets = shite.split(" ").length;
		}
		objSets = 0;
	}

	public boolean isSet() {
		return sets == 0;
	}

	public void setNext(Fuck fuck) {
		// System.out.println("Setting " + fuck.type() + " to " + type + ";Sets: " + sets);
		switch(objSets) {
			case 0:
				fuck1 = fuck;
				break;
			case 1:
				fuck2 = fuck;
				break;
			default:
		}
		objSets++;
		sets--;
	}

	public String type() {
		return type;
	}

	public void setNext(String s) {
		// System.out.println("Setting " + s + "; sets = " + sets);
		try {
			int i = Integer.parseInt(s);
			value = i;
			sets--;
		} catch(NumberFormatException nfe) {
			if( s.equals("*") ) {
				operation = MULTIPLY;
			} else if( s.equals("+")) {
				operation = PLUS;
			}
			sets--;
		}
	}

	public int value(int val) {
		// System.out.println(this);
		switch(type) {
			case "E2":
				if( operation == E ) {
					return val;
				} else if(operation == PLUS) {
					return val + fuck1.value();
				} else {
					return fuck2.value(val * fuck1.value());
				}
			case "T2":
				if( operation == E ) {
					return val;
				} else {
					return val * fuck1.value();
				}
			case "E3":
				if( operation == E ) {
					return val;
				} else {
					return val + fuck1.value();
				}
			default:
				return value;
		}
	}

	public int value() {
		// System.out.println(this);
		switch(type) {
			case "S":
				return fuck1.value();
			case "E":
			case "T":
				if( fuck2 == null ) {
					return fuck1.value(value);
				} else {
					return fuck2.value(fuck1.value());
				}
			default:
				return value;
		}
	}

	public String toString() {
		return type + " -> " + shite;
	}
}