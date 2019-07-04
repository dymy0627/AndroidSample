package com.example.yulin.exoplayer;

public interface IPlayer {

    void setMediaURL(String url);

    void start();

    void stop();

    void release();

}