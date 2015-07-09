package application;

//FreeTTS library has been used for generate sound
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class SoundThread extends Thread{
    private String voiceName = "kevin16";
    private VoiceManager voiceManager;
    private Voice voice;
    private String message;

    public SoundThread(){
        super();
        this.voiceManager = VoiceManager.getInstance();
    }

    public void run(){
        synchronized (voiceManager){
            setup();
            speak(this.message);
            exit();
        }
    }

    private void setup() {
        voice = voiceManager.getVoice(voiceName);

        voice.setPitch((float) 180.0);
        voice.setPitchShift((float) 1.0);//set how high must be voice
        voice.setPitchRange((float)10.0);//range with start and end voice tone
        voice.setRate((float)150.0);//speed of speaking

        voice.setStyle("business");  //"business", "casual", "robotic", "breathy"

        if (voice == null) {
            System.err.println("Cannot find a voice named "
                    + voiceName + ".  Please specify a different voice.");
            System.exit(1);
        }
        voice.allocate();
    }

    private void speak(String message){
        if (message.isEmpty()) {
            message = "nothing";
        }
        voice.speak(message);

    }

    private void exit(){
        voice.deallocate();
    }

    public synchronized void setMessage(String message){
        this.message = message;
    }

}
