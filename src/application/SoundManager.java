package application;

import chess.components.IObserver;

public class SoundManager implements IObserver {

    private static SoundManager instance = null;

    private SoundManager(){}

    public static SoundManager getInstance(){
        if(instance == null)
            instance = new SoundManager();
        return instance;
    }


    @Override
    public void update(String message) {
        SoundThread soundThread = new SoundThread();
        soundThread.setMessage(message);
        soundThread.start();
    }
}
