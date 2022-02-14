package no.ntnu.helipeli.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Notifyer {
    private List<Observer> observers = new ArrayList<>();

    /**
     * Adds observer to observer list
     * @param observer to add
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Removes observer from the observer list
     * @param observer to remove
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void Notify(String achievementText, int achievementKey) {
        for (Observer observer : this.observers) {
            observer.OnNotify(achievementText, achievementKey);
        }
    }
}
