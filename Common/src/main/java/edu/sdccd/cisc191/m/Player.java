package edu.sdccd.cisc191.m;

import java.net.*;
import java.io.*;




public class Player {
    boolean isWhite;


    long id;
    String userName;
    int wins;
    int losses;
    int draws;


    public Player(long ID, String userName, int wins, int losses, int draws) {

        this.userName = userName;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }


    @Override
    public String toString() {
        return "Player{" +
                "isWhite=" + isWhite +
                ", ID=" + id +
                ", Username='" + userName + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", draws=" + draws +
                '}';
    }

    public Player(){
    }

    public Player(boolean isWhite){
        this.isWhite = isWhite;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

}