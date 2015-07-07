package chess.components;

public interface ISubject {
    void attachObserver(IObserver iObserver);
    void detachObserver(IObserver iObserver);
    void notifyObservers(String message);
}
