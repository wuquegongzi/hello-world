package com.leon.core.java8.recon.observer;

public interface  Subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
}


