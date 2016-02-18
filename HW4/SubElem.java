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
					ret = base.changePitch(arg);
					ret = se.value(ret);
					break;
				case "++ SUBELEM":
					ret = base.changePitch(1);
					ret = se.value(ret);
					break;
				case "- num SUBELEM":
					ret = base.changePitch(-arg);
					ret = se.value(ret);
					break;
				case "-- SUBELEM":
					ret = base.changePitch(-1);
					ret = se.value(ret);
					break;
				case "> num SUBELEM":
					ret = base.changeTime(arg);
					ret = se.value(ret);
					break;
				case ">>":
					ret = base.changeTime(2);
					ret = se.value(ret);
					break;
				case "< num SUBELEM":
					ret = base.changeTime(1.0 / arg);
					ret = se.value(ret);
					break;
				case "<< SUBELEM":
					ret = base.changeTime(0.5);
					ret = se.value(ret);
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