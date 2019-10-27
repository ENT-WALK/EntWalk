package com.app.mymap.model;

import android.widget.TextView;

public class Leaderboard {
    private String username;
    private int highscore;
    public Leaderboard(){

    }
    public Leaderboard(String username,int highscore){
        this.username = username;
        this.highscore = highscore;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public int getscore(){
        return highscore;
    }
    public void setscore(int username){
        this.highscore = highscore;
    }
    public String toString(){
        return "User " + this.username + " Score : " + highscore;
    }
}
