package com.cfs.threaded;

public class Main {

    public static void main(String[] args){
        Thread thread = new Thread(new GUI());
        thread.run();
    }
}
