import java.util.ArrayList;
import java.util.Iterator;

public class Var extends NonTerminal implements Playable
{
	private Playable play;
    
	public Var(String pattern)
    {
		super("VAR",pattern);
	}

	public void interpret() throws Exception
    {
		if(!isSet())
        {
			throw new Exception(NOT_SET_MESSAGE);
		}
        else
        {
            play = SymbolTable.instance().get(((Token) getComponent("varname")).token());
            
            if(play == null)
                throw new Exception("Variable \"" + ((Token) getComponent("varname")).token() + "\"does not exist.");
            else
            {
                SubVar sv = (SubVar)getComponent("SUBVAR");
                sv.interpret();
                int[] indices = sv.getIndices();
                if(indices != null)
                {
                    if(play instanceof Note || play instanceof Rest)
                        throw new Exception("Indexing not applicable.");
                    else if(play instanceof Sync)
                    {
                        Playable[] originalSync = ((Sync) play).getPlayables();
                        int lengthSync = 1;
                        if(indices.length == 2)
                            lengthSync = indices[1] - indices[0];
                        Playable[] newSyncPlayables = new Playable[lengthSync];
                        for(int i = 0, curIndex = indices[0] - 1; i < lengthSync; i++, curIndex++)
                            newSyncPlayables[i] = originalSync[curIndex];
                        play = new Sync(newSyncPlayables);
                    }
                    else if(play instanceof Seq)
                    {
                        Playable[] originalSeq = ((Seq) play).getPlayables();
                        int lengthSeq = 1;
                        if(indices.length == 2)
                            lengthSeq = indices[1] - indices[0];
                        Playable[] newSeqPlayables = new Playable[lengthSeq];
                        for(int i = 0, curIndex = indices[0] - 1; i < lengthSeq; i++, curIndex++)
                            newSeqPlayables[i] = originalSeq[curIndex];
                        play = new Seq(newSeqPlayables);
                    }
                }
            }
		}
	}

	public void play()
    {
		play.play();
	}

	public Playable changePitch(int semitones)
    {
		return play.changePitch(semitones);
	}

	public Playable changeTime(double factor)
    {
		return play.changeTime(factor);
	}

	public Playable multiply(int times)
    {
		return (play = play.multiply(times));
	}
}