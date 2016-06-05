package com.rx.samples.using.jdk;

import java.util.Observable;
import java.util.Observer;

public class George implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("George got The news:" + arg);
    }
}
