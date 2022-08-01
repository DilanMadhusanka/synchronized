package com.example.asynchronized;

public class WaitDemo {

    public void startExec()
    {

        GunFight gf = new GunFight();

        // Creating a new thread and invoking
        // our fire() method on it
        new Thread() {
            @Override public void run() { gf.fire(260); }
        }.start();

        // Creating a new thread and invoking
        // our reload method on it
        new Thread() {
            @Override public void run() { gf.reload(); }
        }.start();
    }
}
