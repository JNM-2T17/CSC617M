public class SubElem extends NonTerminal {
	public SubElem(String pattern) {
		super("SUBELEM",pattern);
	}

	public void interpret() throws Exception {
		if(!isSet()) {
			throw new Exception(NOT_SET_MESSAGE);
		}
	}

	public Playable value(Playable base) {
		try {
			Token num = (Token)getComponent("num");
			int arg = 0;
			SubElem se = (SubElem)getComponent("SUBELEM");
			if( num != null ) {
				num.interpret();
				arg = num.intValue();
			}
			Playable ret = null	;
			switch(getProdString()) {
				case "e":
					ret = base;
					break;
				case "* num":
					ret = base.multiply(arg);
					break;
				case "+ num SUBELEM":
					ret = se.value(base.changePitch(arg));
					break;
				case "++ SUBELEM":
					ret = se.value(base.changePitch(1));
					break;
				case "- num SUBELEM":
					ret = se.value(base.changePitch(-arg));
					break;
				case "-- SUBELEM":
					ret = se.value(base.changePitch(-1));
					break;
				case "> num SUBELEM":
					ret = se.value(base.changeTime(arg));
					break;
				case ">>":
					ret = se.value(base.changeTime(2));
					break;
				case "< num SUBELEM":
					ret = se.value(base.changeTime(1.0 / arg));
					break;
				case "<< SUBELEM":
					ret = se.value(base.changeTime(0.5));
					break;
				default:
			}
			return ret;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}