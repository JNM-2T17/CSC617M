import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sync extends NonTerminal implements Playable
{
	private Playable[] playables;
	private List<NoteAction> stream;
    
	public Sync(String pattern)
    {
		super("SYNC",pattern);
	}
	
	public Sync(Playable[] p)
    {
		super("SYNC","sync SUBBODY");
		playables = p;

		buildStream();
	}

	public Playable[] getPlayables() {
		return playables;
	}

	public void execute() {
		play();
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
			int ctr = 0;
			while(itr.hasNext() ) {
				elems.add(itr.next());
				ctr++;
			}
			playables = new Playable[ctr];
			playables = elems.toArray(playables);
			buildStream();
		}
	}

	public void play()
    {
    	for(NoteAction na: stream) {
    		// System.out.println(na);
    		MusicPlayer.instance().play(na);
    	}
	}

	public Playable changePitch(int semitones)
    {
    	Playable[] newPlay = new Playable[playables.length];
        for(int i = 0; i < playables.length; i++) {
		  newPlay[i] = playables[i].changePitch(semitones);
		  if( newPlay[i] == null ) {
		  	newPlay[i] = playables[i];
		  }
        }
        return new Sync(newPlay);
	}

	public Playable changeTime(double factor)
    {
        Playable[] newPlay = new Playable[playables.length];
        for(int i = 0; i < playables.length; i++) {
		  newPlay[i] = playables[i].changeTime(factor);
		  if( newPlay[i] == null ) {
		  	newPlay[i] = playables[i];
		  }
        }
        return new Sync(newPlay);
	}

	public Playable multiply(int times)
    {
        Playable[] syncs = new Playable[times];
        for(int i = 0; i < times; i++)
        {
            syncs[i] = new Sync(playables);

        }
        return new Seq(syncs);
	}

	public String getType() {
		return "SYNC";
	}

	private void buildStream() {
		ArrayList<NoteAction> nas = new ArrayList<NoteAction>();
		List<NoteAction>[] streams = new List[playables.length];
		boolean[][] actives 
			= new boolean[15][MusicPlayer.NOTE_COUNT];
        for(int i = 0; i < actives.length; i++) {
            for(int j = 0; j < actives[0].length; j++ ) {
                actives[i][j] = false;
            }
        }

        int volume = MusicPlayer.DEFAULT_VOLUME;
        for(int i = 0; i < streams.length; i++) {
        	List<NoteAction> temp = playables[i].getStream();
			streams[i] = new ArrayList<NoteAction>();
			if( temp != null ) {
				for(NoteAction na: temp) {
					NoteAction newNa = null;
					switch(na.type()) {
						case NoteAction.ON:
						case NoteAction.OFF:
							newNa = new NoteAction(na.type(),na.note()
													,na.index(), volume 
													!= MusicPlayer
														.DEFAULT_VOLUME 
													? volume : na.volume());
							break;
						case NoteAction.SLEEP:
							newNa = new NoteAction(na.type(),na.duration());
							break;
						default:
					}
					if( newNa != null ) {
						streams[i].add(newNa);
						// System.out.print(na + " ");
					}
				}
				// System.out.println();
			} else {
				volume = ((Elem)playables[i]).volume();
			}
		}

		boolean finished = false;

		while(!finished) {
			for(int i = 0; i < streams.length; i++) {
				if(streams[i].size() > 0 ) {
					finished = false;
					break;
				} 
				finished = true;
			}
			
			if( !finished ) {
				//for each stream eliminate NOTEONs
				for(int i = 0; i < streams.length; i++) {
					if( streams[i].size() > 0 ) {
						//while turning on notes
						while(streams[i].get(0).type() == NoteAction.ON) {
							//get first
							NoteAction na = streams[i].get(0);
							int tempIndex = na.index();
							//if open
							if(!actives[na.index()][na.note()]) {
								nas.add(na);
							} else {
								//look for free channel
								boolean free = false;
								for(int k = 0; !free && k < actives.length; 
										k++) {
									//if free
									if(!actives[k][na.note()]) {
										//set flags
										free = true;
										actives[k][na.note()] = true;
										na.setIndex(k);

										//set index of corresponding off
										int j = 1;
										do {
											NoteAction temp = streams[i].get(j);
											if( temp.note() == na.note() 
												&& tempIndex == temp.index() ) {
												temp.setIndex(k);
												break;
											}
											j++;
										} while(j < streams[i].size());
										nas.add(na);
									}
								}
							}
							streams[i].remove(0);
						}
					}
				}

				//find minimum sleep
				int minIndex = -1;
				double minSleep = 0;
				for(int i = 0; i < streams.length; i++) {
					if( streams[i].size() > 0 && ( minIndex == -1 
							|| streams[minIndex].get(0).duration() 
								> streams[i].get(0).duration() ) ) {
						minIndex = i;
						minSleep = streams[i].get(0).duration();
					}
				}
				nas.add(streams[minIndex].get(0));

				//reduce sleeps
				for(int i = 0; i < streams.length; i++) {
					if( streams[i].size() > 0 ) {
						NoteAction na = streams[i].get(0);
						if( i != minIndex) {
							na.reduceDuration(minSleep);
						}
						if( i == minIndex 
								|| Math.abs(na.duration() - 0.0) < 0.002) {
							streams[i].remove(0);
							while( streams[i].size() > 0) {
								NoteAction na2 = streams[i].get(0);
								if( na2.type() == NoteAction.OFF) {
									nas.add(na2);
									streams[i].remove(0);
									actives[na2.index()][na2.note()] = false;
								} else {
									break;
								}
							}
						}
					}
				}
			}
		}
		stream = nas;
		// for(NoteAction na: nas) {
		// 	System.out.print(na + " ");
		// }
		// System.out.println();
	}

	public List<NoteAction> getStream() {
		return stream;
	}
}