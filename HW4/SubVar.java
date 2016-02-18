import java.util.ArrayList;
import java.util.Iterator;

public class SubVar extends NonTerminal
{
    private int[] indices;
    
	public SubVar(String pattern)
    {
		super("SUBVAR",pattern);
	}

	public void interpret() throws Exception
    {
		if(!isSet())
        {
			throw new Exception(NOT_SET_MESSAGE);
		}
        else
        {
            indices = null;
            Token openingBracket = (Token)getComponent("[");
            if(openingBracket != null)
            {
                int index = ((Token)getComponent("num")).intValue();
                SubVar2 sv2 = (SubVar2)getComponent("SUBVAR2");
                sv2.interpret();
                Integer index2 = sv2.getIndex();
                if(index < 0)
                    throw new Exception("Index is not greater than 0.");
                else if(index2 != -1)
                {
                    if(index2 > index)
                    {
                        indices = new int[2];
                        indices[0] = index;
                        indices[1] = index2;
                    }
                    else
                        throw new Exception("Second index is not higher than first index.");
                }
                else
                {
                    indices = new int[1];
                    indices[0] = index;
                }
            } 
            else 
            {
                indices = new int[0];
            }
		}
	}
    
    public int[] getIndices()
    {
        return indices;
    }
}