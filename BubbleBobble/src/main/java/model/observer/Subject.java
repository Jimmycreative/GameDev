package model.observer;

/**
 * Subject interface for observer pattern
 */
public interface Subject {
    /**
     * Add an observer to the arraylist
     */
    void addObserver(Observer o);
    /**
     * Remove an observer from the arraylist
     */
    void removeObserver(Observer o);
    /**
     * Notify all the observers for update
     */
    void notifyObservers();
}
