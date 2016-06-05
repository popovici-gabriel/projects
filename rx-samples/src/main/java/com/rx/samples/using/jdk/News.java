package com.rx.samples.using.jdk;

import java.util.Observable;

/**
 * The java.util.Observable class is used along with Observer instance to implement the Observer pattern.
 * A class whose changes are to be tracked by the observers,
 * extends the java.util.Observable class.
 * This class has implemented methods for updating/notifying the Observers about the changes made to the Observable.
 * It also provides method to the Observer instances to hook on with itself, or unhook.
 * Few methods in the java.util.Observable class are:
 * public void addObserver(Observer o) Add an Observer.
 * public void deleteObserver(Observer o) Delete an Observer.
 * public void notifyObservers() notify observers of changes.
 * <p>
 * Fully : synchronized !!!
 * <p>
 */
public class News extends Observable {
    void news() {
        String[] news = {"News 1", "News 2", "News 3"};
        System.out.println(">> Start Broadcast");
        for (String s : news) {
            //set change
            setChanged();
            //notify observers for change which is a synchronous operation
            notifyObservers(s);
            System.out.println("...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Error Occurred.");
            }
        }
        System.out.println("<< End Broadcast");
    }
}
