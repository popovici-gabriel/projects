package com.rx.samples.using.jdk;

/**
 * The Observer and Observable are used to implement the Observer Pattern in JDK Java.
 * This pattern is used when a multiple number of instances called Observers are listening to changes
 * to a particular class called Observable.
 * For example, if the underlying data-source changes, all the views using that data-source
 * should reflect the changes.
 */
public class ObserverObservableJdk {

    public static void main(String[] args) {
        News news = new News();
        Gabe gabe = new Gabe();
        George george = new George();
        news.addObserver(gabe);
        news.addObserver(george);
        news.news();
    }
}
