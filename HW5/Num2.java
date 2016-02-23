public class Num2 extends NonTerminal {
	private int num;

	public Num2(String pattern) {
		super("NUM2",pattern);
	}

	public void interpret() {
		Token t = (Token)getComponent("num");
		try {
			t.interpret();
		} catch(Exception e) {
			e.printStackTrace();
		}
		num = t.intValue();
	}

	public int intValue() {
		return num;
	}

	public void execute() {}
}