import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TableGenerator {
	public static TableGenerator instance;
	private HashMap<String,ArrayList<Production>> prods;
	private HashMap<String,ArrayList<String>> firsts;
	private HashMap<String,ArrayList<String>> follows;
	private ArrayList<Production> productionList;
	private ArrayList<String> variables;
	private ArrayList<String> terminals;
	private ArrayList<State> states;
	private ArrayList<Action> shifts;
	public static final String EOF = "EOF";

	public static void main(String[] args) {
		try {
			TableGenerator tg = new TableGenerator(args[0]);
			tg.generateParseTable();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public TableGenerator(String cfgFile) throws Exception {
		prods = new HashMap<String,ArrayList<Production>>();
		firsts = new HashMap<String,ArrayList<String>>();
		follows = new HashMap<String,ArrayList<String>>();
		variables = new ArrayList<String>();
		productionList = new ArrayList<Production>();
		terminals = new ArrayList<String>();
		states = new ArrayList<State>();
		shifts = new ArrayList<Action>();
		extractProductions(cfgFile);
		instance = this;
	}

	private void extractProductions(String cfgFile) throws Exception{
		BufferedReader br = new BufferedReader(
									new FileReader(
										new File(cfgFile)));
		String curr;
		ArrayList<Production> start = new ArrayList<Production>();
		prods.put("START",start);
		boolean started = false;
		do {
			curr = br.readLine();
			if( curr != null ) {
				if( curr.length() > 0 && !curr.startsWith("//") ) {
					int comma = curr.indexOf(",");
					String left = curr.substring(0,comma);
					String production = curr.substring(comma + 1)
											.replaceAll("&com;",",")
											.replaceAll("&quot;","\"");
					ArrayList<Production> temp = prods.get(left);
					if( temp == null ) {
						temp = new ArrayList<Production>();
						prods.put(left,temp);
						firsts.put(left,new ArrayList<String>());
						follows.put(left,new ArrayList<String>());
						variables.add(left);
						if( !started ) {
							start.add(new Production("START",left));
							started = true;
						}
					}
					Production pro = new Production(left,production);
					temp.add(pro);
					productionList.add(pro);
				}
			} else {
				break;
			}
		} while(true);
		br.close();
		computeFirsts();
		computeFollows();
	}

	public Production getProduction(int index) {
		return productionList.get(index);
	}

	public boolean isVariable(String var) {
		for(String s: variables) {
			if( s.equals(var)) {
				return true;
			}
		}
		return false;
	}

	public Production[] productions(String variable) {
		return prods.get(variable).toArray(new Production[0]);
	}

	private void computeFirsts() {
		HashMap<String,ArrayList<Production>> processQueue 
			= new HashMap<String,ArrayList<Production>>();
		HashMap<String,ArrayList<Production>> waitQueue
			= new HashMap<String,ArrayList<Production>>();
		HashMap<String,ArrayList<String>> listenQueue
			= new HashMap<String,ArrayList<String>>();
		ArrayList<String> nullables = new ArrayList<String>();

		for(String s: variables) {
			ArrayList<Production> newList = new ArrayList<Production>();
			ArrayList<Production> wait = new ArrayList<Production>();
			ArrayList<String> listen = new ArrayList<String>();
			processQueue.put(s,newList);
			waitQueue.put(s,wait);
			listenQueue.put(s,listen);
			ArrayList<Production> temp = prods.get(s);
			for(Production p: temp) {
				newList.add(p);
			}
		}
		for(String s: variables) {
			ArrayList<String> first = firsts.get(s);
			ArrayList<Production> curr = processQueue.get(s);
			ArrayList<String> listen = listenQueue.get(s);
			for(int x = 0; x < curr.size(); x = x) {
				Production p = curr.get(x);
				if( p.isEmpty() ) {
					addFirst(p.variable(),"");
					nullables.add(p.variable());
					curr.remove(p);
				} else {
					if( isVariable(p.part(0)) ) {
						waitQueue.get(p.part(0)).add(p);
						if( !p.part(0).equals(p.variable())) {
							listenQueue.get(p.part(0)).add(p.variable());
						}
						x++;
					} else {
						addFirst(s,p.part(0));
						for(String s2: listen) {
							addFirst(s2,p.part(0));
						}
						curr.remove(p);
					}
				}
			}
		}

		//for each nullable variable
		for(String s: nullables) {
			ArrayList<Production> waiting = waitQueue.get(s);
			for(Production p: waiting) {
				if( 1 == p.size() ) {
					addFirst(p.variable(),"");
				} else {
					for(int i = 1; i < p.size(); i++ ) {
						if( isVariable(p.part(i)) ) {
							listenQueue.get(p.part(i)).add(p.variable());
							//if next variable is not nullable
							if(nullables.indexOf(p.part(i)) == - 1) {
								break;
							}
						} else {
							addFirst(p.variable(),p.part(i));
							break;
						}
					}
				}
			}
		} 

		boolean updates = true;

		while(updates) {
			updates = false;
			for(String s: variables) {
				ArrayList<String> listen = listenQueue.get(s);
				ArrayList<String> first = firsts.get(s);
				for(String listener: listen) {
					for(String f: first) {
						updates = updates || addFirst(listener,f);
					}
				}
			}
		}

		for(String s : variables ) {
			ArrayList<String> first = firsts.get(s);
			ArrayList<Production> prodList = prods.get(s);
			boolean hasEmpty = false;
			for(Production p : prodList) {
				if(p.isEmpty()) {
					hasEmpty = true;
					break;
				}
			}
			if( !hasEmpty ) {
				first.remove("");
			}
		}

		// for(String s: variables ) {
		// 	ArrayList<String> first = firsts.get(s);
		// 	System.out.print(s + " -> ");
		// 	for(String s2: first) {
		// 		System.out.print((s2.length() == 0 ? "e" : s2) + ", ");
		// 	}
		// 	System.out.println();
		// }
	}

	private boolean addFirst(String var, String first) {
		ArrayList<String> firstArr = firsts.get(var);
		if( firstArr.indexOf(first) == -1) {
			firstArr.add(first);
			return true;
		} else {
			return false;
		}
	}

	public String[] first(String sequence) {
		if( isVariable(sequence) ) {
			return firsts.get(sequence).toArray(new String[0]);
		} else {
			String[] parts = sequence.split(" ");
			if( parts.length == 0 ) {
				return new String[] {""};
			} else if( !isVariable(parts[0]) ) {
				return new String[] {parts[0]};
			} else {
				ArrayList<String> first = new ArrayList<String>();
				boolean hasEmpty = false;
				int i = 0;
				//for each item in sequence
				do {
					hasEmpty = false;
					String[] temp = first(parts[i]);
					for(int j = 0; j < temp.length; j++) {
						if( temp[j].length() == 0) {
							hasEmpty = true;
							if( i == parts.length - 1) {
								first.add(temp[j]);
								hasEmpty = false;
							}
						} else {
							boolean found = false;
							for(int k = 0; k < first.size(); k++ ) {
								if(first.get(k).equals(temp[j])) {
									found = true;
									break;
								}
							}
							if( !found ) {
								first.add(temp[j]);
							}
						}
					}
					i++;
				} while(hasEmpty); //while current item can be empty

				return first.toArray(new String[0]);
			}
		}	
	}

	public int terminalCount() {
		return terminals.size();
	}

	public int variableCount() {
		return variables.size();
	}

	private boolean addFollow(String var, String follow) {
		ArrayList<String> followArr = follows.get(var);
		if( followArr.indexOf(follow) == -1) {
			followArr.add(follow);
			return true;
		} else {
			return false;
		}
	}

	public Action getShift(State s) {
		for( Action a: shifts) {
			if( a.shift().id() == s.id() ) {
				return a;
			}
		}
		Action a = new Action(s);
		shifts.add(a);
		return a;
	}

	public void computeFollows() {
		HashMap<String,ArrayList<Production>> processQueue 
			= new HashMap<String,ArrayList<Production>>();
		HashMap<String,ArrayList<String>> listenQueue
			= new HashMap<String,ArrayList<String>>();
		ArrayList<String> nullables = new ArrayList<String>();
		for(String s: variables) {
			ArrayList<Production> newList = new ArrayList<Production>();
			ArrayList<String> listen = new ArrayList<String>();
			processQueue.put(s,newList);
			listenQueue.put(s,listen);
		}

		follows.get(variables.get(0)).add("EOF");

		for(Production p : productionList) {
			for(int i = 0; i < p.size(); i++ ) {
				if( isVariable(p.part(i)) ) {
					processQueue.get(p.part(i)).add(p);
				}
			}
		}
		for(String s : variables) {
			ArrayList<Production> process = processQueue.get(s);
			ArrayList<String> listen = listenQueue.get(s);
			//for each production, search variable
			for(Production p: process) {
				for(int i = 0; i < p.size(); i++) {
					if( p.part(i).equals(s)) {
						String[] follow = first(p.remains(i + 1));
						//add non-empty
						for(String fol: follow) {
							if( fol.length() > 0 ) {
								addFollow(s,fol);
								for(String var : listen) {
									addFollow(var,fol);
								}
							} else {
								listenQueue.get(p.variable()).add(s);
							}
						}
					}
				}
			}
		}

		boolean update = true;
		while(update) {
			update = false;
			for(String s : variables) {
				ArrayList<String> listen = listenQueue.get(s);
				ArrayList<String> follow = follows.get(s);

				for(String lis: listen) {
					for(String fol: follow) {
						update = update || addFollow(lis,fol);
					}
				}
			}
		}

		// for(String s: variables) {
		// 	ArrayList<String> follow = follows.get(s);

		// 	System.out.print(s + " -> ");
		// 	for(String fol: follow) {
		// 		System.out.print(fol + ", ");
		// 	}			
		// 	System.out.println();
		// }
	}

	public String[] follow(String variable) {
		if( isVariable(variable) ) {
			return follows.get(variable).toArray(new String[1]);
		} else {
			return new String[0];
		}
	}

	public State addState(State s) {
		for(State curr : states) {
			if( curr.equals(s)) {
				return curr;
			}
		}
		states.add(s);
		s.validate();
		return s;
	}

	public void registerSymbol(String s) {
		if(s != null && !isVariable(s) && terminals.indexOf(s) == -1 ) {
			terminals.add(s);
		}
	}

	public State[] generateParseTable() {
		ArrayList<State> frontier = new ArrayList<State>();
		Production start = prods.get("START").get(0);
		State s = new State(new Item[]{new Item(start,new String[]{EOF})},this);
		s.validate();
		frontier.add(s);
		states.add(s);
		while(frontier.size() > 0) {
			State curr = frontier.get(0);
			State[] newStates = curr.expand();
			frontier.remove(0);
			for(State state: newStates ) {
				frontier.add(state);
			}
		}

		try {
			PrintWriter pw = new PrintWriter(
								new BufferedWriter(
									new FileWriter(
										new File("CFG.csv"))));
			terminals.add(EOF);
			for(String term: terminals ) {
				pw.print("," + (term.equals(",") ? "&com;" : term));
			}
			for(String var: variables ) {
				pw.print("," + var);
			}
			pw.println();

			boolean globalErr = false;
			for(State state: states) {
				pw.print(state.id());
				boolean error = state.generateReductions();
		
				for(String terminal: terminals) {
					Action action = state.getAction(terminal);
					pw.print("," + (action == null ? "" : action));
				}

				for(String var: variables) {
					Integer gt = state.getGoto(var);
					pw.print("," + (gt == null ? "" : gt));
				}
				pw.println();
				if( error ) {
					globalErr = true;
					System.out.println();
				}
				// if( state.hasFollow() ) {
				// 	System.out.println("State " + state.id() + " RECOVERY");
				// 	for(String terminal: terminals) {
				// 		if( state.getRecovery(terminal) != null ) {
				// 			System.out.print(terminal + "->" 
				// 								+ state.getRecovery(terminal)+ " ");
				// 		}
				// 	}
				// 	System.out.println();
				// }
			}

			pw.close();
			if( !globalErr ) {
				return states.toArray(new State[1]);
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}