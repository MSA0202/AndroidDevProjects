package com.example.lab1;

public class Timer{
    long startTime;
    long endTime;
    //System.currentTimeMillis(); a built in method to get current time

    public Timer(){}

    public void start(){
        startTime = System.currentTimeMillis();
    }

    public void stop(){
        endTime = System.currentTimeMillis();
    }

    public long getTime(){
        return endTime-startTime;
    }

}
