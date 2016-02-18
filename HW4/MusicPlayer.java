import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;

public class MusicPlayer {
    private MidiChannel[] channels;
    private boolean[][] actives;
    private int beatLength;
    public static final int NOTE_COUNT = 128;
    public static final int VOLUME = 127;
    public static final int BPM = 100;
    public static final int MS_PER_MINUTE = 60000;
    public static final int BEATS_PER_WHOLE = 4;
    private static MusicPlayer instance = null;


    private MusicPlayer() {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            this.channels = new MidiChannel[15];
            for(int i = 0; i < channels.length; i++) {
                if( i < 9 ) {
                    this.channels[i] = channels[i];
                } else if( i > 9 ) {
                    this.channels[i - 1] = channels[i];
                }
            }
            actives = new boolean[this.channels.length][NOTE_COUNT];
            for(int i = 0; i < actives.length; i++) {
                for(int j = 0; j < actives[0].length; j++ ) {
                    actives[i][j] = false;
                }
            }

            beatLength = MS_PER_MINUTE / BPM * BEATS_PER_WHOLE;
        } catch(Exception e) {
            e.printStackTrace();
        }        
    }

    public static MusicPlayer instance() {
        if( instance == null ) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void play(int note, double duration) throws Exception {
        boolean free = false;
        int i;
        if( note < 0 ) {
            throw new Exception("Note too low");
        }
        for(i = 0; i < actives.length; i++) {
            if( !actives[i][note] ) {
                actives[i][note] = true;
                free = true;
                break;
            }
        }
        if( !free ) {
            i = 0;
        }

        channels[i].noteOn(note,VOLUME);
        System.out.println("Playing " + note + " for " + (duration * beatLength));
        try {
            Thread.sleep((long)(duration * beatLength));
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        channels[i].noteOff(note);
        actives[i][note] = false;
    }

    public void rest(double duration) {
        System.out.println("Resting for " + (duration * beatLength));
        try {
            Thread.sleep((long)(duration * beatLength));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
