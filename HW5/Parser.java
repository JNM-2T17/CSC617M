import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Parser {
	private List<Token> tokens;
	private int currIndex;
	private NonTerminalFactory ntf;
	private State[] parseTable;
	private Stack<State> states;
	private Stack<ParseObject> parsing;

	public Parser(NonTerminalFactory ntf, State[] parseTable) {
		this.ntf = ntf;
		this.parseTable = parseTable;
		states = new Stack<State>();
		parsing = new Stack<ParseObject>();
	}

	public NonTerminal parse() {
		states.push(parseTable[0]);
		boolean error = false;
		boolean accepted = false;

		while(currIndex < tokens.size() && !accepted) {
			State topState = states.peek();
			Token currToken = tokens.get(currIndex);

			Action act = topState.getAction(currToken.type());

			// for( State s: states) {
			// 	System.out.print(s.id() + " ");
			// }

			if( act == null ) {
				error = true;
				// System.out.println(topState);
				// printStack();
				// while(!topState.hasFollow()) {
				// 	topState = states.pop();
				// 	parsing.pop();
				// }
				// String recover;
				// do {
				// 	recover = topState.getRecovery(currToken.type());
				// 	if( recover == null ) {
				// 		currIndex++;
				// 	} else {
				// 		NonTerminal reco = ntf.getNonTerminal(recover,"");
				// 		parsing.push(reco);
				// 		states.push(parseTable[topState.getGoto(reco.type())]);
				// 	}
				// } while(currIndex < tokens.size() && recover == null);
				// if( currIndex == tokens.size() ) {
				// 	break;
				// }
				do {
					System.out.println("Error: Unexpected token " + currToken);
					currIndex++;
					currToken = tokens.get(currIndex);
					act = topState.getAction(currToken.type());
				} while( act == null && currIndex < tokens.size() );
			} if(act != null ) {//else {
				switch(act.type()) {
					case "ACCEPT":
						accepted = true;
						break;
					case "SHIFT":
						parsing.push(currToken);
						states.push(act.shift());
						// System.out.println("To state " + states.peek().id());
						currIndex++;
						// System.out.print(currToken.type() + " | ");
						// printStack();
						break;
					case "REDUCE":
						Production p = act.reduction();
						NonTerminal nt = ntf.getNonTerminal(p.variable()
															,p.production());
						ArrayList<ParseObject> parts 
							= new ArrayList<ParseObject>();
						// System.out.print(p + "\t");
						// System.out.print(currToken.type() + " | ");
						// printStack();
						for(int i = 0; i < p.size(); i++) {
							states.pop();
							parts.add(0,parsing.pop());
						}
						try {
							for(ParseObject curr: parts) {
								nt.setNext(curr);
							}
						} catch(Exception e) {
							e.printStackTrace();
						}
						parsing.push(nt);
						// System.out.print(currToken.type() + " | ");
						// printStack();
						topState = states.peek();
						states.push(parseTable[topState.getGoto(nt.type())]);
						// System.out.println("To state " + states.peek().id());
						break;
					default:	
				}
			} else {
				break;
			}
		}

		if( error ) {
			return null;
		} else {
			ParseObject top = parsing.peek();
			try {
				top.interpret();
				return (NonTerminal)top;
			} catch(Exception e) {
				// if( e.getMessage() == null) {
					e.printStackTrace();
				// } else {
					// System.out.println(e.getMessage());
				// }
				return null;
			}
		}

	}

	public void printStack() {
		for(ParseObject po : parsing ) {
			if( po instanceof Token ) {
				System.out.print(((Token)po).type() + " ");
			} else {
				System.out.print(((NonTerminal)po).type() 
									+ " ");
			}
			
		} 
		System.out.println();	
	}

	public void setTokens(List<Token> tokens) {
		tokens.add(new Token("EOF","EOF"
						,tokens.get(tokens.size() - 1).lineNo()));
		this.tokens = tokens;
		currIndex = 0;
	}
}