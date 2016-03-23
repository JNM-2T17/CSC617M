import java.util.ArrayList;
import java.util.Iterator;

public class SubVar2 extends NonTerminal
{
    private int index;
    
	public SubVar2(String pattern)
    {
		super("SUBVAR2",pattern);
	}

	public void interpret() throws Exception
    {
        if(!isSet())
        {
		    throw new Exception(NOT_SET_MESSAGE);
		}
        else
        {
            if(getComponent("~") != null)
            {
                Token t = ((Token)getComponent("num"));
                t.interpret();
                index = t.intValue();
            }
            else
                index = -1;
		}
	}
    
    public int getIndex()
    {
        return index;
    }
}