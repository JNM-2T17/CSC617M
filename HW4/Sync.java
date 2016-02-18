import java.util.ArrayList;
import java.util.Iterator;

public class Sync extends NonTerminal implements Playable
{
	private Playable[] playables;
    
	public Sync(String pattern)
    {
		super("SYNC",pattern);
	}
	
	public Sync(Playable[] p)
    {
		super("SYNC","sync SUBBODY");
		playables = p;
	}

	public Playable[] getPlayables() {
		return playables;
	}

	public void interpret() throws Exception
    {
		if(!isSet())
        {
			throw new Exception(NOT_SET_MESSAGE);
		}
        else
        {
			Subbody sb = (Subbody) getComponent("SUBBODY");
			sb.interpret();
            Iterator<Elem> itr = sb.getElems();
			ArrayList<Playable> elems = new ArrayList<Playable>();
			while(itr.hasNext() )
            {
				elems.add(itr.next());
			}
			playables = elems.toArray(playables);
		}
	}

	public void play()
    {
    	try {
			SeqThread[] seqs = new SeqThread[playables.length];
			for(int i = 0; i < seqs.length; i++) {
				seqs[i] = new SeqThread(playables[i]);
			}
			for(int i = 0; i < seqs.length; i++) {
				seqs[i].start();
			}
			for(int i = 0; i < seqs.length; i++) {
				seqs[i].join();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void changePitch(int semitones)
    {
        for(int i = 0; i < playables.length; i++)
		  playables[i].changePitch(semitones);
	}

	public void changeTime(double factor)
    {
        for(int i = 0; i < playables.length; i++)
		  playables[i].changeTime(factor);
	}

	public Playable multiply(int times)
    {
        Playable[] syncs = new Playable[times];
        for(int i = 0; i < times; i++)
            syncs[i] = new Sync(playables);
        return new Seq(syncs);
	}

	private class SeqThread extends Thread {
		private Playable p;

		public SeqThread(Playable p) {
			this.p = p;
		}

		public void run() {
			p.play();
		}
	}
}