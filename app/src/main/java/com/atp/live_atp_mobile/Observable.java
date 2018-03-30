package com.atp.live_atp_mobile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cebosc on 27/03/2018.
 */

abstract class Observable {
    List<Observer> observers;

    Observable(){
        this.observers = new ArrayList<>();
    }

    void notification(){
        for (Observer observer: observers) {
            observer.reactGps(this);
        }
    }
}
