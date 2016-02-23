public class ElemsR extends NonTerminal {
	public ElemsR(String pattern) {
		super("ELEMSR",pattern);
	}

	public void interpret() {
		ParseObject po = getComponent("ELEMS");
		try {
			po.interpret();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void execute() {}
}