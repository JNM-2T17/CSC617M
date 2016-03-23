public class Action {
	private Production reduction;
	private State shift;
	private String type;

	public Action() {
		type = "ACCEPT";
	}

	public Action(Production p) {
		type = "REDUCE";
		reduction = p;
	}

	public Action(State shift) {
		type = "SHIFT";
		this.shift = shift;
	}

	public Production reduction() {
		return reduction;
	}

	public String type() {
		return type;
	}

	public State shift() {
		return shift;
	}

	public void setState(State s) {
		shift = s;
	}

	public String toString() {
		return (type + " " + (type.equals("REDUCE") ? reduction.id() 
				: (type.equals("SHIFT") ? shift.id() : "" ))).trim();
	}
}