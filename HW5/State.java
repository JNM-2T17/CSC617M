import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class State {
	private static int STATE_ID = 0;
	private int id;
	private Item[] items;
	private ArrayList<String> transKeys;
	private HashMap<String,State> transition;
	private boolean isExpanded;
	private HashMap<String,Action> actions;
	private HashMap<String,Integer> gotoTable;
	private HashMap<String,Integer> tempShiftTable;
	private HashMap<String,String> follows;
	private TableGenerator tg;

	public State(TableGenerator tg) {
		this.tg = tg;
		transKeys = new ArrayList<String>();
		transition = new HashMap<String,State>();
		actions = new HashMap<String,Action>();
		gotoTable = new HashMap<String,Integer>();
		tempShiftTable = new HashMap<String,Integer>();
		follows = new HashMap<String,String>();
		isExpanded = false;
	}

	public State(Item[] items,TableGenerator tg) {
		this.tg = tg;
		generateItems(items);
		transKeys = new ArrayList<String>();
		transition = new HashMap<String,State>();
		actions = new HashMap<String,Action>();
		gotoTable = new HashMap<String,Integer>();
		follows = new HashMap<String,String>();
		isExpanded = false;
	}

	public void validate() {
		id = STATE_ID;
		STATE_ID++;
	}

	public int id() {
		return id;
	}
		
	private void generateItems(Item[] items) {
		ArrayList<Item> lr1 = new ArrayList<Item>();
		ArrayList<Item> frontier = new ArrayList<Item>();
		for(Item i : items) {
			frontier.add(i);
		}
		while(frontier.size() > 0) {
			Item i = frontier.get(0);
			boolean isFound = false;
			for(Item item:lr1) {
				if(item.equals(i)) {
					isFound = true;
					break;
				}
			}
			if( !isFound ) {
				lr1.add(i);
				String currPart = i.currPart();
				tg.registerSymbol(currPart);
				//if next is a variable
				if( currPart != null && tg.isVariable(currPart)) {
					ArrayList<String> theFirst = new ArrayList<String>();
					String[] first = tg.first(i.remains());
					Production[] prods = tg.productions(currPart);
					boolean hasEmpty = false;
					for(String s: first) {
						if( s.length() == 0 ) {
							hasEmpty = true;
						} else {
							theFirst.add(s);
						}
					}
					if( hasEmpty ) {
						String[] temp = i.lookahead();
						for(String s: temp) {
							if(theFirst.indexOf(s) == -1) {
								theFirst.add(s);
							}
						}
					}
					first = theFirst.toArray(new String[1]);
					for(Production p: prods) {
						frontier.add(new Item(p,first));
					}
				}
			}
			frontier.remove(0);
		}

		this.items = lr1.toArray(new Item[0]);
	}

	public Action getAction(String key) {
		return actions.get(key);
	}

	public Integer getGoto(String key) {
		return gotoTable.get(key);
	}

	public boolean generateReductions() {
		boolean error = false;
		boolean printedState = false;
		
		for(int i = 0; i < size(); i++) {
			Item currItem = item(i);
			if(currItem.isReduction()) {
				String[] lookahead = currItem.lookahead();
				for(String look: lookahead ) {
					Action theAction = actions.get(look);
					if( theAction != null ) {
						if( theAction.type().equals("SHIFT") ) {
							if( !printedState ) {
								System.out.println(this);
								printedState = true;
							}
							
							System.out.println("SR Conflict at terminal " 
												+ look + " and production " 
												+ currItem.prodString());
							error = true;
						} else {
							if( theAction.type().equals("ACCEPT")) {
								if( !printedState ) {
									System.out.println(this);
									printedState = true;
								}
								
								System.out.println("AR conflict with " 
											+ currItem.prodString());
								error = true;
							} else {
								Production conflict = theAction.reduction();
								if( currItem.prodId() 
										!= conflict.id() ) {
									if( !printedState ) {
										System.out.println(this);
										printedState = true;
									}
									
									System.out.println("RR Conflict " 
														+ "with " 
														+ currItem
														.prodString() 
														+ " and " 
														+ conflict);
									error = true;
								}
							}
						}
					} else if( currItem.prodId() == -1) {
						actions.put(look,new Action());
					} else {
						actions.put(look
									,new Action(currItem.productionObject()));
					}
				}
			}
		}
		return error;
	}

	public void putAction(String key,State s) {
		if( tg.isVariable(key) ) {
			gotoTable.put(key,s.id());
		} else {
			actions.put(key,tg.getShift(s));
		}
	}

	public void putAction(String key, int state) {
		if( tg.isVariable(key) ) {
			gotoTable.put(key,state);
		} else {
			tempShiftTable.put(key,state);
		}
	}

	public void accept() {
		actions.put("EOF",new Action());
	}

	public void updateShifts(State[] table) {
		Iterator<String> itr = tempShiftTable.keySet().iterator();
		while(itr.hasNext()) {
			String next = itr.next();
			actions.put(next,tg.getShift(table[tempShiftTable
												.get(next).intValue()]));
		}
	}

	public void putAction(String key, Production p) {
		actions.put(key,new Action(p));
	}

	public void putGoto(String key, State gotoState) {
		if( gotoTable.get(key) != null ) {
			updateGoto(gotoTable.get(key),gotoState);
		}
		addFollow(key);
	}

	public void updateGoto(int prev,State newVal) {
		Iterator<String> itr = gotoTable.keySet().iterator();
		while(itr.hasNext()) {
			String s = itr.next();
			if( gotoTable.get(s).intValue() == prev) {
				gotoTable.put(s,newVal.id());
			}
		}
	}

	public String getRecovery(String token) {
		return follows.get(token);
	} 

	private void addFollow(String var) {
		if( tg.isVariable(var) ) {
			String[] follows = tg.follow(var);
			for(String f: follows) {
				if( this.follows.get(f) == null) {
					this.follows.put(f,var);
				}
			}
		}
	}

	public boolean hasFollow() {
		return follows.size() > 0;
	}

	public State[] expand() {
		if( isExpanded ) {
			return new State[0];
		} else {
			ArrayList<State> states = new ArrayList<State>();
			for(int i = 0; i < items.length; i++) {
				Item item = items[i];
				if( !item.isReduction()) {
					String curr = item.currPart();
					
					if(transKeys.indexOf(curr) == -1) {
						transKeys.add(curr);
						ArrayList<Item> newItems = new ArrayList<Item>();
						newItems.add(item.moveForward());
						for(int j = i + 1; j < items.length; j++) {
							String currPart = items[j].currPart();
							if( currPart != null && currPart.equals(curr)) {
								newItems.add(items[j].moveForward());
							}
						}
						State newState = new State(newItems.toArray(new Item[0])
													,tg);
						newState = tg.addState(newState);
						states.add(newState);
						transition.put(curr,newState);
						if( tg.isVariable(curr)) {
							gotoTable.put(curr,newState.id());
							addFollow(curr);
						} else {
							actions.put(curr,tg.getShift(newState));
						}
					}
				}
			}
			return states.toArray(new State[0]);
		}
	}

	public String transKey(int index) {
		return transKeys.get(index);
	}

	public State transition(String key) {
		return transition.get(key);
	}

	public Item item(int i) {
		return items[i];
	}

	public int size() {
		return items.length;
	}

	public boolean equals(State s) {
		int count = 0;
		for(int i = 0; i < s.size(); i++) {
			Item curr = s.item(i);
			for(Item item : items) {
				if( item.equals(curr)) {
					count++;
					break;
				}
			}
		}
		return count == size() && count == s.size();
	}

	public String toString() {
		String ret = "State # " + id + "\n";
		for(Item i: items) {
			ret += i.toString() + "\n";
		}
		return ret.substring(0,ret.length() - 1);
	}
}