package com.example.androidchess;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class Game implements Serializable, Comparable<Game> {
    private String name;
    private Date date;
    private CharSequence result;
    ChessStack chessStack = new ChessStack();
    public static int  sort_type = 0;
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void setDate(Date currentTime){
        this.date=currentTime;
    }
    public Date getDate(){
        return this.date;
    }
    public String toString(){
        return this.name+"\n"+this.date.toString();
    }
    public void setResult(CharSequence result) {
        this.result = result;
    }
    public CharSequence getResult(){
        return this.result;
    }


    @Override
    public int compareTo(Game other) {
        if(sort_type == 0){
            //if the user click on sort by name, then sort type would be 0
            return this.name.compareTo(other.name);
        }
        if(sort_type == 1){
            // if the user click on sort by date, then
            return this.date.compareTo(other.date);
        }
        return this.date.compareTo(other.date);
    }


//    @Override
//    public int compare(Game game, Game t1) {
//        String v1 = game.name;
//        String v2 = t1.name;
//
//        if ((v1.compareTo(v2)) >0) return 1;
//        else return 0;
//    }
}
